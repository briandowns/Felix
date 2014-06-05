/* Copyright (c) 2014 Brian Downs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.bdowns328.felix;

import com.beust.jcommander.JCommander;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class RegistrationHandler {

    private static JCommander config;
    private static final long STARTMILLIS = System.currentTimeMillis();
    private static ArrayList<String> messageContainer = new ArrayList<String>();
    private static CountDownLatch messageReceivedLatch = new CountDownLatch(1);
    private static final boolean DEBUG = true;

    public RegistrationHandler(JCommander jcmd) {
        config = jcmd;
    }


    private JedisPubSub setupSubscriber() {
        final JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {

                log("Unsubscribed...");
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {

                log("Subscribed and listening...");
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
            }

            @Override
            public void onMessage(String channel, String message) {
                messageContainer.add(message);
                if (DEBUG) {
                    log(messageContainer.iterator().next());
                }
                String[] fields = message.split(REDIS_DELIMITER);
                for (int i = 0; i < fields.length - 1; i++) {
                    if (fields[i] == null) {
                        log("NULL FIELD: " + i + " : " + fields[i]);
                    }
                }
                if (fields[0].contentEquals("CHECK-IN")) {
                    log("Adding Entry");
                    KamControl.controlKamailioDispatcher("reload");
                } else if (fields[0].contentEquals("CHECK-OUT")) {
                    log("Deleting Entry");
                    KamControl.controlKamailioDispatcher("reload");
                } else {
                    log("ERROR:  Received incorrect event.");
                }
                messageReceivedLatch.countDown();
            }
        };

        new Thread( () -> {
            try {
                Jedis jedis = new Jedis(REDISSERVER, REDISPORT, TIMEOUT);
                while (true) {
                    jedis.subscribe(jedisPubSub, KAM_CHANNEL);
                }
            } catch (Exception e) {
                log("ERROR: " + e.getMessage());
            }
        }, "subscriberThread").start();
        return (jedisPubSub);
    }

    /**
     * Main worker thread.
     * @throws java.lang.InterruptedException
     */
    private void run() throws InterruptedException {
        JedisPubSub jedisPubSub = setupSubscriber();
        messageReceivedLatch.await();
        log("Received message: %s", messageContainer.iterator().next());
    }

    /**
     * Logging function for obvious reasons.
     * @param string, Object...
     */
    private static void log(String string, Object... args) {
        long millisSinceStart = System.currentTimeMillis() - STARTMILLIS;
        System.out.printf("%20s %6d %s\n", Thread.currentThread().getName(), millisSinceStart, String.format(string, args));
    }

    /**
     * Point of entry
     * @param args String
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Settings settings = new Settings();
        JCommander jcmd = new JCommander(settings, args);
        jcmd.setProgramName("felix");
        try {
            jcmd.parse(args);
            new RegistrationHandler(jcmd).run();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            jcmd.usage();
        }
    }
}

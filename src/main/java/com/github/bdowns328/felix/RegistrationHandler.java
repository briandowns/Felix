package com.github.bdowns328.felix;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class RegistrationHandler {
    private static final long STARTMILLIS = System.currentTimeMillis();
    private ArrayList<String> messageContainer = new ArrayList<String>();
    private CountDownLatch messageReceivedLatch = new CountDownLatch(1);
    private static final boolean DEBUG = true;

    private Properties getConfigutation() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return(prop);
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
                String[] fields = message.split("\\t");
                for (int i = 0; i < fields.length - 1; i++) {
                    if (fields[i] == null) {
                        log("NULL FIELD: " + i + " : " + fields[i]);
                    }
                }
                if (fields[0].contentEquals("CHECKIN")) {
                    log("Adding Entry...");
                    try {
                        Connection dbConn = getConnection();
                        dbConn.setAutoCommit(false);
                        Statement stmt = dbConn.createStatement();
                        String sql = "INSERT INTO dispatcher (id, setid, destination, flags, priority, attrs, description) VALUES" +
                                "(?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = dbConn.prepareStatement(sql);

                        for (int i = 1; i < fields.length; i++) {
                            preparedStatement.setString(i, fields[i]);
                        }
                        preparedStatement.executeUpdate();
                        dbConn.commit();
                        stmt.close();
                        dbConn.close();
                        restartKamailioDaemon();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (fields[0].contentEquals("CHECKOUT")) {
                    log("Deleting Entry...");
                    try {
                        Connection dbConn = getConnection();
                        dbConn.setAutoCommit(false);
                        Statement stmt = dbConn.createStatement();
                        String sql = "DELETE FROM dispatcher WHERE description in (?)";
                        PreparedStatement preparedStatement = dbConn.prepareStatement(sql);
                        preparedStatement.setString(1, fields[1]);
                        preparedStatement.executeUpdate();
                        dbConn.commit();
                        stmt.close();
                        dbConn.close();
                        restartKamailioDaemon();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    log("ERROR:  Received incorrect event.");
                }
                messageReceivedLatch.countDown();
            }
        };

        // Create new thread that connects to the Redis server
        // and waits for messages sent through the "kam" channel
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log("Connecting");
                    Jedis jedis = new Jedis(REDISSERVER, REDISPORT, TIMEOUT);
                    log("subscribing");
                    while (true) {
                        jedis.subscribe(jedisPubSub, "kam");
                    }
                } catch (Exception e) {
                    log("ERROR: " + e.getMessage());
                }
            }
        }, "subscriberThread").start();
        return (jedisPubSub);
    }

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
}

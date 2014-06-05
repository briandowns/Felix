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

public class FelixRunner {

    /**
     * Point of entry
     * @param args String
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Settings settings = new Settings();
        JCommander jcmd = new JCommander();
        jcmd.setProgramName("felix");
        jcmd.addObject(settings);
        try {
            jcmd.parse(args);
            new RegistrationHandler(jcmd).run();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            jcmd.usage();
        }
    }
}

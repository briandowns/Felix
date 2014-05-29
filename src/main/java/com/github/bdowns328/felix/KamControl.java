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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KamControl {
    /**
     * Control the Kamailio Dispatcher module data.
     * @param dispatchCommand, String
     * @return output, String
     */
    public static String controlKamailioDispatcher(String dispatchCommand) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(String.format("kamctl dispatcher %s", dispatchCommand));
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return(output.toString());
    }

    /**
     * Control the Kamailio Daemon.
     * @param daemonCommand, String
     */
    public static void controlKamailioDaemon(String daemonCommand) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(daemonCommand);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

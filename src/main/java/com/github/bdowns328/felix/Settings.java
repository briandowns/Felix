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

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@SuppressWarnings("unused")
@Parameters(separators = "=", resourceBundle = "MessageBundle")
public final class Settings {

    @Parameter(
            names = { "-h", "--help" },
            help = true
    )
    private boolean help;

    @Parameter(
            names = { "-d", "--debug" },
            description = "Turn on debug"
    )
    private boolean debug;

    @Parameter(
            names = { "-c", "--config" },
            description = "Path to config file to use"
    )
    private String configFile;

    @Parameter(
            names = { "-l", "--log" },
            description = "Log file to use"
    )
    private String logFile;

    /**
     * Display help when called
     * @return boolean
     */
    public boolean isHelp() {
        return(help);
    }

    /**
     * Put in debug when called
     * @return boolean
     */
    public boolean isDebug() {
        return(debug);
    }

    /**
     * Get provided log file
     * @return String
     */
    public String getLogFile() {
        return(logFile);
    }

    /**
     * Get provided redis server
     * @return String
     */
    public String getRedisServer() {
        return(redisServer);
    }

    /**
     * Get provided redis port
     * @return int
     */
    public int getRedisPort() {
        return(redisPort);
    }

    /**
     * Get provided redis channel
     * @return String
     */
    public String getRedisChannel() {
        return(redisChannel);
    }

    /**
     * Get provided redis delimiter
     * @return char
     */
    public char getRedisDelimiter() {
        return(redisDelimiter);
    }

    /**
     * Get provided redis timeout
     * @return int
     */
    public int getRedisTimeout() {
        return(redisTimeout);
    }

    /**
     * Get provided database server
     * @return String
     */
    public String getDatabaseServer() {
        return(databaseServer);
    }

    /**
     * Get provided database username
     * @return String
     */
    public String getDatabaseUser() {
        return(databaseUser);
    }

    /**
     * Get provided database password
     * @return String
     */
    public String getDatabasePassword() {
        return(databasePassword);
    }

    /**
     * Get provided database name
     * @return String
     */
    public String getDatabaseName() {
        return(databaseName);
    }
}
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

@Parameters(separators = "=", resourceBundle = "MessageBundle")
public class Settings {
    @Parameter(names = "--help", help = true)
    private boolean help;

    @Parameter(names = "--debug", description = "Turn on debug")
    private boolean debug;

    @Parameter(names = "--redis-server", description = "Redis server address", required = true)
    private String redisServer;

    @Parameter(names = "--redis-port", description = "Redis server port", required = true)
    private int redisPort;

    @Parameter(names = "--redis-channel", description = "Redis pub/sub channel", required = true)
    private String redisChannel;

    @Parameter(names = "--redis-delimiter", description = "Redis server address", required = true)
    private char redisDelimiter;

    @Parameter(names = "--redis-timeout", description = "Redis server timeout", required = true)
    private int redisTimeout;

    @Parameter(names = "--db-server", description = "DB server address", required = true)
    private String databaseServer;

    @Parameter(names = "--db-user", description = "DB username", required = true)
    private String databaseUser;

    @Parameter(names = "--db-pass", description = "DB password", required = true)
    private String databasePassword;

    @Parameter(names = "--db-name", description = "DB name", required = true)
    private String databaseName;
}
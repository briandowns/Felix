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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseProvider {
    private String delSql = "DELETE FROM dispatcher WHERE description in (?)";
    private String addSql = "INSERT INTO dispatcher (id, setid, destination, flags, priority, attrs, description) VALUES\" +\n" +
            "                    \"(?, ?, ?, ?, ?, ?, ?)";

    public Connection getConnection() {

    }

    public void removeEntry() {
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
    }

    public void addEntry() {
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
    }
}

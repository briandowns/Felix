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

import java.sql.*;

public class DatabaseProvider {
    private String delSql = "DELETE FROM dispatcher WHERE description in (?)";
    private String addSql = "INSERT INTO dispatcher (id, setid, destination, flags, priority, attrs, description) VALUES\" +\n" +
            "                    \"(?, ?, ?, ?, ?, ?, ?)";

    public DatabaseProvider() throws SQLException {
        getConnection();
    }

    /**
     * Connection to Kamailio MySQL database.
     * @throws Exception
     * @return Connection
     */
    private static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + DBHOST + "/" + DATABASE + "?user=" + DBUSER + "&password=" + DBPASS);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return(conn);
    }

    /**
     * Remove entry from the Kamailio dispatcher.
     */
    public void removeEntry() {
        try {
            Connection dbConn = getConnection();
            dbConn.setAutoCommit(false);
            Statement stmt = dbConn.createStatement();
            PreparedStatement preparedStatement = dbConn.prepareStatement(delSql);
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

    /**
     * Add entry to the Kamailio dispatcher.
     */
    public void addEntry() {
        try {
            Connection dbConn = getConnection();
            dbConn.setAutoCommit(false);
            Statement stmt = dbConn.createStatement();
            PreparedStatement preparedStatement = dbConn.prepareStatement(addSql);

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

package com.dbms.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL =
            "jdbc:derby:lab10db;create=true";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(URL);
    }

    public static void shutdownDatabase() {

        try {
            DriverManager.getConnection(
                    "jdbc:derby:lab10db;shutdown=true");

        } catch (SQLException e) {

            System.out.println(
                    "Derby shutdown successfully.");
        }
    }
}
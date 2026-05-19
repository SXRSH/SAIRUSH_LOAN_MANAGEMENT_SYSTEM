package com.dbms.setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.dbms.connection.ConnectionManager;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        try (Connection conn =
                     ConnectionManager.getConnection();

             Statement stmt =
                     conn.createStatement()) {

            createMembersTable(stmt);
            createBooksTable(stmt);
            createLoansTable(stmt);

            createIndexes(stmt);

            seedData(conn);

            System.out.println(
                    "Database initialized successfully!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void createMembersTable(
            Statement stmt)
            throws SQLException {

        try {

            stmt.executeUpdate(
                    "CREATE TABLE Members (" +
                            "MemberID INT GENERATED ALWAYS " +
                            "AS IDENTITY PRIMARY KEY, " +
                            "Name VARCHAR(100), " +
                            "Email VARCHAR(100) UNIQUE, " +
                            "ActiveLoans INT DEFAULT 0" +
                            ")");

            System.out.println(
                    "Members table created.");

        } catch (SQLException e) {

            System.out.println(
                    "Members table already exists.");
        }
    }

    private static void createBooksTable(
            Statement stmt)
            throws SQLException {

        try {

            stmt.executeUpdate(
                    "CREATE TABLE Books (" +
                            "BookID INT GENERATED ALWAYS " +
                            "AS IDENTITY PRIMARY KEY, " +
                            "Title VARCHAR(100), " +
                            "Author VARCHAR(100), " +
                            "ISBN VARCHAR(30) UNIQUE, " +
                            "Available BOOLEAN DEFAULT TRUE" +
                            ")");

            System.out.println(
                    "Books table created.");

        } catch (SQLException e) {

            System.out.println(
                    "Books table already exists.");
        }
    }

    private static void createLoansTable(
            Statement stmt)
            throws SQLException {

        try {

            stmt.executeUpdate(
                    "CREATE TABLE Loans (" +
                            "LoanID INT GENERATED ALWAYS " +
                            "AS IDENTITY PRIMARY KEY, " +
                            "MemberID INT, " +
                            "BookID INT, " +
                            "LoanDate DATE, " +
                            "ReturnDate DATE, " +
                            "FOREIGN KEY (MemberID) " +
                            "REFERENCES Members(MemberID), " +
                            "FOREIGN KEY (BookID) " +
                            "REFERENCES Books(BookID)" +
                            ")");

            System.out.println(
                    "Loans table created.");

        } catch (SQLException e) {

            System.out.println(
                    "Loans table already exists.");
        }
    }

    private static void createIndexes(
            Statement stmt)
            throws SQLException {

        try {

            stmt.executeUpdate(
                    "CREATE INDEX idx_isbn " +
                            "ON Books(ISBN)");

            stmt.executeUpdate(
                    "CREATE INDEX idx_memberid " +
                            "ON Loans(MemberID)");

            stmt.executeUpdate(
                    "CREATE INDEX idx_returndate " +
                            "ON Loans(ReturnDate)");

            System.out.println(
                    "Indexes created.");

        } catch (SQLException e) {

            System.out.println(
                    "Indexes already exist.");
        }
    }

    private static void seedData(
            Connection conn)
            throws SQLException {

        String memberQuery =
                "INSERT INTO Members " +
                        "(Name, Email) VALUES (?, ?)";

        String bookQuery =
                "INSERT INTO Books " +
                        "(Title, Author, ISBN) " +
                        "VALUES (?, ?, ?)";

        try (
                PreparedStatement memberStmt =
                        conn.prepareStatement(
                                memberQuery);

                PreparedStatement bookStmt =
                        conn.prepareStatement(
                                bookQuery)
        ) {

            memberStmt.setString(
                    1,
                    "Rahul");

            memberStmt.setString(
                    2,
                    "rahul@gmail.com");

            memberStmt.executeUpdate();

            memberStmt.setString(
                    1,
                    "Priya");

            memberStmt.setString(
                    2,
                    "priya@gmail.com");

            memberStmt.executeUpdate();

            bookStmt.setString(
                    1,
                    "Java Programming");

            bookStmt.setString(
                    2,
                    "James Gosling");

            bookStmt.setString(
                    3,
                    "111111");

            bookStmt.executeUpdate();

            bookStmt.setString(
                    1,
                    "Database Systems");

            bookStmt.setString(
                    2,
                    "Elmasri");

            bookStmt.setString(
                    3,
                    "222222");

            bookStmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Seed data already exists.");
        }
    }
}
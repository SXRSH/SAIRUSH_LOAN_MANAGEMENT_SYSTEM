package com.dbms.performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.dbms.connection.ConnectionManager;

public class PerformanceEvaluator {

    public static void runBenchmarks() {

        System.out.println(
                "\n===== PERFORMANCE REPORT =====");

        benchmarkInsertStrategy();

        benchmarkStatementVsPrepared();

        benchmarkTransactionGranularity();
    }

    // INSERT STRATEGY
    public static void benchmarkInsertStrategy() {

        int records = 1000;

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection()
        ) {

            // Warm up
            Thread.sleep(100);

            long start =
                    System.nanoTime();

            String query =
                    "INSERT INTO Members " +
                    "(Name, Email) " +
                    "VALUES (?, ?)";

            for (int i = 0;
                 i < records;
                 i++) {

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query);

                stmt.setString(
                        1,
                        "User" + i);

                stmt.setString(
                        2,
                        "mail" + i
                                + "@gmail.com");

                stmt.executeUpdate();
            }

            long end =
                    System.nanoTime();

            double individualTime =
                    (end - start)
                            / 1000000.0;

            start =
                    System.nanoTime();

            PreparedStatement stmt =
                    conn.prepareStatement(
                            query);

            for (int i = 0;
                 i < records;
                 i++) {

                stmt.setString(
                        1,
                        "BatchUser" + i);

                stmt.setString(
                        2,
                        "batch" + i
                                + "@gmail.com");

                stmt.addBatch();
            }

            stmt.executeBatch();

            end =
                    System.nanoTime();

            double batchTime =
                    (end - start)
                            / 1000000.0;

            System.out.println(
                    "\nInsert Strategy");

            System.out.println(
                    "Individual Inserts: "
                            + individualTime
                            + " ms");

            System.out.println(
                    "Batch Inserts: "
                            + batchTime
                            + " ms");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // STATEMENT VS PREPARED
    public static void benchmarkStatementVsPrepared() {

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection()
        ) {

            Thread.sleep(100);

            long start =
                    System.nanoTime();

            Statement stmt =
                    conn.createStatement();

            stmt.executeQuery(
                    "SELECT * FROM Members");

            long end =
                    System.nanoTime();

            double statementTime =
                    (end - start)
                            / 1000000.0;

            start =
                    System.nanoTime();

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "SELECT * FROM Members");

            pstmt.executeQuery();

            end =
                    System.nanoTime();

            double preparedTime =
                    (end - start)
                            / 1000000.0;

            System.out.println(
                    "\nStatement Type");

            System.out.println(
                    "Statement: "
                            + statementTime
                            + " ms");

            System.out.println(
                    "PreparedStatement: "
                            + preparedTime
                            + " ms");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // TRANSACTION GRANULARITY
    public static void benchmarkTransactionGranularity() {

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection()
        ) {

            int operations = 100;

            String query =
                    "INSERT INTO Books " +
                    "(Title, Author, ISBN) " +
                    "VALUES (?, ?, ?)";

            long start =
                    System.nanoTime();

            for (int i = 0;
                 i < operations;
                 i++) {

                conn.setAutoCommit(false);

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query);

                stmt.setString(
                        1,
                        "Book" + i);

                stmt.setString(
                        2,
                        "Author" + i);

                stmt.setString(
                        3,
                        "ISBN" + i);

                stmt.executeUpdate();

                conn.commit();
            }

            long end =
                    System.nanoTime();

            double perCommit =
                    (end - start)
                            / 1000000.0;

            start =
                    System.nanoTime();

            conn.setAutoCommit(false);

            PreparedStatement stmt =
                    conn.prepareStatement(
                            query);

            for (int i = 0;
                 i < operations;
                 i++) {

                stmt.setString(
                        1,
                        "BatchBook" + i);

                stmt.setString(
                        2,
                        "Author" + i);

                stmt.setString(
                        3,
                        "BISBN" + i);

                stmt.executeUpdate();
            }

            conn.commit();

            end =
                    System.nanoTime();

            double batchCommit =
                    (end - start)
                            / 1000000.0;

            System.out.println(
                    "\nTransaction Granularity");

            System.out.println(
                    "Per-operation commit: "
                            + perCommit
                            + " ms");

            System.out.println(
                    "Batch commit: "
                            + batchCommit
                            + " ms");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
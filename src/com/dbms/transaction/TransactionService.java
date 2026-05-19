package com.dbms.transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;

import com.dbms.connection.ConnectionManager;

public class TransactionService {

    public static void processLoan(
            int memberId,
            int bookId) {

        Connection conn = null;
        Savepoint savepoint = null;

        try {

            conn =
                    ConnectionManager.getConnection();

            // Disable auto commit
            conn.setAutoCommit(false);

            // Step 1: Check book availability
            String checkBookQuery =
                    "SELECT Available " +
                    "FROM Books " +
                    "WHERE BookID = ?";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    checkBookQuery)
            ) {

                stmt.setInt(1, bookId);

                ResultSet rs =
                        stmt.executeQuery();

                if (rs.next()) {

                    boolean available =
                            rs.getBoolean(
                                    "Available");

                    if (!available) {

                        throw new Exception(
                                "Book not available!");
                    }

                } else {

                    throw new Exception(
                            "Book not found!");
                }
            }

            // Step 2: Update book status
            String updateBook =
                    "UPDATE Books " +
                    "SET Available = FALSE " +
                    "WHERE BookID = ?";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    updateBook)
            ) {

                stmt.setInt(1, bookId);

                stmt.executeUpdate();
            }

            // Savepoint
            savepoint =
                    conn.setSavepoint(
                            "BookUpdated");

            // Step 3: Insert loan
            String insertLoan =
                    "INSERT INTO Loans " +
                    "(MemberID, BookID, LoanDate) " +
                    "VALUES (?, ?, ?)";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    insertLoan)
            ) {

                stmt.setInt(1, memberId);

                stmt.setInt(2, bookId);

                stmt.setDate(
                        3,
                        new Date(
                                System.currentTimeMillis()
                        )
                );

                stmt.executeUpdate();
            }

            // Step 4:
            // Update member loan count
            String updateMember =
                    "UPDATE Members " +
                    "SET ActiveLoans = " +
                    "ActiveLoans + 1 " +
                    "WHERE MemberID = ?";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    updateMember)
            ) {

                stmt.setInt(
                        1,
                        memberId
                );

                int rows =
                        stmt.executeUpdate();

                // Failure simulation
                if (rows == 0) {

                    throw new Exception(
                            "Member update failed!"
                    );
                }
            }

            // Commit transaction
            conn.commit();

            System.out.println(
                    "Loan processed successfully!"
            );

        } catch (Exception e) {

            try {

                if (conn != null) {

                    if (savepoint != null) {

                        conn.rollback(
                                savepoint
                        );

                        System.out.println(
                                "Rolled back " +
                                "to savepoint."
                        );
                    }

                    conn.rollback();

                    System.out.println(
                            "Transaction rolled back."
                    );
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );

        } finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);

                    conn.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
package com.dbms.business;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dbms.connection.ConnectionManager;

public class BusinessLogic {

    // Register Member
    public static void registerMember(
            String name,
            String email) {

        String query =
                "INSERT INTO Members " +
                "(Name, Email) VALUES (?, ?)";

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query)
        ) {

            stmt.setString(1, name);
            stmt.setString(2, email);

            stmt.executeUpdate();

            System.out.println(
                    "Member registered successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );
        }
    }

    // Add Book
    public static void addBook(
            String title,
            String author,
            String isbn) {

        String query =
                "INSERT INTO Books " +
                "(Title, Author, ISBN) " +
                "VALUES (?, ?, ?)";

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query)
        ) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, isbn);

            stmt.executeUpdate();

            System.out.println(
                    "Book added successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );
        }
    }

    // Return Book
    public static void returnBook(
            int memberId,
            int bookId) {

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection()
        ) {

            conn.setAutoCommit(false);

            // update book availability
            String updateBook =
                    "UPDATE Books " +
                    "SET Available = TRUE " +
                    "WHERE BookID = ?";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    updateBook)
            ) {

                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            // update return date
            String updateLoan =
                    "UPDATE Loans " +
                    "SET ReturnDate = ? " +
                    "WHERE MemberID = ? " +
                    "AND BookID = ? " +
                    "AND ReturnDate IS NULL";

            try (
                    PreparedStatement stmt =
                            conn.prepareStatement(
                                    updateLoan)
            ) {

                stmt.setDate(
                        1,
                        new Date(
                                System.currentTimeMillis()
                        )
                );

                stmt.setInt(2, memberId);
                stmt.setInt(3, bookId);

                stmt.executeUpdate();
            }

            // decrease active loans
            String updateMember =
                    "UPDATE Members " +
                    "SET ActiveLoans = " +
                    "ActiveLoans - 1 " +
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

                stmt.executeUpdate();
            }

            conn.commit();

            System.out.println(
                    "Book returned successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );
        }
    }

    // View Active Loans
    public static void viewActiveLoans(
            int memberId) {

        String query =
                "SELECT l.LoanID, " +
                "b.Title " +
                "FROM Loans l " +
                "JOIN Books b " +
                "ON l.BookID = b.BookID " +
                "WHERE l.MemberID = ? " +
                "AND l.ReturnDate IS NULL";

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query)
        ) {

            stmt.setInt(1, memberId);

            ResultSet rs =
                    stmt.executeQuery();

            System.out.println(
                    "\nActive Loans:"
            );

            while (rs.next()) {

                System.out.println(
                        "Loan ID: " +
                        rs.getInt("LoanID")
                                + " | Book: "
                                + rs.getString(
                                "Title")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );
        }
    }

    // Overdue Books
    public static void viewOverdueBooks() {

        String query =
                "SELECT LoanID, " +
                "MemberID, BookID " +
                "FROM Loans " +
                "WHERE ReturnDate IS NULL " +
                "AND LoanDate < " +
                "CURRENT_DATE - 7 DAYS";

        try (
                Connection conn =
                        ConnectionManager
                                .getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(
                                query)
        ) {

            ResultSet rs =
                    stmt.executeQuery();

            System.out.println(
                    "\nOverdue Books:"
            );

            while (rs.next()) {

                System.out.println(
                        "LoanID: " +
                        rs.getInt(
                                "LoanID")
                                + " MemberID: "
                                + rs.getInt(
                                "MemberID")
                                + " BookID: "
                                + rs.getInt(
                                "BookID")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " +
                    e.getMessage()
            );
        }
    }
}
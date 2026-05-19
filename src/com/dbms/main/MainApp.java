package com.dbms.main;

import java.util.Scanner;


import com.dbms.business.BusinessLogic;
import com.dbms.setup.DatabaseInitializer;
import com.dbms.transaction.TransactionService;
import com.dbms.performance.PerformanceEvaluator;
import com.dbms.connection.ConnectionManager;
import com.dbms.validation.ValidationRunner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        DatabaseInitializer
                .initializeDatabase();

        int choice;

        do {

            System.out.println(
                    "\n===== LIBRARY LOAN SYSTEM =====");

            System.out.println(
                    "1. Register Member");

            System.out.println(
                    "2. Add Book");

            System.out.println(
                    "3. Process Loan");

            System.out.println(
                    "4. Return Book");

            System.out.println(
                    "5. View Active Loans");

            System.out.println(
                    "6. View Overdue Books");

            System.out.println(
                    "7. Run Performance Benchmark");

            System.out.println(
                    "8. Run Validation Tests");

            System.out.println(
                    "9. Exit");

            System.out.print(
                    "Enter choice: ");

            choice =
                    sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print(
                            "Enter Name: ");

                    String name =
                            sc.nextLine();

                    System.out.print(
                            "Enter Email: ");

                    String email =
                            sc.nextLine();

                    BusinessLogic
                            .registerMember(
                                    name,
                                    email);

                    break;

                case 2:

                    System.out.print(
                            "Enter Title: ");

                    String title =
                            sc.nextLine();

                    System.out.print(
                            "Enter Author: ");

                    String author =
                            sc.nextLine();

                    System.out.print(
                            "Enter ISBN: ");

                    String isbn =
                            sc.nextLine();

                    BusinessLogic
                            .addBook(
                                    title,
                                    author,
                                    isbn);

                    break;

                case 3:

                    System.out.print(
                            "Member ID: ");

                    int memberId =
                            sc.nextInt();

                    System.out.print(
                            "Book ID: ");

                    int bookId =
                            sc.nextInt();

                    TransactionService
                            .processLoan(
                                    memberId,
                                    bookId);

                    break;

                case 4:

                    System.out.print(
                            "Member ID: ");

                    memberId =
                            sc.nextInt();

                    System.out.print(
                            "Book ID: ");

                    bookId =
                            sc.nextInt();

                    BusinessLogic
                            .returnBook(
                                    memberId,
                                    bookId);

                    break;

                case 5:

                    System.out.print(
                            "Member ID: ");

                    memberId =
                            sc.nextInt();

                    BusinessLogic
                            .viewActiveLoans(
                                    memberId);

                    break;

                case 6:

                    BusinessLogic
                            .viewOverdueBooks();

                    break;

                case 7:

                    PerformanceEvaluator
                            .runBenchmarks();

                    break;

                case 8:

                    ValidationRunner
                            .runValidation();

                    break;

                case 9:

                    System.out.println(
                            "Exiting...");

                    break;
                default:

                    System.out.println(
                            "Invalid choice.");
            }

        } while (choice != 9);

        sc.close();

        ConnectionManager
                .shutdownDatabase();
        
        
    }
}
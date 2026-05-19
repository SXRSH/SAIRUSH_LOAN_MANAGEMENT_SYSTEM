package com.dbms.validation;

import com.dbms.business.BusinessLogic;
import com.dbms.transaction.TransactionService;

public class ValidationRunner {

    public static void runValidation() {

        System.out.println(
                "\n===== VALIDATION TESTS =====");

        // Duplicate member email
        System.out.println(
                "\n1. Duplicate Insert Test");

        BusinessLogic.registerMember(
                "TestUser",
                "rahul@gmail.com"
        );

        // Invalid loan
        System.out.println(
                "\n2. Constraint Violation Test");

        TransactionService.processLoan(
                999,
                999
        );

        // Transaction rollback
        System.out.println(
                "\n3. Rollback Test");

        TransactionService.processLoan(
                1,
                1
        );

        System.out.println(
                "\nValidation completed.");
    }
}
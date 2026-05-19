# Analysis Document

## Project Title

Library Loan Management System using JDBC and Apache Derby

---

## Introduction

The Library Loan Management System is a JDBC-based database application developed using Java and Apache Derby in embedded mode. The project demonstrates transaction management, JDBC connectivity, ACID properties, performance benchmarking, and database initialization using a menu-driven command-line interface.

The system is designed to manage members, books, and loan transactions in a structured and efficient manner.

---

## Problem Statement

Traditional manual library management systems are inefficient for handling transactions, maintaining consistency, and processing loans. Errors such as duplicate entries, inconsistent loan tracking, and transaction failures may occur.

The objective of this project is to design and implement a robust database-driven system using JDBC that supports transaction management, rollback mechanisms, performance evaluation, and automated database initialization.

---

## Objectives

The objectives of the project are:

- To establish JDBC connectivity using Apache Derby
- To implement transaction management using commit and rollback
- To demonstrate ACID properties
- To create a menu-driven CLI-based system
- To evaluate JDBC performance using benchmarking
- To validate system correctness through testing

---

## System Architecture

The application follows a layered architecture.

### Connection Layer
Responsible for database connectivity and graceful shutdown using `ConnectionManager`.

### Setup Layer
Responsible for creating tables, indexes, and seed data using `DatabaseInitializer`.

### Model Layer
Contains Java entity classes:

- Member
- Book
- Loan

These classes represent database tables.

### Business Layer
Implements business operations such as:

- Register member
- Add book
- Return book
- View active loans
- View overdue books

### Transaction Layer
Handles explicit transaction management using:

- setAutoCommit(false)
- commit()
- rollback()
- Savepoint

The `TransactionService` class ensures transaction consistency.

### Performance Layer
Benchmarks JDBC execution strategies and compares execution performance.

### Validation Layer
Validates duplicate entries, rollback behavior, and transaction consistency.

### UI Layer
Provides a menu-driven command-line interface through `MainApp`.

---

## Database Design

The project contains three normalized tables:

### Members
Stores member details.

Fields:
- MemberID
- Name
- Email
- ActiveLoans

### Books
Stores book details.

Fields:
- BookID
- Title
- Author
- ISBN
- Available

### Loans
Stores loan transactions.

Fields:
- LoanID
- MemberID
- BookID
- LoanDate
- ReturnDate

---

## Transaction Management

The project demonstrates explicit transaction management through JDBC.

The loan processing operation performs:

1. Book availability verification
2. Book status update
3. Loan record insertion
4. Member loan count update

If any step fails, rollback() restores database consistency.

Savepoints are also used to support partial rollback.

---

## Performance Evaluation

The application evaluates:

- Individual insert vs batch insert
- Statement vs PreparedStatement
- Per-operation commit vs batch commit

Execution time is measured using:

`System.nanoTime()`

The benchmark results show that batch execution and PreparedStatement improve overall performance.

---

## Validation Testing

The system validates:

- Duplicate insertion handling
- Constraint violations
- Transaction rollback
- Error handling

Validation tests ensure correctness and reliability.

---

## Conclusion

The Library Loan Management System successfully demonstrates JDBC connectivity, transaction management, performance benchmarking, and layered software architecture using Apache Derby. The project highlights the importance of ACID properties, rollback mechanisms, and efficient JDBC execution strategies in database-driven applications.
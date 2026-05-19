# Library Loan Management System

## Project Overview

This project is an end-to-end JDBC-based Library Loan Management System developed using Java and Apache Derby (Embedded Mode).

The system demonstrates:

- JDBC database connectivity
- Transaction management (commit, rollback, savepoint)
- ACID properties
- Menu-driven command-line interface
- Performance benchmarking
- Validation testing
- Database initialization and cleanup

---

## Objective

To design and implement a JDBC-based database application using Apache Derby that demonstrates transaction management, error handling, and JDBC performance evaluation.

---

## Technologies Used

- Java (JDK 17)
- JDBC
- Apache Derby (Embedded Database)
- Eclipse IDE

---

## Dependency List

The following Derby JAR files are required:

- derby.jar
- derbytools.jar

Java dependency:

- java.sql

---

## Project Architecture

The project is organized into the following layers:

- com.dbms.connection
- com.dbms.setup
- com.dbms.model
- com.dbms.business
- com.dbms.transaction
- com.dbms.performance
- com.dbms.validation
- com.dbms.main

---

## Features

- Register Member
- Add Book
- Process Loan
- Return Book
- View Active Loans
- View Overdue Books
- Run Performance Benchmark
- Run Validation Tests
- Graceful Database Shutdown

---

## Build Instructions

1. Install JDK 17
2. Install Eclipse IDE for Java Developers
3. Download and extract Apache Derby
4. Add Derby JAR files to Build Path

Steps:

Right Click Project  
→ Build Path  
→ Configure Build Path  
→ Libraries  
→ Add External JARs

Add:

- derby.jar
- derbytools.jar

5. Open project in Eclipse
6. Run MainApp.java

---

## Run Instructions

Run the application using:

Right Click MainApp.java  
→ Run As  
→ Java Application

The Derby database initializes automatically at startup.

---

## Sample CLI Session

===== LIBRARY LOAN SYSTEM =====

1. Register Member  
2. Add Book  
3. Process Loan  
4. Return Book  
5. View Active Loans  
6. View Overdue Books  
7. Run Performance Benchmark  
8. Run Validation Tests  
9. Exit

Enter choice: 1

Enter Name: Sairush

Enter Email: sairush@gmail.com

Member registered successfully!

---

## Performance Benchmark Example

Insert Strategy

Individual Inserts: 130.81 ms

Batch Inserts: 41.52 ms

Statement Type

Statement: 7.47 ms

PreparedStatement: 0.09 ms

Transaction Granularity

Per-operation commit: 7.22 ms

Batch commit: 3.40 ms

---

## Validation Tests

The system validates:

- Duplicate insert handling
- Constraint violation testing
- Rollback verification
- Transaction consistency

---

## Author

Sairush Sahoo
B.Tech Student
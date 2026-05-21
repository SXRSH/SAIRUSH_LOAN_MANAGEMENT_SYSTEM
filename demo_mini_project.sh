#!/bin/bash

echo "====================================================="
echo " LIBRARY LOAN MANAGEMENT SYSTEM - DEMO SCRIPT"
echo "====================================================="
echo ""

# Derby jar location
DERBY_JAR="/Users/sairushsahoo/SAIRUSHSAHOO_DIIJ/db-derby-10.16.1.1-bin/lib/derby.jar"

echo "Compiling Java project..."

mkdir -p bin

javac -cp "$DERBY_JAR:src" -d bin $(find src -name "*.java")

echo ""
echo "Running Library Loan Management System..."
echo ""

java -cp "bin:$DERBY_JAR" com.dbms.main.MainApp <<EOF

1
Sairush
sairush@gmail.com

2
Java Programming
James Gosling
ISBN101

2
Database Systems
Elmasri
ISBN102

3
1
1

5
1

6

4
1
1

7

8

9

EOF

echo ""
echo "====================================================="
echo " DEMO EXECUTION COMPLETED"
echo "====================================================="
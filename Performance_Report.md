# Performance Evaluation Report

## Project Title

Library Loan Management System using JDBC and Apache Derby

---

## Objective

The objective of performance evaluation is to measure and compare the execution characteristics of different JDBC strategies using Apache Derby. The benchmarking process evaluates execution time, transaction efficiency, and database performance.

---

## Benchmark Methodology

The performance evaluation was conducted using:

- `System.nanoTime()` for high-resolution timing
- JDBC-based execution benchmarking
- Embedded Apache Derby database
- Comparative execution strategies
- Warm-up execution before benchmarking

The benchmarks compare execution performance of different JDBC access patterns.

---

## Comparative Metrics

| Operation Type | Strategy | Execution Time (ms) | Observation |
|---|---|---:|---|
| Insert Strategy | Individual Insert (`executeUpdate()`) | 130.81 | Slower because every insert executes separately |
| Insert Strategy | Batch Insert (`addBatch()` + `executeBatch()`) | 41.52 | Faster because multiple inserts execute together |
| Statement Type | `Statement` | 7.47 | Slower due to repeated SQL parsing |
| Statement Type | `PreparedStatement` | 0.09 | Faster because query compilation is reused |
| Transaction Granularity | Per-operation Commit | 7.22 | Increased transaction overhead |
| Transaction Granularity | Batch Commit | 3.40 | Faster because fewer commits occur |

---

## Throughput Analysis

Batch execution improves throughput by reducing repeated JDBC execution overhead. Instead of executing each operation individually, multiple operations are grouped together, improving performance.

PreparedStatement improves execution efficiency because SQL statements are precompiled and reused, reducing parsing and compilation costs.

Batch commit improves transaction performance by minimizing repeated commit operations and transaction logging overhead.

---

## Benchmark Observations

1. Batch insert execution performs significantly faster than individual inserts.

2. PreparedStatement performs faster than Statement due to SQL reuse and precompilation.

3. Batch commit improves performance by reducing transaction overhead.

4. JDBC optimization techniques improve scalability and execution efficiency.

---

## Conclusion

The performance evaluation demonstrates that optimized JDBC techniques significantly improve execution performance in Apache Derby. Batch inserts, PreparedStatement, and grouped transaction commits provide faster and more efficient execution than traditional approaches.

The results validate that JDBC optimization strategies improve throughput, reduce execution overhead, and enhance overall database performance.
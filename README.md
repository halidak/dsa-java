# dsa-java

My LeetCode solutions in Java 21, organized by pattern and following the NeetCode 150 roadmap. Every solution has JUnit 5 tests, and every push runs them on GitHub Actions.

The pattern templates are in [CHEATSHEET.md](CHEATSHEET.md).

## Progress

| # | Pattern | Package | Solved |
|---|---------|---------|--------|
| 1 | Arrays & Hashing | `dsa.arrays` | 1 / 9 |
| 2 | Two Pointers | `dsa.twopointers` | 0 / 5 |
| 3 | Sliding Window | `dsa.slidingwindow` | 0 / 6 |
| 4 | Stack | `dsa.stack` | 0 / 7 |
| 5 | Binary Search | `dsa.binarysearch` | 0 / 7 |
| 6 | Linked List | `dsa.linkedlist` | 0 / 11 |
| 7 | Trees | `dsa.trees` | 0 / 15 |
| 8 | Tries | `dsa.tries` | 0 / 3 |
| 9 | Heap / Priority Queue | `dsa.heap` | 0 / 7 |
| 10 | Backtracking | `dsa.backtracking` | 0 / 9 |
| 11 | Graphs | `dsa.graphs` | 0 / 13 |
| 12 | Advanced Graphs | `dsa.advancedgraphs` | 0 / 6 |
| 13 | 1-D Dynamic Programming | `dsa.dp1d` | 0 / 12 |
| 14 | 2-D Dynamic Programming | `dsa.dp2d` | 0 / 11 |
| 15 | Greedy | `dsa.greedy` | 0 / 8 |
| 16 | Intervals | `dsa.intervals` | 0 / 6 |
| 17 | Math & Geometry | `dsa.math` | 0 / 8 |
| 18 | Bit Manipulation | `dsa.bits` | 0 / 7 |

## Layout

```
src/main/java/dsa/<pattern>/<ProblemName>.java       solution
src/test/java/dsa/<pattern>/<ProblemName>Test.java   tests
```

## Conventions

Each solution starts with a header comment:

```java
/**
 * <number>. <Title>
 * <link>
 *
 * Pattern:    <pattern>
 * Idea:       <the key insight in one or two sentences>
 * Complexity: O(?) time, O(?) space
 * Solved:     <date>  ·  confidence: <1-3>/3
 */
```

- One problem per commit: `feat(<pattern>): <problem title>`.
- Tests cover the LeetCode examples plus edge cases (empty input, a single element, duplicates, negative numbers).
- If I needed to look at the solution, the header says `confidence: 1/3` and I solve the problem again from scratch a few days later.
- When a problem teaches me a new trick, I add one line about it to CHEATSHEET.md.

## Running the tests

```bash
mvn test                                  # all tests
mvn test -Dtest=ContainsDuplicateTest     # one problem
```

You need JDK 21 and Maven 3.9 or newer.

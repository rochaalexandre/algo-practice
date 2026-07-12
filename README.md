# Algorithms practice

Personal repository for deliberate practice on algorithm patterns and data structure trade-offs, built through problem-solving sessions guided by Claude (pattern recognition first, code second, no solutions handed over directly).

Each exercise is a JUnit test class. The class javadoc documents:
- the problem statement and constraints
- the naive approach and its time complexity
- the optimized solution and the reasoning that leads to it

## Stack

- Java
- JUnit 5
- Gradle (Kotlin DSL)

## Running the tests

```bash
./gradlew test
```

## Exercises

| Class | Pattern | Complexity |
|---|---|---|
| [`TwoSumWithHashMapTest`](src/test/java/org/example/TwoSumWithHashMapTest.java) | Hash map for O(1) lookup instead of nested loop | O(n) time, O(n) space |

## Why this exists

The goal isn't to memorize solutions, but to build the habit of recognizing which data structure fits a problem's constraints — and to connect that recognition back to real-world backend scenarios (e.g. matching records without a nested loop).

# Algorithms practice

Personal repository for deliberate practice on algorithm patterns and data structure trade-offs, built through problem-solving sessions guided by Claude (pattern recognition first, code second, no solutions handed over directly).

Each exercise is a JUnit test class. The class javadoc documents:
- the problem statement and constraints
- the naive approach and its time complexity
- the optimized solution and the reasoning that leads to it

## Stack

- Java
- JUnit 5
- AssertJ
- Gradle (Kotlin DSL)

## Running the tests

```bash
./gradlew test
```

## Exercises

| Class | Pattern | Complexity |
|---|---|---|
| [`TwoSumWithHashMapTest`](src/test/java/org/example/TwoSumWithHashMapTest.java) | Hash map for O(1) lookup instead of nested loop | O(n) time, O(n) space |
| [`DailyTemperaturesWithStackTest`](src/test/java/org/example/DailyTemperaturesWithStackTest.java) | Monotonic stack — resolves multiple waiting days in one pass | O(n) time, O(n) space |
| [`TwoSumWithTwoPointersTest`](src/test/java/org/example/TwoSumWithTwoPointersTest.java) | Two pointers on a sorted array, no extra space | O(n) time, O(1) space |
| [`LongestSubstringWithSlidingWindowTest`](src/test/java/org/example/LongestSubstringWithSlidingWindowTest.java) | Sliding window + hash map, window grows/shrinks by uniqueness | O(n) time, O(n) space |
| [`MinSubarrayWithSlidingWindowTest`](src/test/java/org/example/MinSubarrayWithSlidingWindowTest.java) | Sliding window, shrinks while sum stays valid | O(n) time, O(1) space |
| [`MaxSubarraySumWithKadaneTest`](src/test/java/org/example/MaxSubarraySumWithKadaneTest.java) | Kadane's algorithm — greedy running sum, no lookup structure | O(n) time, O(1) space |
| [`ContainsDuplicateWithHashMapTest`](src/test/java/org/example/ContainsDuplicateWithHashMapTest.java) | Hash map of value → last index, checked against a max distance | O(n) time, O(n) space |
| [`BinaryTreeLevelOrderWithBFSTest`](src/test/java/org/example/BinaryTreeLevelOrderWithBFSTest.java) | BFS with a queue — level-by-level, left to right | O(n) time, O(w) space |
| [`MaxDepthBinaryTreeWithBFSTest`](src/test/java/org/example/MaxDepthBinaryTreeWithBFSTest.java) | BFS, processing one full level per outer loop pass | O(n) time, O(w) space |

## Why this exists

The goal isn't to memorize solutions, but to build the habit of recognizing which data structure fits a problem's constraints — and to connect that recognition back to real-world backend scenarios (e.g. matching records without a nested loop).

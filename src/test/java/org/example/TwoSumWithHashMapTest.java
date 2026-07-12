package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Problem: Two Sum
 *
 * <p>Given an array of integers {@code nums} and an integer {@code target}, return the indices of
 * the two numbers such that they add up to {@code target}.
 *
 * <p>Assumptions for this exercise: exactly one valid pair exists, and the same element cannot be
 * used twice (no reusing the same index).
 *
 * <p>Naive approach: a nested loop comparing every pair costs O(n^2) — for each element, the
 * array is scanned again to find its complement.
 *
 * <p>Solution below: a single pass using a hash map, achieving O(n) time.
 *
 * <p>Reasoning: while iterating, we only need to know whether the complement of the current
 * number ({@code target - num}) has already been seen. A hash map gives O(1) lookup for "have I
 * seen this value before", removing the need for an inner loop entirely.
 *
 * <p>Key implementation detail: check for the complement BEFORE storing the current number in
 * the map. Storing first would let a number incorrectly match against itself.
 */
class TwoSumWithHashMapTest {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int complement = target - num;

            if (seen.containsKey(complement)) {
                return new int[] {seen.get(complement), i};
            }

            seen.put(num, i);
        }

        return new int[] {};
    }

    @Test
    void firstAndLastElement() {
        int[] result = twoSum(new int[] {2, 11, 15, 7}, 9);
        assertArrayEquals(new int[] {0, 3}, result);
    }

    @Test
    void firstAndSecondElement() {
        assertArrayEquals(new int[] {0, 1}, twoSum(new int[] {3, 7, 10, 5}, 10));
    }

    @Test
    void negativeNumbers() {
        assertArrayEquals(new int[] {0, 2}, twoSum(new int[] {-3, 1, 3, 5}, 0));
    }

    @Test
    void targetZeroWithTwoZeros() {
        assertArrayEquals(new int[] {0, 1}, twoSum(new int[] {0, 0, 1}, 0));
    }

    @Test
    void noSolutionReturnsEmptyArray() {
        assertArrayEquals(new int[] {}, twoSum(new int[] {1, 2, 3}, 100));
    }

    @Test
    void lastAndSecondToLastElement() {
        assertArrayEquals(new int[] {2, 3}, twoSum(new int[] {1, 5, 4, 6}, 10));
    }
}

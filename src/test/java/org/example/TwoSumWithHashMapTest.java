package org.example;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(result).isEqualTo(new int[] {0, 3});
    }

    @Test
    void firstAndSecondElement() {
        assertThat(twoSum(new int[] {3, 7, 10, 5}, 10)).isEqualTo(new int[] {0, 1});
    }

    @Test
    void negativeNumbers() {
        assertThat(twoSum(new int[] {-3, 1, 3, 5}, 0)).isEqualTo(new int[] {0, 2});
    }

    @Test
    void targetZeroWithTwoZeros() {
        assertThat(twoSum(new int[] {0, 0, 1}, 0)).isEqualTo(new int[] {0, 1});
    }

    @Test
    void noSolutionReturnsEmptyArray() {
        assertThat(twoSum(new int[] {1, 2, 3}, 100)).isEmpty();
    }

    @Test
    void lastAndSecondToLastElement() {
        assertThat(twoSum(new int[] {1, 5, 4, 6}, 10)).isEqualTo(new int[] {2, 3});
    }
}

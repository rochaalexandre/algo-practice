package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Problem: Maximum Subarray Sum (Kadane's Algorithm)
 *
 * <p>Given an array of integers {@code nums} (which may include negative numbers), find the
 * largest possible sum of a contiguous sub-array.
 *
 * <p>Naive approach: check every possible contiguous sub-array and sum it — O(n^2) (or O(n^3) if
 * the sum itself is recomputed from scratch for each sub-array instead of reused).
 *
 * <p>Solution below: Kadane's algorithm, a single pass keeping two running values: {@code
 * currentSum} (the best sum ending exactly at the current position) and {@code maxSum} (the best
 * sum seen anywhere so far). At each element, decide whether it's better to extend the existing
 * run ({@code currentSum + num}) or start a fresh run at the current element ({@code num} alone)
 * — whichever is larger. If the running sum ever drops below the value of the current element
 * alone, everything before it was dragging the sum down, so it's discarded.
 *
 * <p>Key implementation detail: {@code maxSum} must be initialized to {@code nums[0]}, not zero —
 * otherwise an array made up entirely of negative numbers would incorrectly report a max sum of
 * 0, when the correct answer is the least-negative single element.
 *
 * <p>This runs in O(n) time and O(1) extra space, since only two running values are tracked
 * regardless of array size.
 *
 * <p>Comparison with previously seen patterns: Two Sum (hash map) and Sliding Window both keep a
 * structure to look things up later — a map of seen values, or window boundaries. Kadane's
 * algorithm keeps no such structure. It only carries two numbers forward ({@code currentSum},
 * {@code maxSum}) and never looks back at earlier positions — a purely local decision at each
 * step ("extend or restart here?"), which is why it's considered a greedy approach (also treated
 * as a simple case of dynamic programming).
 */
class MaxSubarraySumWithKadaneTest {

    public int maxSubArray(int[] nums) {
        int currentSum = 0;
        int maxSum = nums[0];

        for (int num : nums) {
            int sum = currentSum + num;
            currentSum = Math.max(sum, num);

            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }

    @Test
    void mixedPositiveAndNegativeNumbers() {
        int result = maxSubArray(new int[] {-2, 3, 4, -1, 2, -5, 6});
        assertThat(result).isEqualTo(9);
    }

    @Test
    void singlePositiveElement() {
        int result = maxSubArray(new int[] {3});
        assertThat(result).isEqualTo(3);
    }

    @Test
    void singleNegativeElement() {
        int result = maxSubArray(new int[] {-3});
        assertThat(result).isEqualTo(-3);
    }

    @Test
    void allNegativeNumbers() {
        int result = maxSubArray(new int[] {-5, -3, -8});
        assertThat(result).isEqualTo(-3);
    }

    @Test
    void allPositiveNumbers() {
        int result = maxSubArray(new int[] {1, 2, 3, 4});
        assertThat(result).isEqualTo(10);
    }

    @Test
    void bestSubarrayInTheMiddle() {
        int result = maxSubArray(new int[] {-1, -2, 4, 5, -1, 8, -20});
        assertThat(result).isEqualTo(16);
    }
}

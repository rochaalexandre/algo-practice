package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Problem: Minimum Size Subarray Sum
 *
 * <p>Given an array of positive integers {@code nums} and a target value {@code target}, find
 * the length of the shortest contiguous sub-array whose sum is greater than or equal to {@code
 * target}. If no such sub-array exists, return 0.
 *
 * <p>Naive approach: check every possible contiguous sub-array and sum it, keeping the shortest
 * one that meets the target — O(n^2) (or O(n^3) if each sum is recomputed from scratch).
 *
 * <p>Solution below: a sliding window with two pointers, {@code start} and {@code i} (acting as
 * {@code end}). {@code i} walks the array once, always growing the window by adding {@code
 * nums[i]} to {@code currentSum}. Whenever {@code currentSum >= target}, the window {@code
 * [start, i]} is valid, so it shrinks from the left one step at a time — at every step of the
 * {@code while}, the current window is still valid, so its size is compared against the smallest
 * one seen so far <em>before</em> subtracting {@code nums[start]} and advancing {@code start}.
 *
 * <p>Key bug this exercise surfaced: measuring the window size only once, after the {@code while}
 * loop already exits, is wrong — by the time the loop condition becomes false, {@code start} has
 * already advanced one step too far, past the last valid window. The window size must be
 * recorded on every iteration <em>inside</em> the loop, while {@code currentSum >= target} still
 * holds, not just once after it stops holding.
 *
 * <p>Key implementation detail: the window length is {@code i - start + 1}, not {@code i -
 * start}. Both {@code start} and {@code i} are indices, and the window includes both endpoints,
 * so the raw index distance always undercounts by one.
 *
 * <p>Another key detail: {@code lowestSize} must be initialized to a value no real window can
 * reach — here {@code nums.length + 1} — rather than 0, so the first valid window found is
 * guaranteed to be registered. If it's never updated, no valid sub-array exists, and the method
 * returns 0 instead.
 *
 * <p>Because {@code start} only ever moves forward and each index is pushed and shrunk past at
 * most once, this runs in O(n) time and O(1) extra space.
 */
class MinSubarrayWithSlidingWindowTest {

    public int minSubArray(int[] nums, int target) {
        int initialValue = nums.length + 1;
        int lowestSize = initialValue;
        int start = 0;
        int currentSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            while (currentSum >= target) {
                lowestSize = Math.min(lowestSize, i - start + 1);
                currentSum -= nums[start];
                start++;
            }
        }
        return lowestSize == initialValue ? 0 : lowestSize;
    }

    @Test
    void findsShortestSubarrayMeetingTarget() {
        int result = minSubArray(new int[] {2, 3, 1, 2, 4, 3}, 7);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void noSubarrayReachesTarget() {
        int result = minSubArray(new int[] {1, 1, 1, 1}, 100);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void wholeArrayIsTheOnlyValidWindow() {
        int result = minSubArray(new int[] {1, 1, 1, 1}, 4);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void singleElementMeetsTargetExactly() {
        int result = minSubArray(new int[] {1, 4, 4}, 4);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void sumExactlyEqualsTarget() {
        int result = minSubArray(new int[] {1, 2, 3, 4, 5}, 15);
        assertThat(result).isEqualTo(5);
    }

    @Test
    void shortestWindowIsAtTheEnd() {
        int result = minSubArray(new int[] {1, 1, 1, 1, 10}, 9);
        assertThat(result).isEqualTo(1);
    }
}

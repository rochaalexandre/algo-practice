package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Problem: Two Sum II - Input Array Is Sorted
 *
 * <p>Given a sorted array of integers {@code nums} and an integer {@code target}, return the two
 * numbers such that they add up to {@code target}.
 *
 * <p>Assumptions for this exercise: exactly one valid pair exists (or none), and the same element
 * cannot be used twice.
 *
 * <p>Naive approach: same as the unsorted Two Sum — hash map, O(n) time, O(n) space. It works,
 * but it ignores the one extra piece of information this version of the problem gives us: the
 * array is sorted.
 *
 * <p>Solution below: two pointers, one at each end of the array. At each step, compare the sum
 * of both pointed values against the target:
 *
 * <ul>
 *   <li>sum == target → found the pair, stop.
 *   <li>sum &gt; target → the sum needs to shrink, so move the right pointer left (since the
 *       array is sorted ascending, moving the left pointer instead could only grow the sum
 *       further).
 *   <li>sum &lt; target → the sum needs to grow, so move the left pointer right.
 * </ul>
 *
 * <p>Stopping condition: either a match is found, or the two pointers meet (no valid pair
 * exists).
 *
 * <p>This achieves O(n) time with O(1) extra space, trading the hash map's memory cost for the
 * sortedness of the input.
 */
class TwoSumWithTwoPointersTest {

    public int[] twoSum(int[] nums, int target) {
        int pointStart = 0;
        int pointEnd = nums.length - 1;
        boolean matchesTarget = false;

        boolean keepRunning = true;
        while (keepRunning) {
            int start = nums[pointStart];
            int end = nums[pointEnd];
            int sum = start + end;
            matchesTarget = sum == target;

            if (matchesTarget || pointStart == pointEnd) {
                keepRunning = false;
            } else {
                if (sum > target) {
                    pointEnd--;
                }
                if (sum < target) {
                    pointStart++;
                }
            }
        }
        return matchesTarget ? new int[] {nums[pointStart], nums[pointEnd]} : new int[] {};
    }

    @Test
    void findNumbersThatSumMatchesTarget() {
        int[] result = twoSum(new int[] {1, 3, 4, 5, 7, 10}, 12);
        assertThat(result).isEqualTo(new int[] {5, 7});
    }

    @Test
    void firstAndLastElement() {
        int[] result = twoSum(new int[] {1, 2, 3, 4, 9}, 10);
        assertThat(result).isEqualTo(new int[] {1, 9});
    }

    @Test
    void adjacentElements() {
        int[] result = twoSum(new int[] {1, 2, 3, 4, 8}, 7);
        assertThat(result).isEqualTo(new int[] {3, 4});
    }

    @Test
    void negativeAndPositiveNumbers() {
        int[] result = twoSum(new int[] {-4, -1, 0, 3, 5}, 1);
        assertThat(result).isEqualTo(new int[] {-4, 5});
    }

    @Test
    void noSolutionReturnsEmptyArray() {
        int[] result = twoSum(new int[] {1, 2, 3, 4}, 100);
        assertThat(result).isEmpty();
    }

    @Test
    void twoElementArrayThatMatches() {
        int[] result = twoSum(new int[] {4, 8}, 12);
        assertThat(result).isEqualTo(new int[] {4, 8});
    }
}

package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Problem: Contains Duplicate II
 *
 * <p>Given an array of integers {@code nums} and an integer {@code k}, return whether there are
 * two distinct indices {@code i} and {@code j} such that {@code nums[i] == nums[j]} and {@code
 * |i - j| <= k}.
 *
 * <p>Naive approach: for every index, scan the rest of the array looking for a match within
 * range — O(n^2).
 *
 * <p>Solution below: a hash map of {@code value -> last seen index}, walked once. For each
 * number, if it was seen before, check whether the distance to that earlier index is already
 * {@code <= k} — if so, a valid pair is found. Either way (found or not), the map is updated to
 * the current index for that value.
 *
 * <p>Key reasoning: there's no need to keep the old index around if the distance check fails.
 * Since indices only move forward, an old occurrence that's already too far away can only get
 * farther away as the scan continues — it can never become useful again. Overwriting it with the
 * current index (which is always the closest, most recent occurrence of that value) is strictly
 * better for any future comparison.
 *
 * <p>This runs in O(n) time and O(n) space (bounded by the number of distinct values in the map).
 */
class ContainsDuplicateWithHashMapTest {

    public boolean find(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (seen.containsKey(num)) {
                int distance = i - seen.get(num);
                if (distance <= k) {
                    return true;
                }
            }
            seen.put(num, i);
        }
        return false;
    }

    @Test
    void duplicateWithinAllowedDistance() {
        boolean result = find(new int[] {1, 2, 3, 1}, 3);
        assertThat(result).isTrue();
    }

    @Test
    void duplicateBeyondAllowedDistance() {
        boolean result = find(new int[] {1, 2, 3, 1}, 2);
        assertThat(result).isFalse();
    }

    @Test
    void noDuplicatesAtAll() {
        boolean result = find(new int[] {1, 2, 3, 4}, 3);
        assertThat(result).isFalse();
    }

    @Test
    void adjacentDuplicates() {
        boolean result = find(new int[] {1, 1}, 1);
        assertThat(result).isTrue();
    }

    @Test
    void oldOccurrenceDiscardedInFavorOfCloserOne() {
        boolean result = find(new int[] {1, 2, 3, 1, 4, 1}, 2);
        assertThat(result).isTrue();
    }

    @Test
    void emptyArray() {
        boolean result = find(new int[] {}, 3);
        assertThat(result).isFalse();
    }
}

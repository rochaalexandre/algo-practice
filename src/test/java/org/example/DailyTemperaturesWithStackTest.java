package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Test;

/**
 * Problem: Daily Temperatures
 *
 * <p>Given an array of daily temperatures {@code temps}, return an array {@code answer} such
 * that {@code answer[i]} is the number of days you have to wait after day {@code i} to get a
 * warmer temperature. If there is no future day for which this is possible, {@code answer[i] ==
 * 0}.
 *
 * <p>Naive approach: for each day, scan forward until a warmer day is found — O(n^2), and it
 * repeats comparisons that a smarter pass could resolve once.
 *
 * <p>Solution below: a monotonic stack of days that are still "waiting" for a warmer day.
 * Walking the array once, left to right, whenever the current temperature is warmer than the
 * temperature at the top of the stack, that stacked day just found its answer — pop it and
 * record how many days it waited ({@code i - stackedDay.index}). Keep popping while the new
 * day beats the new top of the stack; a single day can resolve several stacked days at once
 * (e.g. one warm day closing out multiple colder days in a row). Once the stack no longer beats
 * the current day (or is empty), push the current day — it is now the one waiting.
 *
 * <p>Because every day is pushed once and popped at most once, the total work across the whole
 * array is O(n), even though there's a loop inside a loop.
 *
 * <p>Days that are never resolved (no warmer day ahead) are left untouched in the result array,
 * which defaults to 0 — matching the problem's expected output for that case.
 */
public class DailyTemperaturesWithStackTest {

    static class DailyTemp {
        int temp;
        int index;

        public DailyTemp(int temp, int index) {
            this.temp = temp;
            this.index = index;
        }
    }

    public int[] getWaitingDays(int[] temps) {
        int[] answer = new int[temps.length];
        Deque<DailyTemp> stack = new ArrayDeque<>();

        for (int i = 0; i < temps.length; i++) {
            int temp = temps[i];
            boolean continueLoop = true;
            while (continueLoop) {
                DailyTemp dailyTemp = stack.peek();
                if (dailyTemp != null && dailyTemp.temp < temp) {
                    answer[dailyTemp.index] = i - dailyTemp.index;
                    stack.pop();
                } else {
                    continueLoop = false;
                }
            }
            stack.add(new DailyTemp(temp, i));
        }
        return answer;
    }

    @Test
    void findWaitingDays() {
        int[] result = getWaitingDays(new int[] {73, 74, 75, 71, 69, 72, 76, 73});
        assertThat(result).isEqualTo(new int[] {1, 1, 4, 3, 2, 1, 0, 0});
    }

    @Test
    void allSameTemperatureNeverWarmsUp() {
        int[] result = getWaitingDays(new int[] {70, 70, 70, 70});
        assertThat(result).isEqualTo(new int[] {0, 0, 0, 0});
    }

    @Test
    void strictlyDecreasingTemperatureNeverWarmsUp() {
        int[] result = getWaitingDays(new int[] {80, 75, 70, 65, 60});
        assertThat(result).isEqualTo(new int[] {0, 0, 0, 0, 0});
    }

    @Test
    void stackHoldsSeveralDaysBeforePopping() {
        int[] result = getWaitingDays(new int[] {63, 62, 61, 66, 90});
        assertThat(result).isEqualTo(new int[] {3, 2, 1, 1, 0});
    }
}

package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Problem: Longest Substring Without Repeating Characters
 *
 * <p>Given a string {@code input}, find the length (represented here by the substring itself)
 * of the longest substring without repeating characters.
 *
 * <p>Naive approach: for every possible starting index, expand forward and check for repeats
 * character by character, comparing each new char against every char already in the current
 * substring — O(n^3) in the worst case (O(n^2) substrings, each check up to O(n)).
 *
 * <p>Solution below: a sliding window with two pointers, {@code startPoint} and {@code endPoint}.
 * {@code endPoint} walks the string once, left to right, always growing the window by one
 * character at a time (a plain {@code for} loop, since its movement is fixed and predictable).
 * {@code startPoint} only jumps forward when a repeated character is found <em>inside the
 * current window</em> — it moves to just past the character's previous occurrence, not just one
 * step, so no still-unique characters in between are lost.
 *
 * <p>A hash map ({@code seen}) tracks, for every character, the last index where it appeared.
 * Because the map holds indices from anywhere in the string's history (not just the current
 * window), a stored index only counts as a real repeat if it is {@code >= startPoint} — anything
 * before that has already fallen outside the window and no longer matters.
 *
 * <p>At every step, after resolving any repeat, the current window's length is compared against
 * the longest one found so far. Since {@code endPoint} is pushed and {@code startPoint} only
 * moves forward (never backward), each index is visited a bounded number of times, giving O(n)
 * time overall.
 */
public class LongestSubstringWithSlidingWindowTest {


    private String getLongestSubString(String input) {
        char[] inputChars = input.toCharArray();
        int startPoint=0;
        String result="";
        Map<Character, Integer> seen = new HashMap<>();
        for (int endPoint = 0; endPoint < inputChars.length; endPoint++) {
            char atual = inputChars[endPoint];

            if (seen.containsKey(atual) && seen.get(atual) >= startPoint) {
                startPoint = seen.get(atual) + 1;
            }

            String subValue = input.substring(startPoint, endPoint + 1);

            if (subValue.length() > result.length()) {
                result = subValue;
            }

            seen.put(atual, endPoint);
        }

        return result;
    }

    @Test
    void findLongestSubString() {
        var result = getLongestSubString("abcabcbb");
        assertThat(result).isEqualTo("abc");
    }

    @Test
    void emptyString() {
        var result = getLongestSubString("");
        assertThat(result).isEqualTo("");
    }

    @Test
    void singleCharacter() {
        var result = getLongestSubString("a");
        assertThat(result).isEqualTo("a");
    }

    @Test
    void allCharactersRepeated() {
        var result = getLongestSubString("bbbbb");
        assertThat(result).isEqualTo("b");
    }

    @Test
    void noRepeatingCharacters() {
        var result = getLongestSubString("abcdef");
        assertThat(result).isEqualTo("abcdef");
    }

    @Test
    void repeatInTheMiddleResetsWindow() {
        var result = getLongestSubString("pwwkew");
        assertThat(result).isEqualTo("wke");
    }

}

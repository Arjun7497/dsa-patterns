package Strings;

/**
 * Problem: Length of Last Word (LeetCode 58)
 * Pattern: Two Pointers / Reverse Scan
 * URL: <a href="https://leetcode.com/problems/length-of-last-word/">...</a>
 * <p>
 * Problem Statement:
 * Given a string s consisting of words and spaces, return the length of the
 * last word in the string. A word is a maximal substring of non-space characters.
 * <p>
 * Example:
 * Input:  "Hello World"
 * Output: 5
 * <p>
 * Input:  "   fly me   to   the moon  "
 * Output: 4
 * <p>
 * This file contains three approaches:
 *   1. Split on spaces, take the last word        O(n) time, O(n) space
 *   2. Forward scan, track the current word length O(n) time, O(1) space
 *   3. Reverse scan, skip trailing spaces (optimal) O(n) time, O(1) space
 */
public class LengthOfLastWord {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Split, Then Take the Last Word)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Splitting scans the whole string.
    // Space Complexity: O(n) for the array of words.
    //
    // Idea: split the string on runs of spaces, ignore empty pieces,
    // and return the length of the final word.
    //
    // Why NOT optimal: split builds every word as a new String, even
    // though we only need the length of the last one.
    // ─────────────────────────────────────────────────────────────
    public int lengthOfLastWordSplit(String s) {
        String[] words = s.trim().split("\\s+");
        return words[words.length - 1].length();
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Forward Scan (Track the Current Word Length)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass over the string.
    // Space Complexity: O(1) extra.
    //
    // Idea: walk left to right. Each non-space character extends the
    // current word. Each space resets the count to zero, but only when
    // the previous character was a letter, so the stored length remains
    // the most recent completed word.
    //
    // Trade-off: this reads the whole string even when the answer is
    // found near the end, and it needs careful reset logic.
    // ─────────────────────────────────────────────────────────────
    public int lengthOfLastWordForward(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                length++;
            } else if (length > 0) {
                lastLength = length;
                length = 0;
            }
        }
        return length > 0 ? length : lastLength;
    }

    private int lastLength = 0;

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Reverse Scan, Skip Trailing Spaces)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n) in the worst case, but it stops as soon as
    // the last word ends, so it often reads far less than the whole string.
    // Space Complexity: O(1) extra.
    //
    // Idea: start at the end. Skip any trailing spaces first. Then count
    // non-space characters until you reach a space or the start of the
    // string. That count is the length of the last word.
    //
    // This is the version to write in an interview. It needs no extra
    // memory and no string copies.
    // ─────────────────────────────────────────────────────────────
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        int length = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }
        return length;
    }
}
package TwoPointers;

/**
 * Problem: Merge Strings Alternately (LeetCode 1768)
 * Pattern: Two Pointers (Parallel Traversal, Alternate Writes)
 * URL: <a href="https://leetcode.com/problems/merge-strings-alternately/">...</a>
 * <p>
 * Problem Statement:
 * You are given two strings word1 and word2. Merge them by adding letters in
 * alternating order, starting with word1. If a string is longer than the other,
 * append the additional letters onto the end of the merged string.
 * <p>
 * Example:
 * Input:  word1 = "abc", word2 = "pqrstu"
 * Output: "apbqcrstu"
 * <p>
 * This file contains three approaches:
 *   1. Naive string concatenation          O(m + n) time, O(m + n) space, but O(n^2) copying
 *   2. Two pointers, StringBuilder          O(m + n) time, O(m + n) space
 *   3. Two pointers, single pass char[]     O(m + n) time, O(m + n) space (optimal constant factors)
 */
public class MergeStringsAlternately {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Naive (String Concatenation)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O((m + n)^2) because each += copies the whole string so far.
    // Space Complexity: O(m + n) for the result, plus temporary strings.
    //
    // Simple to read, but Java strings are immutable, so repeated
    // concatenation is expensive. Kept here as a baseline.
    // ─────────────────────────────────────────────────────────────
    public String mergeAlternatelyNaive(String word1, String word2) {
        String result = "";
        int m = word1.length();
        int n = word2.length();

        for (int i = 0; i < Math.max(m, n); i++) {
            if (i < m) {
                result += word1.charAt(i);
            }
            if (i < n) {
                result += word2.charAt(i);
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Single Index With Preallocated StringBuilder
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(m + n). One pass, one append per character.
    // Space Complexity: O(m + n) for the builder.
    //
    // Both strings advance at the same rate, so one index is enough.
    // Passing the capacity up front avoids repeated internal reallocation.
    // This is the most readable option and the one to write in an interview.
    // ─────────────────────────────────────────────────────────────
    public String mergeAlternatelyBuilder(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        StringBuilder result = new StringBuilder(m + n);

        for (int i = 0; i < Math.max(m, n); i++) {
            if (i < m) {
                result.append(word1.charAt(i));
            }
            if (i < n) {
                result.append(word2.charAt(i));
            }
        }
        return result.toString();
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Single Pass, Preallocated char Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(m + n). One write per character.
    // Space Complexity: O(m + n) for the char array.
    //
    // The output length is known in advance, so each character is written
    // directly into its final slot. This gives the most direct control over
    // memory, with no builder bookkeeping. Performance is similar to Approach 2.
    // ─────────────────────────────────────────────────────────────
    public String mergeAlternatelyCharArray(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        char[] result = new char[m + n];
        int pos = 0;

        for (int i = 0; i < Math.max(m, n); i++) {
            if (i < m) {
                result[pos++] = word1.charAt(i);
            }
            if (i < n) {
                result[pos++] = word2.charAt(i);
            }
        }
        return new String(result);
    }
}

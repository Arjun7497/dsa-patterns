package Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Valid Anagram (LeetCode 242)
 * Pattern: Frequency Counting
 * URL: <a href="https://leetcode.com/problems/valid-anagram/">...</a>
 * <p>
 * Problem Statement:
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * An anagram is a word formed by rearranging the letters of another word,
 * using every original letter exactly once.
 * <p>
 * Example:
 * Input:  s = "anagram", t = "nagaram"
 * Output: true
 * <p>
 * Input:  s = "rat", t = "car"
 * Output: false
 * <p>
 * This file contains three approaches:
 *   1. Sort both strings and compare          O(n log n) time, O(n) space
 *   2. Hash map of character counts           O(n) time, O(k) space (k = distinct chars)
 *   3. Fixed array of 26 counts (optimal)     O(n) time, O(1) space
 */
public class ValidAnagram {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Sort Both Strings)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(n) for the character arrays Java creates when sorting.
    //
    // Idea: two strings are anagrams exactly when their sorted forms match.
    // Sorting puts identical letters next to each other, so a direct
    // comparison is enough.
    //
    // Why NOT optimal: sorting does far more work than we need. We only
    // care how many times each letter appears, not their order.
    // ─────────────────────────────────────────────────────────────
    public boolean isAnagramSorting(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Hash Map of Character Counts
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to count, one pass to subtract.
    // Space Complexity: O(k), where k is the number of distinct characters.
    //
    // Idea: count every character in s, then remove one count for each
    // character in t. If t is an anagram, every count ends at zero. A
    // missing key or a negative count means the strings differ.
    //
    // This version works for any characters, including Unicode, so it is
    // the safest choice when the alphabet is not guaranteed.
    // ─────────────────────────────────────────────────────────────
    public boolean isAnagramHashMap(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        for (char c : t.toCharArray()) {
            int count = freq.getOrDefault(c, 0);
            if (count == 0) {
                return false;
            }
            freq.put(c, count - 1);
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Fixed Array of 26 Counts)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass over each string.
    // Space Complexity: O(1), since the array size is fixed at 26.
    //
    // Idea: the input is lowercase English letters, so each letter maps to
    // an index 0 to 25 via c - 'a'. Increment for s, decrement for t. If the
    // strings are anagrams, every slot returns to zero, and the length check
    // rules out most mismatches immediately.
    //
    // Trade-off: this relies on the lowercase-letters constraint. If the
    // input may contain other characters, use Approach 2.
    // ─────────────────────────────────────────────────────────────
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}
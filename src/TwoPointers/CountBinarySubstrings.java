package TwoPointers;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem: Count Binary Substrings (LeetCode 696)
 * Pattern: Two Pointers (Run Lengths, Compare Adjacent Groups)
 * URL: <a href="https://leetcode.com/problems/count-binary-substrings/">...</a>
 * <p>
 * Problem Statement:
 * Given a binary string s, return the number of non-empty substrings that have
 * the same number of 0's and 1's, and where all the 0's and all the 1's in the
 * substring are grouped consecutively. Substrings that occur multiple times are
 * counted each time they occur.
 * <p>
 * Example:
 * Input:  s = "00110011"
 * Output: 6
 * Explanation: "0011", "01", "1100", "10", "0011", and "01" are valid.
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every substring)        O(n^3) time, O(1) space
 *   2. Group lengths in an array, sum the mins    O(n) time, O(n) space
 *   3. Two pointers over adjacent run lengths     O(n) time, O(1) space
 */
public class CountBinarySubstrings {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Substring)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^3). There are O(n^2) substrings, and checking
    // each one takes O(n).
    // Space Complexity: O(1). Only loop variables.
    //
    // A valid substring has the form 0^k 1^k or 1^k 0^k. Check each
    // substring directly against that shape. Correct, but it re-scans
    // characters that the structure of the string already tells us about.
    // ─────────────────────────────────────────────────────────────
    public int countBinarySubstringsBruteForce(String s) {
        int n = s.length();
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j += 2) {
                if (isValid(s, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // A substring is valid when its length is even, its first half is all one
    // character, and its second half is all the other character.
    private boolean isValid(String s, int start, int end) {
        int len = end - start + 1;
        int half = len / 2;
        char first = s.charAt(start);
        for (int k = 0; k < half; k++) {
            if (s.charAt(start + k) != first) {
                return false;
            }
        }
        for (int k = half; k < len; k++) {
            if (s.charAt(start + k) == first) {
                return false;
            }
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Group Lengths in an Array, Sum the Mins
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to build the groups, then one pass over them.
    // Space Complexity: O(n) for the list of group lengths.
    //
    // Idea: split the string into runs of identical characters. A valid
    // substring spans exactly two adjacent runs and takes the same number of
    // characters from each. Between runs of length a and b, there are
    // min(a, b) valid substrings. Summing over all adjacent pairs gives the answer.
    // ─────────────────────────────────────────────────────────────
    public int countBinarySubstringsGroups(String s) {
        List<Integer> groups = new ArrayList<>();
        int run = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                run++;
            } else {
                groups.add(run);
                run = 1;
            }
        }
        groups.add(run);

        int count = 0;
        for (int i = 0; i + 1 < groups.size(); i++) {
            count += Math.min(groups.get(i), groups.get(i + 1));
        }
        return count;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Two Pointers Over Adjacent Run Lengths)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the string.
    // Space Complexity: O(1). Only the previous run, the current run, and the total.
    //
    // Idea: the same as Approach 2, but we never store the groups. prev is the
    // length of the run before the current one, and cur is the length of the
    // run being read. When a run ends, add min(prev, cur) and shift cur into prev.
    // The first run has prev = 0, so it contributes nothing, which is correct.
    // ─────────────────────────────────────────────────────────────
    public int countBinarySubstrings(String s) {
        int prev = 0;
        int cur = 1;
        int count = 0;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                count += Math.min(prev, cur);
                prev = cur;
                cur = 1;
            }
        }
        count += Math.min(prev, cur);
        return count;
    }
}

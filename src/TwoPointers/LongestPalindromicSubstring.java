package TwoPointers;

/**
 * Problem: Longest Palindromic Substring (LeetCode 5)
 * Pattern: Two Pointers (Expand Outward From a Center)
 * URL: <a href="https://leetcode.com/problems/longest-palindromic-substring/">...</a>
 * <p>
 * Problem Statement:
 * Given a string s, return the longest palindromic substring in s.
 * <p>
 * Example:
 * Input:  s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every substring)          O(n^3) time, O(1) space
 *   2. Dynamic programming table                    O(n^2) time, O(n^2) space
 *   3. Expand around each center                    O(n^2) time, O(1) space
 */
public class LongestPalindromicSubstring {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Substring)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^3). There are O(n^2) substrings, and checking
    // each one for a palindrome takes O(n).
    // Space Complexity: O(1). Only indices and a few variables.
    //
    // Try every start and end pair, and keep the longest one that reads the
    // same in both directions. Correct and easy to verify, but it repeats
    // palindrome checks that overlap heavily.
    // ─────────────────────────────────────────────────────────────
    public String longestPalindromeBruteForce(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }
        int bestStart = 0;
        int bestLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int len = j - i + 1;
                if (len > bestLen && isPalindrome(s, i, j)) {
                    bestStart = i;
                    bestLen = len;
                }
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    // Two pointers moving inward from both ends of s[l..r].
    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Dynamic Programming Table
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). Each pair (i, j) is filled once in O(1).
    // Space Complexity: O(n^2) for the boolean table.
    //
    // Let dp[i][j] be true when s[i..j] is a palindrome. It is one when
    // s[i] == s[j] and the inner part s[i+1..j-1] is a palindrome (or has
    // length 2 or less). Fill the table by increasing length, so the inner
    // entry is always ready before it is needed.
    // ─────────────────────────────────────────────────────────────
    public String longestPalindromeDP(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }
        boolean[][] dp = new boolean[n][n];
        int bestStart = 0;
        int bestLen = 1;

        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && (len <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    if (len > bestLen) {
                        bestStart = i;
                        bestLen = len;
                    }
                }
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Expand Around Each Center)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). There are 2n - 1 centers, and each expansion
    // takes O(n) in the worst case.
    // Space Complexity: O(1). Only a few indices.
    //
    // Idea: every palindrome mirrors around its center. An odd-length palindrome
    // is centered on a character, and an even-length one is centered between two
    // characters. For each center, expand left and right while the characters
    // match, then keep the longest span found.
    // ─────────────────────────────────────────────────────────────
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }
        int bestStart = 0;
        int bestLen = 1;

        for (int i = 0; i < n; i++) {
            int odd = expandAroundCenter(s, i, i);
            int even = expandAroundCenter(s, i, i + 1);
            int len = Math.max(odd, even);

            if (len > bestLen) {
                bestLen = len;
                bestStart = i - (len - 1) / 2;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    // Expands while the outer characters match, then returns the length of
    // the palindrome found. Starts with l == r for odd, or l + 1 == r for even.
    private int expandAroundCenter(String s, int l, int r) {
        int n = s.length();
        while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return r - l - 1;
    }
}

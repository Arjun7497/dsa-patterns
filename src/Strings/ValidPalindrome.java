package Strings;

/**
 * Problem: Valid Palindrome (LeetCode 125)
 * Pattern: Two Pointers (Opposite Ends, Skip Non-Alphanumeric)
 * URL: <a href="https://leetcode.com/problems/valid-palindrome/">...</a>
 * <p>
 * Problem Statement:
 * A phrase is a palindrome if, after converting all uppercase letters to
 * lowercase and removing all non-alphanumeric characters, it reads the same
 * forward and backward. Given a string s, return true if it is a palindrome,
 * and false otherwise.
 * <p>
 * Example:
 * Input:  "A man, a plan, a canal: Panama"
 * Output: true
 * <p>
 * Input:  "race a car"
 * Output: false
 * <p>
 * This file contains three approaches:
 *   1. Clean the string, then compare it with its reverse   O(n) time, O(n) space
 *   2. Clean into a char array, then two pointers           O(n) time, O(n) space
 *   3. Two pointers on the original string (optimal)        O(n) time, O(1) space
 */
public class ValidPalindrome {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Clean, Then Compare With Reverse)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Cleaning, reversing, and comparing are each linear.
    // Space Complexity: O(n) for the cleaned string and its reversed copy.
    //
    // Idea: build a lowercase string with only letters and digits. A palindrome
    // is exactly a string equal to its own reverse, so compare the two.
    //
    // Why NOT optimal: the cleaned string and its reverse are both full copies
    // of the input, even though we only need to compare characters pairwise.
    // ─────────────────────────────────────────────────────────────
    public boolean isPalindromeCleanThenReverse(String s) {
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        String forward = cleaned.toString();
        String backward = cleaned.reverse().toString();
        return forward.equals(backward);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Clean Into an Array, Then Two Pointers
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to clean, one pass to compare.
    // Space Complexity: O(n) for the cleaned character array.
    //
    // Idea: filter the input into a char array first, so the two-pointer
    // comparison works on a string where every character matters. Then
    // compare the ends and move inward.
    //
    // This is easier to reason about than Approach 3, but it still allocates
    // a copy of the input just to skip characters we could have skipped directly.
    // ─────────────────────────────────────────────────────────────
    public boolean isPalindromeCleanedArray(String s) {
        char[] cleaned = new char[s.length()];
        int size = 0;
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned[size++] = Character.toLowerCase(c);
            }
        }
        int left = 0;
        int right = size - 1;
        while (left < right) {
            if (cleaned[left] != cleaned[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Two Pointers, Skip Characters In Place)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each pointer moves inward at most n times in total.
    // Space Complexity: O(1). Only two indices are used.
    //
    // Idea: place one pointer at the start and one at the end. Skip any character
    // that is not alphanumeric on either side. When both pointers rest on valid
    // characters, compare them case-insensitively. A mismatch means the answer is
    // false. If the pointers cross without a mismatch, the answer is true.
    //
    // This is the version to write in an interview. It filters and compares in
    // one pass without creating any new string.
    // ─────────────────────────────────────────────────────────────
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !isAlphanumeric(s.charAt(left))) {
                left++;
            }
            while (left < right && !isAlphanumeric(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isAlphanumeric(char c) {
        return Character.isLetterOrDigit(c);
    }
}
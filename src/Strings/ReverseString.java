package Strings;

/**
 * Problem: Reverse String (LeetCode 344)
 * Pattern: Two Pointers (Opposite Ends)
 * URL: <a href="https://leetcode.com/problems/reverse-string/">...</a>
 * <p>
 * Problem Statement:
 * Write a function that reverses a character array s in place.
 * You must do this by modifying the input array with O(1) extra memory.
 * <p>
 * Example:
 * Input:  ['h', 'e', 'l', 'l', 'o']
 * Output: ['o', 'l', 'l', 'e', 'h']
 * <p>
 * Input:  ['H', 'a', 'n', 'n', 'a', 'h']
 * Output: ['h', 'a', 'n', 'n', 'a', 'H']
 * <p>
 * This file contains three approaches:
 *   1. Copy into a new array, then write back    O(n) time, O(n) space
 *   2. Recursion, swapping outer pair inward     O(n) time, O(n) call stack
 *   3. Two pointers, swap from both ends (optimal) O(n) time, O(1) space
 */
public class ReverseString {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Copy Into a New Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to copy, one pass to write back.
    // Space Complexity: O(n) for the temporary copy.
    //
    // Idea: read the characters from the end and store them in a new
    // array in that order. Then copy the reversed result back into s.
    //
    // Why NOT optimal: the problem requires O(1) extra memory, and this
    // allocates a full second array. It is correct, but it breaks the
    // in-place constraint.
    // ─────────────────────────────────────────────────────────────
    public void reverseStringCopy(char[] s) {
        int n = s.length;
        char[] reversed = new char[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = s[n - 1 - i];
        }
        for (int i = 0; i < n; i++) {
            s[i] = reversed[i];
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Recursion (Swap the Outer Pair, Then Recurse Inward)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each character is swapped exactly once.
    // Space Complexity: O(n) for the recursion stack, one frame per pair.
    //
    // Idea: swap the first and last characters, then reverse the
    // middle section by calling the same function on the inner range.
    // The base case is an empty or single-character range.
    //
    // Trade-off: the logic reads cleanly, but the call stack grows with
    // the input length. Very long arrays can overflow the stack, so the
    // iterative version is safer in production code.
    // ─────────────────────────────────────────────────────────────
    public void reverseStringRecursive(char[] s) {
        reverseRange(s, 0, s.length - 1);
    }

    private void reverseRange(char[] s, int left, int right) {
        if (left >= right) {
            return;
        }
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        reverseRange(s, left + 1, right - 1);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Two Pointers, Swap From Both Ends)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each swap places two characters in final position.
    // Space Complexity: O(1). Only two indices and one temporary variable.
    //
    // Idea: put one pointer at the start and one at the end. Swap the
    // characters they point to, then move both inward. When the pointers
    // meet or cross, every character has reached its mirror position.
    //
    // This is the version to write in an interview. It satisfies the
    // in-place, constant-memory requirement directly.
    // ─────────────────────────────────────────────────────────────
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}
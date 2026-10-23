package TwoPointers;

import java.util.Arrays;

/**
 * Problem: Squares of a Sorted Array (LeetCode 977)
 * Pattern: Two Pointers (Opposite Ends, Fill From the Back)
 * URL: <a href="https://leetcode.com/problems/squares-of-a-sorted-array/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums sorted in non-decreasing order, return an array of
 * the squares of each number, also sorted in non-decreasing order.
 * <p>
 * Example:
 * Input:  [-4, -1, 0, 3, 10]
 * Output: [0, 1, 9, 16, 100]
 * <p>
 * This file contains three approaches:
 *   1. Square each value, then sort       O(n log n) time, O(n) space
 *   2. Two pointers, build from the front  O(n) time, O(n) space (extra array)
 *   3. Two pointers, fill from the back    O(n) time, O(n) space (output only)
 */
public class SquaresOfSortedArray {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Square, Then Sort)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(n) for the output array (plus sort overhead).
    //
    // Why NOT optimal: the input is already sorted, so sorting again
    // throws away that structure. Squaring makes negatives large, which
    // is why the order breaks, but the two ends still hold the largest squares.
    // ─────────────────────────────────────────────────────────────
    public int[] sortedSquaresBruteForce(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = nums[i] * nums[i];
        }
        Arrays.sort(result);
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Two Pointers, Merge Into a Front-Filled Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each step consumes one element from either end.
    // Space Complexity: O(n) for the output array.
    //
    // Idea: the largest square is at one of the two ends, since the values
    // closest to zero have the smallest squares. Compare the two ends, and
    // place the smaller square at the next free front position of the result.
    // This is the merge step of merge sort, applied to two sorted halves.
    // ─────────────────────────────────────────────────────────────
    public int[] sortedSquaresFrontFill(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;
        int pos = 0;

        while (left <= right) {
            int leftSq = nums[left] * nums[left];
            int rightSq = nums[right] * nums[right];
            if (leftSq > rightSq) {
                result[pos++] = leftSq;
                left++;
            } else {
                result[pos++] = rightSq;
                right--;
            }
        }

        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Two Pointers, Fill From the Back)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass with two pointers.
    // Space Complexity: O(n) for the output only, with no extra structures.
    //
    // Idea: the largest square is always at one end, so place it at the last
    // free position of the result and move that pointer inward. Filling from
    // the back means each write is final, so no value is ever moved.
    // ─────────────────────────────────────────────────────────────
    public int[] sortedSquaresOptimal(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;

        for (int pos = n - 1; pos >= 0; pos--) {
            int leftSq = nums[left] * nums[left];
            int rightSq = nums[right] * nums[right];
            if (leftSq > rightSq) {
                result[pos] = leftSq;
                left++;
            } else {
                result[pos] = rightSq;
                right--;
            }
        }

        return result;
    }
}
package TwoPointers;

/**
 * Problem: Two Sum II - Input Array Is Sorted (LeetCode 167)
 * Pattern: Two Pointers (Opposite Ends, Move Based on Sum)
 * URL: <a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/">...</a>
 * <p>
 * Problem Statement:
 * Given a 1-indexed array of integers numbers that is already sorted in
 * non-decreasing order, find two numbers such that they add up to a specific
 * target. Return the indices of the two numbers, index1 and index2, each
 * increased by one, where 1 <= index1 < index2 <= numbers.length. Use only
 * constant extra space. Exactly one solution is guaranteed.
 * <p>
 * Example:
 * Input:  numbers = [2, 7, 11, 15], target = 9
 * Output: [1, 2]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every pair)           O(n^2) time, O(1) space
 *   2. Binary search for each complement        O(n log n) time, O(1) space
 *   3. Two pointers from both ends              O(n) time, O(1) space
 */
public class TwoSumIIInputArrayIsSorted {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Pair)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). Every pair (i, j) is checked once.
    // Space Complexity: O(1). Only loop variables.
    //
    // The sorted order is ignored entirely. Simple and correct, but it
    // does far more work than the structure of the input requires.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSumBruteForce(int[] numbers, int target) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i + 1, j + 1}; // convert to 1-indexed
                }
            }
        }
        return new int[]{}; // unreachable under the problem guarantee
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Binary Search for Each Complement
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). For each element, a binary search costs O(log n).
    // Space Complexity: O(1). Only a few integer variables.
    //
    // Idea: for each numbers[i], the partner must be target - numbers[i].
    // Because the array is sorted, binary search can find that partner
    // in the range after i. This uses the sorted order, but it still
    // repeats a search for every element.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSumBinarySearch(int[] numbers, int target) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            int complement = target - numbers[i];
            int low = i + 1;
            int high = n - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (numbers[mid] == complement) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] < complement) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return new int[]{}; // unreachable under the problem guarantee
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Two Pointers From Both Ends)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each step moves one pointer inward, so at most n steps.
    // Space Complexity: O(1). Only two pointer variables.
    //
    // Idea: start with the smallest value (left) and the largest value (right).
    // If the sum is too small, the only way to grow it is to move left forward,
    // since every other option is already at its largest on the right. If the sum
    // is too large, move right backward. Any pair skipped this way cannot be the answer.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1}; // convert to 1-indexed
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{}; // unreachable under the problem guarantee
    }
}
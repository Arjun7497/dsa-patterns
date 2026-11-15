package PrefixSum;

/**
 * Problem: Find Pivot Index (LeetCode 724)
 * Pattern: Prefix Sum / Running Totals
 * URL: <a href="https://leetcode.com/problems/find-pivot-index/">...</a>
 * <p>
 * Problem Statement:
 * Given an array of integers nums, return the leftmost pivot index, where the
 * sum of all numbers strictly to the left equals the sum of all numbers strictly
 * to the right. If no such index exists, return -1.
 * <p>
 * Example:
 * Input:  [1, 7, 3, 6, 5, 6]
 * Output: 3
 * Left of index 3:  1 + 7 + 3 = 11
 * Right of index 3: 5 + 6     = 11
 * <p>
 * This file contains three approaches:
 *   1. Brute force: sum both sides for each index   O(n^2) time, O(1) extra space
 *   2. Prefix sum array                              O(n) time, O(n) space
 *   3. Running left sum with total sum               O(n) time, O(1) extra space
 */
public class FindPivotIndex {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Sum Both Sides at Each Index)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each index we scan up to n elements.
    // Space Complexity: O(1) extra.
    //
    // Why NOT optimal: the left and right sums for index i+1 differ from
    // those for index i by only one element, yet we recompute them fully.
    // ─────────────────────────────────────────────────────────────
    public int pivotIndexBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int leftSum = 0;
            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }
            int rightSum = 0;
            for (int j = i + 1; j < n; j++) {
                rightSum += nums[j];
            }
            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Prefix Sum Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to build the prefix sums, one to check.
    // Space Complexity: O(n) for the prefix array.
    //
    // Idea: prefix[i] is the sum of nums[0..i-1], so the left sum of index i
    // is prefix[i]. The total is prefix[n]. The right sum of index i is
    // prefix[n] - prefix[i + 1]. A pivot exists where these two are equal.
    // ─────────────────────────────────────────────────────────────
    public int pivotIndexPrefix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        for (int i = 0; i < n; i++) {
            int leftSum = prefix[i];
            int rightSum = prefix[n] - prefix[i + 1];
            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Running Left Sum and Total Sum)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to compute the total, one to scan.
    // Space Complexity: O(1) extra.
    //
    // Idea: the right sum at index i is total - leftSum - nums[i], because
    // the total is the left part, the element itself, and the right part.
    // We never need the right side stored, since it is derived from the total.
    // ─────────────────────────────────────────────────────────────
    public int pivotIndex(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int rightSum = total - leftSum - nums[i];
            if (leftSum == rightSum) {
                return i;
            }
            leftSum += nums[i];
        }
        return -1;
    }
}
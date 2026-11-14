package PrefixSum;

/**
 * Problem: Running Sum of 1d Array (LeetCode 1480)
 * Pattern: Prefix Sum
 * URL: <a href="https://leetcode.com/problems/running-sum-of-1d-array/">...</a>
 * <p>
 * Problem Statement:
 * Given an array nums, the running sum is defined as
 * runningSum[i] = nums[0] + nums[1] + ... + nums[i].
 * Return the running sum of nums.
 * <p>
 * Example:
 * Input:  [1, 2, 3, 4]
 * Output: [1, 3, 6, 10]
 * <p>
 * This file contains three approaches:
 *   1. Brute force: re-sum the prefix for each index   O(n^2) time, O(1) extra space
 *   2. Running total into a new array                  O(n) time, O(n) space
 *   3. Running total in place                          O(n) time, O(1) extra space
 */
public class RunningSumOf1DArray {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Re-Sum the Prefix Each Time)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For index i we add i + 1 elements.
    // Space Complexity: O(1) extra, not counting the output array.
    //
    // Why NOT optimal: runningSum[i - 1] already holds the sum of
    // nums[0..i-1]. Recomputing it from scratch repeats work we
    // have already done.
    // ─────────────────────────────────────────────────────────────
    public int[] runningSumBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += nums[j];
            }
            result[i] = sum;
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Running Total Into a New Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass, each step reuses the previous total.
    // Space Complexity: O(n) for the result array.
    //
    // Idea: result[i] = result[i - 1] + nums[i]. The input is untouched,
    // which makes this the safest version when the caller still needs nums.
    // ─────────────────────────────────────────────────────────────
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        result[0] = nums[0];
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] + nums[i];
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Running Total In Place)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass.
    // Space Complexity: O(1) extra, since nums becomes the result.
    //
    // Idea: nums[i - 1] already holds the running sum up to i - 1 when
    // we reach index i, so adding it to nums[i] gives the running sum up to i.
    //
    // Trade-off: the input array is modified. If the caller needs the
    // original values, use Approach 2.
    // ─────────────────────────────────────────────────────────────
    public int[] runningSumOptimal(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }
}
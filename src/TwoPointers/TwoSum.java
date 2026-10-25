package TwoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Two Sum (LeetCode 1)
 * Pattern: Hash Map (Complement Lookup)
 * URL: <a href="https://leetcode.com/problems/two-sum/">...</a>
 * <p>
 * Problem Statement:
 * Given an array of integers nums and an integer target, return the indices of the
 * two numbers such that they add up to target. Each input has exactly one solution,
 * and you may not use the same element twice. You can return the answer in any order.
 * <p>
 * Example:
 * Input:  nums = [2, 7, 11, 15], target = 9
 * Output: [0, 1]   (nums[0] + nums[1] = 2 + 7 = 9)
 */
public class TwoSum {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Check Every Pair)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each index we scan every index after it.
    // Space Complexity: O(1). Only loop variables.
    //
    // Why NOT optimal: for each element we search the rest of the array from scratch.
    // The question "have I seen target - nums[i] before?" can be answered in O(1)
    // with a hash map, so the inner loop is unnecessary.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{-1, -1}; // Problem guarantees a solution, so this is never reached
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Sorting + Two Pointers
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost; the scan is O(n).
    // Space Complexity: O(n). We store a sorted array of indices so we can
    // still return the original positions.
    //
    // Key insight: once sorted, a sum that's too small means the left pointer
    // must move right, and a sum that's too large means the right pointer must
    // move left. But sorting scrambles the original indices, so we sort the
    // indices by their values instead.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSumSorted(int[] nums, int target) {
        Integer[] idx = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> Integer.compare(nums[a], nums[b]));

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[idx[left]] + nums[idx[right]];
            if (sum == target) {
                return new int[]{idx[left], idx[right]};
            } else if (sum < target) {
                left++;   // Need a larger sum
            } else {
                right--;  // Need a smaller sum
            }
        }

        return new int[]{-1, -1};
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Hash Map of Seen Values)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass, with O(1) average map operations.
    // Space Complexity: O(n). The map can hold up to n entries.
    //
    // Idea: for each number, compute its complement (target - num). If the
    // complement was already seen, we found the pair. Otherwise, store the
    // current number and its index so future elements can find it.
    // ─────────────────────────────────────────────────────────────
    public int[] twoSumOptimal(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(nums[i], i); // Store after checking, so an element never pairs with itself
        }

        return new int[]{-1, -1};
    }
}
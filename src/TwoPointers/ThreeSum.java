package TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Problem: 3Sum (LeetCode 15)
 * Pattern: Two Pointers (Sort, Fix One Value, Opposite Ends on the Rest)
 * URL: <a href="https://leetcode.com/problems/3sum/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * The solution set must not contain duplicate triplets.
 * <p>
 * Example:
 * Input:  nums = [-1, 0, 1, 2, -1, -4]
 * Output: [[-1, -1, 2], [-1, 0, 1]]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every triple)                 O(n^3) time, O(k) space
 *   2. Hash set for the third value                     O(n^2) time, O(n) space
 *   3. Sort, then two pointers for each fixed value     O(n^2) time, O(1) extra space
 */
public class ThreeSum {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Triple)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^3). Every triple (i, j, k) is checked.
    // Space Complexity: O(k) for the result, plus a set to remove duplicates.
    //
    // Sorting each triple before adding it to a set makes duplicate
    // detection straightforward. Correct, but far too slow for large inputs.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> seen = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = new ArrayList<>(List.of(nums[i], nums[j], nums[k]));
                        triplet.sort(null);
                        seen.add(triplet);
                    }
                }
            }
        }
        return new ArrayList<>(seen);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Hash Set for the Third Value
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). The outer loop fixes i, and the inner loop
    // does O(1) hash lookups for each j.
    // Space Complexity: O(n) for the per-i hash set.
    //
    // Idea: fix nums[i], then for each j, the required third value is
    // -(nums[i] + nums[j]). Check a hash set of previously seen values
    // for that value. Duplicates are handled by sorting the array first,
    // then skipping repeated values for i and recording only unique triplets.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> threeSumHashSet(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // skip duplicate fixed values
            }
            Set<Integer> seen = new HashSet<>();
            Set<Integer> used = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int need = -(nums[i] + nums[j]);
                if (seen.contains(need) && !used.contains(need)) {
                    result.add(List.of(nums[i], need, nums[j]));
                    used.add(need);
                }
                seen.add(nums[j]);
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Sort, Then Two Pointers)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). Sorting costs O(n log n), and for each of the
    // n fixed values, the two-pointer scan costs O(n).
    // Space Complexity: O(1) extra, not counting the output list and sort overhead.
    //
    // Idea: sort the array. Fix nums[i], then run two pointers on the rest
    // (left = i + 1, right = n - 1). If the sum is too small, move left forward.
    // If it is too large, move right backward. On a match, record the triplet and
    // move both pointers, skipping equal values so no duplicate triplet is produced.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0) {
                break; // all remaining values are positive, so no zero sum is possible
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // skip duplicate fixed values
            }

            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(List.of(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}

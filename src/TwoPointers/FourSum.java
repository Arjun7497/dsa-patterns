package TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Problem: 4Sum (LeetCode 18)
 * Pattern: Two Pointers (Sort, Fix Two Values, Opposite Ends on the Rest)
 * URL: <a href="https://leetcode.com/problems/4sum/">...</a>
 * <p>
 * Problem Statement:
 * Given an array nums of n integers, return an array of all the unique quadruplets
 * [nums[a], nums[b], nums[c], nums[d]] such that:
 *   a, b, c, and d are distinct indices, and
 *   nums[a] + nums[b] + nums[c] + nums[d] == target.
 * The answer may be returned in any order, and it must not contain duplicate quadruplets.
 * <p>
 * Example:
 * Input:  nums = [1, 0, -1, 0, -2, 2], target = 0
 * Output: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every quadruple)           O(n^4) time, O(k) space
 *   2. Fix two values, hash set for the last two     O(n^3) time, O(n) space
 *   3. Sort, fix two values, two pointers on the rest O(n^3) time, O(1) extra space
 */
public class FourSum {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Quadruple)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^4). Every quadruple (a, b, c, d) is checked.
    // Space Complexity: O(k) for the result, plus a set to remove duplicates.
    //
    // Sorting each quadruple before inserting it into a set makes duplicate
    // detection easy. Correct, but far too slow for even moderate inputs.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        Set<List<Integer>> seen = new HashSet<>();
        int n = nums.length;

        for (int a = 0; a < n - 3; a++) {
            for (int b = a + 1; b < n - 2; b++) {
                for (int c = b + 1; c < n - 1; c++) {
                    for (int d = c + 1; d < n; d++) {
                        long sum = (long) nums[a] + nums[b] + nums[c] + nums[d];
                        if (sum == target) {
                            List<Integer> quad = new ArrayList<>(List.of(nums[a], nums[b], nums[c], nums[d]));
                            quad.sort(null);
                            seen.add(quad);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(seen);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Fix Two Values, Hash Set for the Last Two
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^3). Two nested loops fix the first two values, and
    // the inner loop does O(1) hash lookups for each third value.
    // Space Complexity: O(n) for the per-pair hash set.
    //
    // Idea: sort the array, fix nums[i] and nums[j], then reduce the problem to
    // a Two Sum over the rest. For each k, the required fourth value is
    // target - nums[i] - nums[j] - nums[k], which is checked against a hash set
    // of earlier values. Duplicates are skipped at each fixed level.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> fourSumHashSet(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // skip duplicate first values
            }
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // skip duplicate second values
                }
                Set<Integer> seen = new HashSet<>();
                Set<Integer> used = new HashSet<>();
                long partial = (long) nums[i] + nums[j];
                for (int k = j + 1; k < n; k++) {
                    long need = target - partial - nums[k];
                    if (need <= Integer.MAX_VALUE && need >= Integer.MIN_VALUE
                            && seen.contains((int) need) && !used.contains((int) need)) {
                        result.add(List.of(nums[i], nums[j], (int) need, nums[k]));
                        used.add((int) need);
                    }
                    seen.add(nums[k]);
                }
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Sort, Fix Two Values, Two Pointers)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^3). Two fixed levels, each with an O(n) two-pointer scan.
    // Space Complexity: O(1) extra, not counting the output list and sort overhead.
    //
    // Idea: sort the array. Fix nums[i] and nums[j], then run two pointers on the
    // rest (left = j + 1, right = n - 1), exactly as in 3Sum. Each step compares
    // the four-value sum to the target and moves one pointer. On a match, record
    // the quadruplet and skip equal values on both sides so no duplicate is produced.
    // ─────────────────────────────────────────────────────────────
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // skip duplicate first values
            }
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // skip duplicate second values
                }

                int left = j + 1;
                int right = n - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Skip duplicates on the left pointer
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        // Skip duplicates on the right pointer
                        while (left < right && nums[right] == nums[right - 1]) right--;

                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return result;
    }
}
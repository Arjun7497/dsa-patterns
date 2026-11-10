package Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem: Majority Element II (LeetCode 229)
 * Pattern: Boyer-Moore Voting (Generalized to Two Candidates)
 * URL: <a href="https://leetcode.com/problems/majority-element-ii/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums of size n, return all the elements that appear
 * more than n / 3 times. The answer may be returned in any order.
 * <p>
 * Example:
 * Input:  nums = [3, 2, 3]
 * Output: [3]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (count each distinct value)    O(n^2) time, O(1) space
 *   2. Hash map of counts                         O(n) time, O(n) space
 *   3. Boyer-Moore voting with two candidates     O(n) time, O(1) space
 */
public class MajorityElementII {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Count Each Distinct Value)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each value, a full scan counts its occurrences.
    // Space Complexity: O(1) extra, not counting the output list.
    //
    // For each position, skip it if its value appeared earlier (so each value
    // is counted once), then count how many times it appears in the whole array.
    // Correct and simple, but it rescans the array for every distinct value.
    // ─────────────────────────────────────────────────────────────
    public List<Integer> majorityElementBruteForce(int[] nums) {
        int n = nums.length;
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            // Skip values already handled at an earlier index
            boolean seenBefore = false;
            for (int j = 0; j < i; j++) {
                if (nums[j] == nums[i]) {
                    seenBefore = true;
                    break;
                }
            }
            if (seenBefore) {
                continue;
            }

            int count = 0;
            for (int num : nums) {
                if (num == nums[i]) {
                    count++;
                }
            }
            if (count > n / 3) {
                result.add(nums[i]);
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Hash Map of Counts
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to count, one pass over the map.
    // Space Complexity: O(n) for the map in the worst case.
    //
    // Count every value in a hash map, then keep the values whose count
    // exceeds n / 3. This is easy to reason about and easy to explain, but
    // it uses memory proportional to the number of distinct values.
    // ─────────────────────────────────────────────────────────────
    public List<Integer> majorityElementHashMap(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.merge(num, 1, Integer::sum);
        }

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > n / 3) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Boyer-Moore Voting With Two Candidates)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Two passes: one to find candidates, one to verify.
    // Space Complexity: O(1). Only two candidates and two counters.
    //
    // Idea: at most two values can appear more than n / 3 times. Keep two
    // candidates with counters. A value matching a candidate adds a vote to it.
    // A new value takes an empty slot (counter zero). If both slots are taken
    // and the value matches neither, it cancels one vote from each candidate,
    // since three distinct values voting against each other cancel out. The
    // surviving candidates are then checked with a second pass, because the
    // first pass only guarantees that any majority value is among them.
    // ─────────────────────────────────────────────────────────────
    public List<Integer> majorityElement(int[] nums) {
        int cand1 = 0;
        int cand2 = 0;
        int count1 = 0;
        int count2 = 0;

        // Pass 1: find up to two candidates
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Pass 2: verify the candidates, since counts from pass 1 are not exact
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            }
        }

        List<Integer> result = new ArrayList<>();
        int threshold = nums.length / 3;
        if (count1 > threshold) {
            result.add(cand1);
        }
        if (count2 > threshold) {
            result.add(cand2);
        }
        return result;
    }
}
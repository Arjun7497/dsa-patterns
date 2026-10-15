package Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * Problem: Majority Element (LeetCode 169)
 * Pattern: Hashing / Sorting / Boyer-Moore Voting
 * URL: <a href="https://leetcode.com/problems/majority-element/">...</a>
 * <p>
 * Problem Statement:
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than n / 2 times.
 * You may assume that the majority element always exists in the array.
 * <p>
 * Example:
 * Input:  [2, 2, 1, 1, 1, 2, 2]
 * Output: 2
 */
public class MajorityElement {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Count Each Element)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each element we scan the whole array to count its occurrences.
    // Space Complexity: O(1). Only a counter and loop variables.
    //
    // Why NOT optimal: the nested loop rescans the array for every element,
    // which is far too slow for large inputs.
    // ─────────────────────────────────────────────────────────────
    public int majorityElementBruteForce(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (nums[j] == nums[i]) {
                    count++;
                }
            }
            if (count > n / 2) {
                return nums[i];
            }
        }

        return -1; // Problem guarantees a majority exists, so this is never reached
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Hash Map (Frequency Count)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to build the counts, one pass to find the answer.
    // Space Complexity: O(n). The map can hold up to n distinct keys.
    //
    // Why NOT optimal: it works well, but uses extra memory for the map.
    // Boyer-Moore below solves the same problem in O(1) space.
    // ─────────────────────────────────────────────────────────────
    public int majorityElementHashMap(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        int threshold = nums.length / 2;

        for (int num : nums) {
            int count = counts.getOrDefault(num, 0) + 1;
            counts.put(num, count);

            // Return as soon as we find the majority element
            if (count > threshold) {
                return num;
            }
        }

        return -1;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Sorting
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(1) extra if the sort is in place (Java may use O(log n) stack for dual-pivot quicksort).
    //
    // Key insight: a majority element occupies more than half the slots,
    // so after sorting it must cover the middle index n / 2.
    // ─────────────────────────────────────────────────────────────
    public int majorityElementSort(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 4 — Optimal (Boyer-Moore Voting)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array.
    // Space Complexity: O(1). Only two variables: candidate and count.
    //
    // Idea: pair each non-candidate with a candidate and let them cancel.
    // The majority element has more than half the votes, so it can't be
    // fully canceled out and is the one left as the candidate.
    // ─────────────────────────────────────────────────────────────
    public int majorityElementOptimal(int[] nums) {
        int candidate = nums[0];
        int count = 1;
        for(int num : nums) {
            if(count == 0) {
                candidate = num;
                count++;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }
}
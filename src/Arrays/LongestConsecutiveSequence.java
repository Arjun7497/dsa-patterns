package Arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Problem: Longest Consecutive Sequence (LeetCode 128)
 * Pattern: Hash Set (Start Detection)
 * URL: <a href="https://leetcode.com/problems/longest-consecutive-sequence/">...</a>
 * <p>
 * Problem Statement:
 * Given an unsorted array of integers nums, return the length of the longest
 * sequence of consecutive integers. The algorithm must run in O(n) time.
 * <p>
 * Example:
 * Input:  [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest sequence is [1, 2, 3, 4].
 * <p>
 * Input:  [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
 * Output: 9
 * <p>
 * This file contains three approaches:
 *   1. Sort, then scan for runs              O(n log n) time, O(1) to O(log n) extra space
 *   2. Hash map of run lengths at endpoints  O(n) time, O(n) space
 *   3. Hash set, start only at run beginnings (optimal) O(n) time, O(n) space
 */
public class LongestConsecutiveSequence {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Sort, Then Scan for Runs)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(1) to O(log n) extra, depending on the sort.
    //
    // Idea: after sorting, consecutive values sit next to each other.
    // Duplicates are skipped, and each run is measured by extending
    // the current length whenever the next value is exactly one larger.
    //
    // Why NOT optimal: the problem only asks for a length, not a
    // sorted order, so sorting does more work than necessary. The
    // statement also requires O(n) time, which sorting cannot meet.
    // ─────────────────────────────────────────────────────────────
    public int longestConsecutiveSorting(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int best = 1;
        int current = 1;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                continue;
            }
            if (sorted[i] == sorted[i - 1] + 1) {
                current++;
            } else {
                current = 1;
            }
            best = Math.max(best, current);
        }
        return best;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Hash Map of Run Lengths at Endpoints
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each number is inserted once and touches
    // a constant number of map entries.
    // Space Complexity: O(n) for the map.
    //
    // Idea: map each run's two endpoints to the full run length. When a
    // new number x arrives, its left neighbor x - 1 may end a run and
    // its right neighbor x + 1 may start one. Joining them gives the
    // new run length, which is stored at both new endpoints.
    //
    // This works, but it needs careful bookkeeping at the endpoints.
    // ─────────────────────────────────────────────────────────────
    public int longestConsecutiveEndpoints(int[] nums) {
        Map<Integer, Integer> runLength = new HashMap<>();
        int best = 0;
        for (int num : nums) {
            if (runLength.containsKey(num)) {
                continue;
            }
            int left = runLength.getOrDefault(num - 1, 0);
            int right = runLength.getOrDefault(num + 1, 0);
            int length = left + right + 1;
            runLength.put(num, length);
            runLength.put(num - left, length);
            runLength.put(num + right, length);
            best = Math.max(best, length);
        }
        return best;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Hash Set, Start Only at Run Beginnings)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each number is counted at most twice: once
    // as a loop value, and once while walking up from a run start.
    // Space Complexity: O(n) for the set.
    //
    // Idea: a number that has no predecessor (x - 1 is absent) must be
    // the start of its run. Only walk upward from those starts. Numbers
    // in the middle of a run are skipped, so no run is walked twice.
    //
    // Trade-off: the set is O(n) extra memory, but the logic is short
    // and easy to verify, which makes it the usual interview answer.
    // ─────────────────────────────────────────────────────────────
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        int best = 0;
        for (int num : numSet) {
            if (numSet.contains(num - 1)) {
                continue;
            }
            int length = 1;
            while (numSet.contains(num + length)) {
                length++;
            }
            best = Math.max(best, length);
        }
        return best;
    }
}
package Arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem: First Missing Positive (LeetCode 41)
 * Pattern: Cyclic Sort (Index as Hash)
 * URL: <a href="https://leetcode.com/problems/first-missing-positive/">...</a>
 * <p>
 * Problem Statement:
 * Given an unsorted integer array nums, return the smallest positive integer
 * that is not present in nums. You must run in O(n) time and use O(1)
 * auxiliary space.
 * <p>
 * Example:
 * Input:  [3, 4, -1, 1]
 * Output: 2
 */
public class FirstMissingPositive {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Sort, Then Scan)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(1) extra, ignoring the sort's internal stack.
    //
    // Why NOT optimal: the problem asks for O(n) time, and sorting
    // does more work than we need. We only care about positive values
    // that fit in the range 1..n.
    // ─────────────────────────────────────────────────────────────
    public int firstMissingPositiveSort(int[] nums) {
        Arrays.sort(nums);

        int expected = 1;
        for (int num : nums) {
            if (num == expected) {
                expected++;          // Found the next expected positive
            } else if (num > expected) {
                break;               // Gap found, so expected is missing
            }
            // Duplicates and non-positive values are skipped
        }

        return expected;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Hash Set
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to build the set, then up to n+1 lookups.
    // Space Complexity: O(n). The set can hold up to n values.
    //
    // Key insight: the answer is always in the range 1..n+1. If the array
    // held every value from 1 to n, the answer would be n+1. So we only
    // need to check the candidates 1, 2, ..., n+1.
    //
    // Why NOT optimal: the set uses O(n) extra memory, which the problem forbids.
    // ─────────────────────────────────────────────────────────────
    public int firstMissingPositiveHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            seen.add(num);
        }

        int candidate = 1;
        while (seen.contains(candidate)) {
            candidate++;
        }

        return candidate;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Cyclic Placement Using the Array as a Hash)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each swap places one value in its home slot,
    // so there are at most n swaps in total, and the final scan is O(n).
    // Space Complexity: O(1). The array itself stores the markers.
    //
    // Idea: value v belongs at index v - 1. Keep swapping each value in
    // range [1, n] into its home slot until it's there or it's a duplicate.
    // Ignore 0, negatives, and values larger than n, since they can't be
    // the answer's slots. Then the first index i where nums[i] != i + 1
    // reveals the missing value i + 1.
    // ─────────────────────────────────────────────────────────────
    public int firstMissingPositiveOptimal(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Keep placing nums[i] until it's out of range, already home,
            // or a duplicate of the value at its home slot
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int target = nums[i] - 1;
                int temp = nums[target];
                nums[target] = nums[i];
                nums[i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return n + 1; // Every slot 1..n is filled, so n + 1 is missing
    }
}
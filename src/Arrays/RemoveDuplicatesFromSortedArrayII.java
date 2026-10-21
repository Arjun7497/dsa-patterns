package Arrays;

/**
 * Problem: Remove Duplicates from Sorted Array II (LeetCode 80)
 * Pattern: Two Pointers (Read-Write, Compare With k Positions Back)
 * URL: <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums sorted in non-decreasing order, remove some duplicates
 * in-place such that each unique element appears at most twice. The relative order
 * of the elements should be kept the same. Return k, the number of elements left.
 * The first k elements of nums must hold the result.
 * <p>
 * Example:
 * Input:  [1, 1, 1, 2, 2, 3]
 * Output: 5, nums = [1, 1, 2, 2, 3, _]
 * <p>
 * This file contains four approaches:
 *   1. Brute force with an extra array          O(n) time, O(n) space
 *   2. Count each run and shift left            O(n^2) time, O(1) space
 *   3. Two pointers comparing with slow - 2     O(n) time, O(1) space
 *   4. Count each run in a single pass          O(n) time, O(1) space
 */
public class RemoveDuplicatesFromSortedArrayII {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Extra Array)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to collect values, one pass to copy back.
    // Space Complexity: O(n). The temporary array can hold up to n values.
    //
    // Why NOT optimal: it breaks the in-place requirement. The sorted order
    // means we can decide each element's fate by looking at the output so far.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesExtraArray(int[] nums) {
        int[] result = new int[nums.length];
        int k = 0;

        for (int num : nums) {
            // Keep num if the result is short, or if it differs from the value two slots back
            if (k < 2 || num != result[k - 2]) {
                result[k++] = num;
            }
        }

        for (int i = 0; i < k; i++) {
            nums[i] = result[i];
        }

        return k;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Count Each Run and Shift Left
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2) in the worst case. Each removal can shift up to n elements.
    // Space Complexity: O(1). Only a few counters and loop variables.
    //
    // Idea: walk through the array, count how many times the current value repeats,
    // and when a run is longer than 2, shift the rest of the array left to close the gap.
    //
    // Why NOT optimal: every removal costs a full shift, so the same elements
    // get moved over and over.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesShift(int[] nums) {
        int length = nums.length;
        int i = 0;

        while (i < length) {
            int count = 1;
            while (i + count < length && nums[i + count] == nums[i]) {
                count++;
            }

            // Keep at most two copies: remove the extra (count - 2) by shifting left
            int extra = Math.max(0, count - 2);
            for (int j = i + 2; j + extra < length; j++) {
                nums[j] = nums[j + extra];
            }
            length -= extra;
            i += Math.min(count, 2);
        }

        return length;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Two Pointers, Compare With slow - 2)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass. Each element is visited once.
    // Space Complexity: O(1). Only two pointer variables.
    //
    // Idea: "slow" is the next write position. The first two elements are always
    // kept. After that, nums[fast] is kept only if it differs from nums[slow - 2].
    // Since the array is sorted, if nums[slow - 2] equals nums[fast], then the
    // last two kept values are both equal to nums[fast], so a third copy would
    // break the rule.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesOptimal(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }

        int slow = 2; // The first two elements are always valid
        for (int fast = 2; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 4 — Optimal (Count Each Run, Single Pass)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array.
    // Space Complexity: O(1). Only a few counters.
    //
    // Idea: track how many times the current value has appeared in a row.
    // Keep an element only while its run length is at most 2. Writing into
    // nums[writePos] never overwrites a value we still need to read.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesCount(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }

        int writePos = 1;
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            if (count <= 2) {
                nums[writePos] = nums[i];
                writePos++;
            }
        }

        return writePos;
    }
}
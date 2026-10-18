package Arrays;

/**
 * Problem: Remove Duplicates from Sorted Array (LeetCode 26)
 * Pattern: Two Pointers (Same Direction / Read-Write)
 * URL: <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums sorted in non-decreasing order, remove the duplicates
 * in-place such that each unique element appears only once. The relative order of the
 * elements should be kept the same. Return k, the number of unique elements. The first
 * k elements of nums must contain the unique elements in sorted order.
 * <p>
 * Example:
 * Input:  [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
 * Output: 5, nums = [0, 1, 2, 3, 4, _, _, _, _, _]
 */
public class RemoveDuplicatesFromSortedArray {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Extra Array)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to collect unique values, one pass to copy them back.
    // Space Complexity: O(n). The temporary array can hold up to n values.
    //
    // Why NOT optimal: it works, but it breaks the in-place requirement.
    // The sorted order means we don't need any extra storage at all.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesExtraArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int[] unique = new int[nums.length];
        int k = 0;

        for (int num : nums) {
            if (k == 0 || num != unique[k - 1]) {
                unique[k++] = num;
            }
        }

        for (int i = 0; i < k; i++) {
            nums[i] = unique[i];
        }

        return k;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Shift Elements Left On Each Duplicate
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2) in the worst case. Each duplicate can trigger a shift
    // of up to n elements, and there can be up to n duplicates.
    // Space Complexity: O(1). Only loop variables.
    //
    // Why NOT optimal: when a duplicate is found, every later element moves left by one.
    // Those shifts are repeated work. The two-pointer version avoids them completely.
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesShift(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int i = 1;
        while (i < length) {
            if (nums[i] == nums[i - 1]) {
                // Shift everything after i one position to the left
                for (int j = i; j < length - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                length--;
            } else {
                i++;
            }
        }

        return length;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Slow and Fast Pointers)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass. Each element is visited once.
    // Space Complexity: O(1). Only two pointer variables.
    //
    // Idea: "slow" marks the end of the unique prefix (the next write position).
    // "fast" scans the array. Since the array is sorted, a value that differs from
    // nums[slow] is a new unique value, so copy it to nums[slow + 1].
    // ─────────────────────────────────────────────────────────────
    public int removeDuplicatesOptimal(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }
}
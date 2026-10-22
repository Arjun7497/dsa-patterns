package Arrays;

/**
 * Problem: Remove Element (LeetCode 27)
 * Pattern: Two Pointers (Read-Write)
 * URL: <a href="https://leetcode.com/problems/remove-element/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums and an integer val, remove all occurrences of val in nums
 * in-place. The order of the elements may be changed. Return k, the number of elements
 * in nums that are not equal to val. The first k elements of nums must hold the result.
 * <p>
 * Example:
 * Input:  nums = [3, 2, 2, 3], val = 3
 * Output: 2, nums = [2, 2, _, _]
 * <p>
 * This file contains three approaches:
 *   1. Extra array                    O(n) time, O(n) space
 *   2. Shift left on each match       O(n^2) time, O(1) space
 *   3. Two pointers (read-write)      O(n) time, O(1) space
 */
public class RemoveElement {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Extra Array)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass to collect, one pass to copy back.
    // Space Complexity: O(n). The temporary array can hold up to n values.
    //
    // Why NOT optimal: it breaks the in-place requirement. We can keep
    // the good values in the same array by writing them forward.
    // ─────────────────────────────────────────────────────────────
    public int removeElementExtraArray(int[] nums, int val) {
        int[] result = new int[nums.length];
        int k = 0;

        for (int num : nums) {
            if (num != val) {
                result[k++] = num;
            }
        }

        for (int i = 0; i < k; i++) {
            nums[i] = result[i];
        }

        return k;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Shift Left On Each Match
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2) in the worst case. Each match can shift up to n elements.
    // Space Complexity: O(1). Only loop variables.
    //
    // Idea: when nums[i] equals val, shift every later element one position left
    // and shrink the logical length. Don't advance i, since a new value now sits there.
    //
    // Why NOT optimal: each removal costs a full shift, so the same elements
    // move over and over.
    // ─────────────────────────────────────────────────────────────
    public int removeElementShift(int[] nums, int val) {
        int length = nums.length;
        int i = 0;

        while (i < length) {
            if (nums[i] == val) {
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
    // APPROACH 3 — Optimal (Two Pointers, Read-Write)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass. Each element is visited once.
    // Space Complexity: O(1). Only two pointer variables.
    //
    // Idea: "fast" reads every element. "slow" is the next write position
    // for a value that should be kept. Whenever nums[fast] is not val,
    // copy it to nums[slow] and advance slow.
    // ─────────────────────────────────────────────────────────────
    public int removeElementOptimal(int[] nums, int val) {
        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 4 — Optimal (Swap With Last, Shrink the Range)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each iteration either advances i or shrinks n.
    // Space Complexity: O(1). Only two index variables.
    //
    // Idea: when nums[i] equals val, replace it with the last active element
    // and shrink the range. Don't advance i, since the new value still needs
    // to be checked. Order may change, which the problem allows.
    // ─────────────────────────────────────────────────────────────
    public int removeElementSwapLast(int[] nums, int val) {
        int i = 0;
        int n = nums.length;

        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }

        return i; // i == n here, which is the count of kept elements
    }
}
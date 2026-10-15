package Arrays;

/**
 * Problem: Move Zeroes (LeetCode 283)
 * Pattern: Two Pointers
 * URL: <a href="https://leetcode.com/problems/move-zeroes/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums, move all 0s to the end
 * while maintaining relative order of non-zero elements.
 * Must do this in-place without making a copy of the array.
 * <p>
 * Example:
 * Input:  [0, 1, 0, 3, 12]
 * Output: [1, 3, 12, 0, 0]
 */
public class MoveZeroes {

    // ─────────────────────────────────────────────────────────────
    // APPROACH — Brute Force (Extra Array)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). We iterate through the array twice: once to collect non-zeroes and once to copy back.
    // Both passes are O(n), so the total is O(n).
    // Space Complexity: O(n). We allocate an extra array of the same size as the input.
    //
    // Why NOT optimal: violates in-place constraint (without making a copy of the array).
    // ─────────────────────────────────────────────────────────────
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int insertPos = 0;

        // Collect non-zero elements
        for (int num : nums) {
            if (num != 0) {
                result[insertPos] = num;
                insertPos++;
            }
        }

        // Copy result back into nums
        for (int i = 0; i < n; i++) {
            nums[i] = result[i];
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH  — Two Pointers (Swap)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array. Each element is visited exactly once.
    // Space Complexity: O(1). Only a couple of extra variables (insertPos, temp). No additional data structures.
    //
    // swaps on every non-zero element, including when i == insertPos and the element is already in place.
    // In an array like [1, 2, 3, 0, 0], the first three iterations swap each element with itself, three wasted writes
    public void moveZeroesSwap(int[] nums) {
        int insertPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // Swap non-zero element into the correct position
                int temp = nums[insertPos];
                nums[insertPos] = nums[i];
                nums[i] = temp;
                insertPos++;
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH  — Optimal (Minimal Operations)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array. Each element is visited exactly once.
    // Space Complexity: O(1). Only a couple of extra variables (left,right, temp). No additional data structures.
    //
    // This version writes only when i != insertPos. When a non-zero element is already at its target slot,
    // it advances insertPos without touching the array
    public void moveZeroesOptimal(int[] nums) {
        int insertPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // Only swap when the element isn't already in position
                if (i != insertPos) {
                    nums[insertPos] = nums[i];
                    nums[i] = 0;
                }
                insertPos++;
            }
        }
    }

}

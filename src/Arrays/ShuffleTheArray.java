package Arrays;


import java.util.ArrayList;
import java.util.List;

/**
 * Problem: Shuffle the Array (LeetCode 1470)
 * Pattern: Two Pointers (Parallel Traversal, Interleave Two Halves)
 * URL: <a href="https://leetcode.com/problems/shuffle-the-array/">...</a>
 * <p>
 * Problem Statement:
 * Given an array nums of 2n elements in the form [x1, x2, ..., xn, y1, y2, ..., yn],
 * return the array in the form [x1, y1, x2, y2, ..., xn, yn].
 * <p>
 * Example:
 * Input:  nums = [2, 5, 1, 3, 4, 7], n = 3
 * Output: [2, 3, 5, 4, 1, 7]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (build with a list, then copy)   O(n) time, O(n) space
 *   2. Single loop with index math                  O(n) time, O(n) space
 *   3. In-place encoding in base 1001 (no extra array) O(n) time, O(1) extra space
 */

public class ShuffleTheArray {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Build a List, Then Copy)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each element is added once and copied once.
    // Space Complexity: O(n) for the list and the output array.
    //
    // Collect the pairs one at a time into a list, then copy the list into
    // an array. Correct and readable, but it stores the result twice.
    // ─────────────────────────────────────────────────────────────
    public int[] shuffleBruteForce(int[] nums, int n) {
        List<Integer> shuffled = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            shuffled.add(nums[i]);
            shuffled.add(nums[i + n]);
        }

        int[] result = new int[2 * n];
        for (int i = 0; i < result.length; i++) {
            result[i] = shuffled.get(i);
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Optimal (Single Loop With Index Math)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One loop of n iterations.
    // Space Complexity: O(n) for the output array.
    //
    // Idea: the x values live at indices 0..n-1 and the y values at n..2n-1.
    // For each i, nums[i] is the next x value and belongs at even position 2i,
    // while nums[n + i] is the matching y value and belongs at odd position 2i + 1.
    // Both writes happen in the same iteration, so the output index is computed
    // from i and no separate pointer is needed.
    // ─────────────────────────────────────────────────────────────
    public int[] shuffle(int[] nums, int n) {
        int[] result = new int[2 * n];

        for (int i = 0; i < n; i++) {
            result[2 * i] = nums[i];           // x_i goes to an even slot
            result[2 * i + 1] = nums[n + i];   // y_i goes to the following odd slot
        }

        return result;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (In-Place Encoding in Base 1001)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each slot is read and written a constant number of times.
    // Space Complexity: O(1) extra. The result is stored in nums itself.
    //
    // Constraints guarantee every value is at most 1000, so each value fits
    // below the base 1001. Each slot keeps its original value in the low part
    // (value % 1001) and stores the new value in the high part (new * 1001).
    // Adding to a slot never disturbs its low part, so every original value
    // can still be read with % 1001, even after its slot has been modified.
    // A final pass divides by 1001 to keep only the high part.
    // ─────────────────────────────────────────────────────────────
    public int[] shuffleInPlace(int[] nums, int n) {
        int maxVal = 1001;

        // Encode: add each shuffled value as the high part (base maxVal)
        for (int i = n - 1; i >= 0; i--) {
            // nums[n + i] goes to position 2i + 1
            nums[2 * i + 1] += (nums[n + i] % maxVal) * maxVal;
            // nums[i] goes to position 2i
            nums[2 * i] += (nums[i] % maxVal) * maxVal;
        }

        // Decode: keep only the high part (the shuffled value)
        for (int i = 0; i < 2 * n; i++) {
            nums[i] = nums[i] / maxVal;
        }

        return nums;
    }
}
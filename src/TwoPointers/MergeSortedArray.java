package TwoPointers;

import java.util.Arrays;

/**
 * Problem: Merge Sorted Array (LeetCode 88)
 * Pattern: Two Pointers (Fill From the Back)
 * URL: <a href="https://leetcode.com/problems/merge-sorted-array/">...</a>
 * <p>
 * Problem Statement:
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2
 * respectively. nums1 has a length of m + n, where the first m elements are the
 * values to merge and the last n elements are set to 0 and should be ignored.
 * Merge nums2 into nums1 so that nums1 becomes one sorted array, in place.
 * <p>
 * Example:
 * Input:  nums1 = [1, 2, 3, 0, 0, 0], m = 3, nums2 = [2, 5, 6], n = 3
 * Output: [1, 2, 2, 3, 5, 6]
 * <p>
 * This file contains three approaches:
 *   1. Brute force (copy, then sort)            O((m + n) log(m + n)) time, O(1) extra space
 *   2. Two pointers, merge into a new array     O(m + n) time, O(m + n) space
 *   3. Two pointers, fill nums1 from the back   O(m + n) time, O(1) extra space
 */
public class MergeSortedArray {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Copy, Then Sort)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O((m + n) log(m + n)). Sorting dominates the cost.
    // Space Complexity: O(1) extra, since the sort happens in place on nums1.
    //
    // Copy the n values from nums2 into the empty tail of nums1, then sort
    // the whole array. Correct and short, but it ignores the fact that both
    // inputs are already sorted.
    // ─────────────────────────────────────────────────────────────
    public void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Two Pointers, Merge Into a New Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(m + n). Each step consumes one element from either input.
    // Space Complexity: O(m + n) for the temporary merged array.
    //
    // Idea: this is the classic merge step from merge sort. Walk both inputs
    // from the front, always taking the smaller head value, and write into a
    // new array. The result is then copied back into nums1. Simple, but it
    // needs extra memory that the problem does not require.
    // ─────────────────────────────────────────────────────────────
    public void mergeNewArray(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m + n];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }

        for (int t = 0; t < merged.length; t++) {
            nums1[t] = merged[t];
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Two Pointers, Fill nums1 From the Back)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(m + n). Each step places one value into its final slot.
    // Space Complexity: O(1) extra. The merge happens inside nums1.
    //
    // Idea: the back of nums1 is empty space, so fill it from the largest value
    // down. Compare the last real element of nums1 (index i) with the last
    // element of nums2 (index j), and write the larger one to position k, the
    // current end. Writing from the back never overwrites an unread value in
    // nums1, because k is always at least as far right as i.
    // ─────────────────────────────────────────────────────────────
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}

package PrefixSum;

/**
 * Problem: Range Sum Query - Immutable (LeetCode 303)
 * Pattern: Prefix Sum
 * URL: <a href="https://leetcode.com/problems/range-sum-query-immutable/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums, handle multiple queries of the form:
 * return the sum of the elements between indices left and right (inclusive).
 * <p>
 * Example:
 * nums = [-2, 0, 3, -5, 2, -1]
 * sumRange(0, 2) = 1
 * sumRange(2, 5) = -1
 * sumRange(0, 5) = -3
 * <p>
 * This file contains three approaches:
 *   1. Brute force: sum on every query        Build O(1), Query O(n), Space O(1) extra
 *   2. Prefix sum array (separate array)      Build O(n), Query O(1), Space O(n)
 *   3. In-place prefix sum (reuses input)     Build O(n), Query O(1), Space O(1) extra
 */
public class RangeSumQueryImmutable {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Sum on Every Query)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(1) to build, O(n) per sumRange call.
    // Space Complexity: O(1) extra (we only keep a reference to nums).
    //
    // Why NOT optimal: the array never changes, so every query re-adds
    // the same elements. With up to 10^4 queries on 10^4 elements, that
    // is about 10^8 operations, which is too slow in the worst case.
    // ─────────────────────────────────────────────────────────────
    public static class NumArrayBruteForce {
        private final int[] nums;

        public NumArrayBruteForce(int[] nums) {
            this.nums = nums;
        }

        public int sumRange(int left, int right) {
            int sum = 0;
            for (int i = left; i <= right; i++) {
                sum += nums[i];
            }
            return sum;
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Prefix Sum Array (Separate Array)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n) to build, O(1) per sumRange call.
    // Space Complexity: O(n) for the prefix array.
    //
    // Idea: prefix[i] holds the sum of nums[0..i-1], so prefix[0] = 0.
    // The sum of [left, right] is prefix[right + 1] - prefix[left].
    // The extra leading zero removes the special case when left = 0.
    // ─────────────────────────────────────────────────────────────
    public static class NumArray {
        private final int[] prefix;

        public NumArray(int[] nums) {
            int n = nums.length;
            prefix = new int[n + 1];
            for (int i = 0; i < n; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (In-Place Prefix Sum)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n) to build, O(1) per sumRange call.
    // Space Complexity: O(1) extra, since the prefix sums overwrite nums.
    //
    // Idea: after the transform, nums[i] holds the sum of the original
    // nums[0..i]. The sum of [left, right] is nums[right] minus the sum
    // up to left - 1, which is nums[left - 1] when left > 0.
    //
    // Trade-off: the input array is modified. This is fine when the
    // caller does not need the original values. Otherwise, use Approach 2.
    // ─────────────────────────────────────────────────────────────
    public static class NumArrayOptimal {
        private final int[] sums;

        public NumArrayOptimal(int[] nums) {
            this.sums = nums;
            for (int i = 1; i < sums.length; i++) {
                sums[i] += sums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            if (left == 0) {
                return sums[right];
            }
            return sums[right] - sums[left - 1];
        }
    }
}
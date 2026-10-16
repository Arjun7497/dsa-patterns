package Arrays;

/**
 * Problem: Product of Array Except Self (LeetCode 238)
 * Pattern: Prefix and Suffix Products
 * URL: <a href="https://leetcode.com/problems/product-of-array-except-self/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums, return an array answer such that
 * answer[i] is equal to the product of all the elements of nums except nums[i].
 * You must write an algorithm that runs in O(n) time and without using division.
 * <p>
 * Example:
 * Input:  [1, 2, 3, 4]
 * Output: [24, 12, 8, 6]
 */
public class ProductExceptSelf {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Nested Loops)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each index we multiply every other element.
    // Space Complexity: O(1) extra, not counting the output array.
    //
    // Why NOT optimal: the inner loop rescans the array for every index,
    // so the same products are recomputed over and over.
    // ─────────────────────────────────────────────────────────────
    public int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            answer[i] = product;
        }

        return answer;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Prefix and Suffix Arrays
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Three linear passes.
    // Space Complexity: O(n). Two extra arrays of size n.
    //
    // Key insight: answer[i] = (product of everything left of i) * (product of everything right of i).
    // Precompute both sides once, then combine them.
    //
    // Why NOT optimal: the prefix and suffix arrays use extra memory
    // that the final version can avoid.
    // ─────────────────────────────────────────────────────────────
    public int[] productExceptSelfPrefixSuffix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] answer = new int[n];

        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < n; i++) {
            answer[i] = prefix[i] * suffix[i];
        }

        return answer;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Output Array as Prefix, Running Suffix)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Two linear passes.
    // Space Complexity: O(1) extra, not counting the output array.
    //
    // Idea: first fill answer[] with prefix products. Then walk from the right,
    // keeping a single running variable for the suffix product and multiplying
    // it into answer[i]. The suffix product never needs to be stored.
    // ─────────────────────────────────────────────────────────────
    public int[] productExceptSelfOptimal(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // Pass 1: answer[i] holds the product of all elements left of i
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // Pass 2: multiply in the product of all elements right of i
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }

        return answer;
    }
}
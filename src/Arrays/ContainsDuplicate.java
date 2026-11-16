package Arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Problem: Contains Duplicate (LeetCode 217)
 * Pattern: Hash Set (Membership Check)
 * URL: <a href="https://leetcode.com/problems/contains-duplicate/">...</a>
 * <p>
 * Problem Statement:
 * Given an integer array nums, return true if any value appears at least
 * twice in the array, and return false if every element is distinct.
 * <p>
 * Example:
 * Input:  [1, 2, 3, 1]
 * Output: true
 * <p>
 * Input:  [1, 2, 3, 4]
 * Output: false
 * <p>
 * This file contains three approaches:
 *   1. Brute force: compare every pair              O(n^2) time, O(1) extra space
 *   2. Sort, then check neighbors                   O(n log n) time, O(1) to O(log n) extra space
 *   3. Hash set, stop at the first repeat (optimal) O(n) time, O(n) space
 */
public class ContainsDuplicate {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Compare Every Pair)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). Each element is compared with every later element.
    // Space Complexity: O(1) extra.
    //
    // Why NOT optimal: the same pair can be checked again and again, and
    // with n up to 10^5 the number of comparisons becomes far too large.
    // ─────────────────────────────────────────────────────────────
    public boolean containsDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Sort, Then Check Neighbors
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n log n). Sorting dominates the cost.
    // Space Complexity: O(1) to O(log n) extra, depending on the sort's internal stack.
    //
    // Idea: equal values end up next to each other after sorting, so one
    // scan comparing adjacent elements is enough. The array is modified,
    // so copy it first if the caller still needs the original order.
    // ─────────────────────────────────────────────────────────────
    public boolean containsDuplicateSorting(int[] nums) {
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) {
                return true;
            }
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Hash Set, Stop at First Repeat)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each add and contains check is O(1) on average.
    // Space Complexity: O(n) for the set in the worst case.
    //
    // Idea: walk the array once and remember every value seen so far.
    // The moment add() returns false, that value was already in the set,
    // so we have found a duplicate and can return immediately.
    //
    // Trade-off: this uses O(n) extra memory, while sorting uses almost none.
    // In interviews, the O(n) time solution is usually the expected answer.
    // ─────────────────────────────────────────────────────────────
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}
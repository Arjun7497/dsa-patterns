package TwoPointers;

/**
 * Problem: Container With Most Water (LeetCode 11)
 * Pattern: Two Pointers (Opposite Ends, Move the Shorter Wall)
 * URL: <a href="https://leetcode.com/problems/container-with-most-water/">...</a>
 * <p>
 * Problem Statement:
 * You are given an integer array height of length n. There are n vertical lines
 * drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * Find two lines that together with the x-axis form a container that holds the
 * most water. Return the maximum amount of water a container can store.
 * <p>
 * Example:
 * Input:  height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
 * Output: 49
 * <p>
 * This file contains three approaches:
 *   1. Brute force (check every pair)            O(n^2) time, O(1) space
 *   2. Two pointers, move the shorter wall       O(n) time, O(1) space
 *   3. Two pointers with early skip              O(n) time, O(1) space
 */
public class ContainerWithMostWater {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Check Every Pair)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). Every pair of lines is checked once.
    // Space Complexity: O(1). Only loop variables and a running maximum.
    //
    // Water between lines i and j is (j - i) * min(height[i], height[j]).
    // Trying every pair is simple and correct, but it ignores the structure
    // that lets us discard most pairs early.
    // ─────────────────────────────────────────────────────────────
    public int maxAreaBruteForce(int[] height) {
        int n = height.length;
        int best = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int area = (j - i) * Math.min(height[i], height[j]);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Optimal (Two Pointers, Move the Shorter Wall)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each step moves one pointer inward.
    // Space Complexity: O(1). Only two pointers and the best area.
    //
    // Idea: start with the widest container, left at 0 and right at n - 1.
    // The water level is limited by the shorter wall. Moving the taller wall
    // inward can only shrink the width and cannot raise the limiting height,
    // so it can never help. Move the shorter wall instead, hoping to find a
    // taller one that compensates for the lost width.
    // ─────────────────────────────────────────────────────────────
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;

        while (left < right) {
            int width = right - left;
            int h = Math.min(height[left], height[right]);
            best = Math.max(best, width * h);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Two Pointers With Early Skip
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Each pointer still moves inward at most n times.
    // Space Complexity: O(1). Only two pointers and the best area.
    //
    // Idea: same as Approach 2, but when the shorter wall is replaced, skip
    // every following wall that is no taller than it. Those walls would give
    // a smaller height and a smaller width, so they cannot beat the current
    // best. This saves comparisons in practice, though the worst case is still O(n).
    // ─────────────────────────────────────────────────────────────
    public int maxAreaEarlySkip(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            best = Math.max(best, (right - left) * h);

            if (height[left] < height[right]) {
                int current = height[left];
                while (left < right && height[left] <= current) {
                    left++;
                }
            } else {
                int current = height[right];
                while (left < right && height[right] <= current) {
                    right--;
                }
            }
        }
        return best;
    }
}

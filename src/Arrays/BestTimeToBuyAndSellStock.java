package Arrays;

/**
 * Problem: Best Time to Buy and Sell Stock (LeetCode 121)
 * Pattern: Single Pass / Running Minimum
 * URL: <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">...</a>
 * <p>
 * Problem Statement:
 * You are given an array prices where prices[i] is the price of a stock on day i.
 * You want to maximize your profit by choosing a single day to buy one stock
 * and choosing a different day in the future to sell that stock.
 * Return the maximum profit you can achieve. If no profit is possible, return 0.
 * <p>
 * Example:
 * Input:  [7, 1, 5, 3, 6, 4]
 * Output: 5  (buy on day 1 at price 1, sell on day 4 at price 6)
 */
public class BestTimeToBuyAndSellStock {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Check Every Pair)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2). For each buy day we scan every later sell day.
    // Space Complexity: O(1). Only a few variables.
    //
    // Why NOT optimal: many pairs are compared again and again.
    // The best buy price seen so far is all we ever need, so we don't have to check every pair.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;

        for (int buy = 0; buy < prices.length; buy++) {
            for (int sell = buy + 1; sell < prices.length; sell++) {
                int profit = prices[sell] - prices[buy];
                maxProfit = Math.max(maxProfit, profit);
            }
        }

        return maxProfit;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Prefix Minimum Array
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). Two linear passes.
    // Space Complexity: O(n). One extra array of size n.
    //
    // Key insight: on day i, the best possible sell is at prices[i], and the best possible
    // buy is the lowest price on any day before i. Precompute those minimums, then subtract.
    //
    // Why NOT optimal: the minimum array stores information we only ever use once,
    // and the final version keeps just a single running value.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitPrefixMin(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        int[] minSoFar = new int[n];
        minSoFar[0] = prices[0];
        for (int i = 1; i < n; i++) {
            minSoFar[i] = Math.min(minSoFar[i - 1], prices[i]);
        }

        int maxProfit = 0;
        for (int i = 1; i < n; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minSoFar[i - 1]);
        }

        return maxProfit;
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Single Pass, Track Minimum)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array.
    // Space Complexity: O(1). Only two variables: minPrice and maxProfit.
    //
    // Idea: walk through the prices while remembering the lowest price seen so far.
    // At each day, ask "what if I sold today?" and keep the best answer.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitOptimal(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;                          // Best day to buy so far
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice); // Profit if sold today
            }
        }

        return maxProfit;
    }
}
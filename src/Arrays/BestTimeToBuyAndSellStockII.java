package Arrays;

/**
 * Problem: Best Time to Buy and Sell Stock II (LeetCode 122)
 * Pattern: Greedy (Sum of Positive Differences) / State DP
 * URL: <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/">...</a>
 * <p>
 * Problem Statement:
 * You are given an integer array prices where prices[i] is the price of a stock on day i.
 * You may buy and sell the stock any number of times, but you can hold at most one share
 * at a time. You must sell before you buy again. Return the maximum profit you can achieve.
 * <p>
 * Example:
 * Input:  [7, 1, 5, 3, 6, 4]
 * Output: 7  (buy at 1, sell at 5 for +4; buy at 3, sell at 6 for +3)
 */
public class BestTimeToBuyAndSellStockII {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Recursion Over Buy/Hold Decisions)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(2^n). Every day branches into "act" or "skip" in each state.
    // Space Complexity: O(n). The recursion goes up to n levels deep.
    //
    // Why NOT optimal: the same (day, holding) subproblems are solved again and again
    // in different branches, and the branching is exponential.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitBruteForce(int[] prices) {
        return explore(prices, 0, false);
    }

    private int explore(int[] prices, int day, boolean holding) {
        if (day == prices.length) {
            return 0;
        }

        if (holding) {
            // Either sell today, or keep holding
            int sell = prices[day] + explore(prices, day + 1, false);
            int hold = explore(prices, day + 1, true);
            return Math.max(sell, hold);
        } else {
            // Either buy today, or stay in cash
            int buy = -prices[day] + explore(prices, day + 1, true);
            int skip = explore(prices, day + 1, false);
            return Math.max(buy, skip);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — State DP (Cash and Holding Per Day)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). One pass, constant work per day.
    // Space Complexity: O(n). Two arrays of size n.
    //
    // Idea: at the end of each day, track two best values:
    //   cash[i]    = max profit if we hold no stock after day i
    //   holding[i] = max profit if we hold one share after day i
    // Each day, we either keep the previous state or transition into the other one.
    //
    // Why NOT optimal: each day only depends on the previous day,
    // so the full arrays are more memory than the problem needs.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitDP(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        int[] cash = new int[n];
        int[] holding = new int[n];
        cash[0] = 0;
        holding[0] = -prices[0];

        for (int i = 1; i < n; i++) {
            // Stay in cash, or sell today (which needed a share from yesterday)
            cash[i] = Math.max(cash[i - 1], holding[i - 1] + prices[i]);
            // Keep holding, or buy today (which needs cash from yesterday)
            holding[i] = Math.max(holding[i - 1], cash[i - 1] - prices[i]);
        }

        return cash[n - 1];
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Greedy: Sum of Every Price Rise)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n). A single pass through the array.
    // Space Complexity: O(1). Only a running total.
    //
    // Key insight: any upward move from day i to day i+1 can be captured by buying
    // on day i and selling on day i+1. A rise spanning several days, like 1 -> 2 -> 3,
    // earns the same profit as taking each step separately (1 + 1 = 2 = 3 - 1).
    // So we just add up every positive day-to-day difference.
    // ─────────────────────────────────────────────────────────────
    public int maxProfitOptimal(int[] prices) {
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }
}
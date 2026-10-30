package TwoPointers;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Problem: Backspace String Compare (LeetCode 844)
 * Pattern: Two Pointers (Scan Backwards, Skip Erased Characters)
 * URL: <a href="https://leetcode.com/problems/backspace-string-compare/">...</a>
 * <p>
 * Problem Statement:
 * Given two strings s and t, return true if they are equal when both are typed
 * into empty text editors. The character '#' means a backspace character that
 * deletes the character before it. If there is no character to delete, nothing happens.
 * <p>
 * Example:
 * Input:  s = "ab#c", t = "ad#c"
 * Output: true
 * Explanation: Both become "ac".
 * <p>
 * This file contains three approaches:
 *   1. Brute force (build both strings with a stack)   O(n + m) time, O(n + m) space
 *   2. Build with StringBuilder, then compare           O(n + m) time, O(n + m) space
 *   3. Two pointers, scan both strings backwards        O(n + m) time, O(1) space
 */

public class BackspaceStringCompare {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Stack Simulation)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n + m). Each character is pushed and popped at most once.
    // Space Complexity: O(n + m) for the two stacks.
    //
    // Simulate typing each string with a stack. A letter is pushed, and a '#'
    // pops the top if the stack is not empty. The final stack contents are the
    // resulting text, and the two stacks are compared.
    // ─────────────────────────────────────────────────────────────
    public boolean backspaceCompareStack(String s, String t) {
        return build(s).equals(build(t));
    }

    private String build(String text) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : text.toCharArray()) {
            if (c == '#') {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : stack) {
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Build Final Strings With StringBuilder
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n + m). One pass over each string.
    // Space Complexity: O(n + m) for the two builders.
    //
    // Same idea as the stack version, but StringBuilder supports deleteCharAt
    // at the end directly, so there is no reversal step. Easier to read than
    // the stack, and still linear, but it allocates the intermediate text.
    // ─────────────────────────────────────────────────────────────
    public boolean backspaceCompareBuilder(String s, String t) {
        return typed(s).equals(typed(t));
    }

    private String typed(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '#') {
                if (!sb.isEmpty()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 - Optimal (Two Pointers, Scan Backwards)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n + m). Each pointer moves across its string at most once.
    // Space Complexity: O(1). No extra strings or stacks.
    //
    // Idea: a '#' affects the characters before it, so read both strings from
    // the end. Keep a skip counter. A '#' increments it, and a letter is skipped
    // while the counter is positive, decrementing it. Once each pointer lands on
    // a surviving letter (or runs off the front), compare the two letters.
    // ─────────────────────────────────────────────────────────────
    public boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;

        while (true) {
            i = nextValidIndex(s, i);
            j = nextValidIndex(t, j);

            if (i < 0 && j < 0) {
                return true;
            }
            if (i < 0 || j < 0) {
                return false;
            }
            if (s.charAt(i) != t.charAt(j)) {
                return false;
            }
            i--;
            j--;
        }
    }

    // Returns the index of the next character that survives the backspaces,
    // scanning leftward from index idx, or -1 if none remain.
    private int nextValidIndex(String text, int idx) {
        int skip = 0;
        while (idx >= 0) {
            if (text.charAt(idx) == '#') {
                skip++;
            } else if (skip > 0) {
                skip--;
            } else {
                break;
            }
            idx--;
        }
        return idx;
    }
}

package Strings;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem: Group Anagrams (LeetCode 49)
 * Pattern: Hash Map with a Canonical Key
 * URL: <a href="https://leetcode.com/problems/group-anagrams/">...</a>
 * <p>
 * Problem Statement:
 * Given an array of strings strs, group the anagrams together.
 * You can return the answer in any order.
 * <p>
 * Example:
 * Input:  ["eat", "tea", "tan", "ate", "nat", "bat"]
 * Output: [["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
 * <p>
 * This file contains three approaches:
 *   1. Compare each string with every group       O(n^2 * k log k) time, O(n * k) space
 *   2. Hash map keyed by sorted characters        O(n * k log k) time, O(n * k) space
 *   3. Hash map keyed by letter counts (optimal)  O(n * k) time, O(n * k) space
 *      (n = number of strings, k = maximum string length)
 */

public class GroupAnagrams {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 — Brute Force (Compare Each String With Every Group)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n^2 * k log k). Each string is checked against
    // every existing group, and each check sorts two strings of length k.
    // Space Complexity: O(n * k) for the output groups.
    //
    // Idea: keep a list of groups. Each group's first word is its
    // representative. A new string joins the first group whose
    // representative is an anagram of it, or starts a new group.
    //
    // Why NOT optimal: the same sorting work is repeated for every
    // comparison, and the number of groups can grow close to n.
    // ─────────────────────────────────────────────────────────────
    public List<List<String>> groupAnagramsBruteForce(String[] strs) {
        List<List<String>> groups = new ArrayList<>();
        for (String s : strs) {
            boolean placed = false;
            for (List<String> group : groups) {
                if (isAnagram(group.get(0), s)) {
                    group.add(s);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                List<String> group = new ArrayList<>();
                group.add(s);
                groups.add(group);
            }
        }
        return groups;
    }

    private boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        Arrays.sort(x);
        Arrays.sort(y);
        return Arrays.equals(x, y);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 — Hash Map Keyed by Sorted Characters
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n * k log k). Each string is sorted once.
    // Space Complexity: O(n * k) for the map and the stored strings.
    //
    // Idea: anagrams become identical after sorting their letters, so
    // the sorted form works as a shared key. Every string with the same
    // key goes into the same bucket.
    //
    // This is the most common interview answer because it is short and
    // easy to explain. Its cost is the sort on each string.
    // ─────────────────────────────────────────────────────────────
    public List<List<String>> groupAnagramsSorted(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 3 — Optimal (Hash Map Keyed by Letter Counts)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n * k). Counting letters is linear, and building
    // the 26-slot key is constant work per string.
    // Space Complexity: O(n * k) for the map and the key strings.
    //
    // Idea: two strings are anagrams exactly when they have the same
    // count of every letter. Build a signature from those 26 counts and
    // use it as the key. This avoids sorting entirely.
    //
    // Trade-off: this assumes lowercase English letters. The signature
    // is built with '#' separators so that "1#11" and "11#1" cannot
    // collide as keys.
    // ─────────────────────────────────────────────────────────────
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            int[] counts = new int[26];
            for (int i = 0; i < s.length(); i++) {
                counts[s.charAt(i) - 'a']++;
            }
            StringBuilder key = new StringBuilder();
            for (int count : counts) {
                key.append(count).append('#');
            }
            groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }
}
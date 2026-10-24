package TwoPointers;

/**
 * Problem: Valid Word Abbreviation (LeetCode 408)
 * Pattern: Two Pointers (Parallel Traversal, Digits Skip Characters)
 * URL: <a href="https://leetcode.com/problems/valid-word-abbreviation/">...</a>
 * <p>
 * Problem Statement:
 * A string can be abbreviated by replacing any number of non-adjacent, non-empty
 * substrings with their lengths. Given a string word and an abbreviation abbr,
 * return whether abbr is a valid abbreviation of word.
 * <p>
 * Example:
 * Input:  word = "internationalization", abbr = "i12iz4n"
 * Output: true
 * <p>
 * This file contains two approaches:
 *   1. Brute force (generate and compare)  O(2^n * n) time, O(n) space
 *   2. Two pointers, single pass           O(n + m) time, O(1) space
 */
public class ValidWordAbbreviation {

    // ─────────────────────────────────────────────────────────────
    // APPROACH 1 - Brute Force (Generate and Compare)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(2^n * n). Each character is either kept or folded
    // into a count, giving 2^n abbreviations, each built in O(n).
    // Space Complexity: O(n) recursion depth, plus strings built along the way.
    //
    // Enumerate every abbreviation of word and check whether the target is
    // among them. Easy to reason about, but far too slow for real inputs.
    // ─────────────────────────────────────────────────────────────
    public boolean validWordAbbreviationBruteForce(String word, String abbr) {
        return generateAndCompare(word, 0, "", 0, abbr);
    }

    private boolean generateAndCompare(String word, int idx, String cur, int count, String target) {
        if (idx == word.length()) {
            String built = count > 0 ? cur + count : cur;
            return built.equals(target);
        }
        // Option 1: keep word[idx] as a literal character
        String prefix = count > 0 ? cur + count : cur;
        if (generateAndCompare(word, idx + 1, prefix + word.charAt(idx), 0, target)) {
            return true;
        }
        // Option 2: abbreviate word[idx] by adding 1 to the running count
        return generateAndCompare(word, idx + 1, cur, count + 1, target);
    }

    // ─────────────────────────────────────────────────────────────
    // APPROACH 2 - Optimal (Two Pointers, Single Pass)
    // ─────────────────────────────────────────────────────────────
    // Time Complexity: O(n + m). Each pointer only moves forward.
    // Space Complexity: O(1). No extra structures.
    //
    // Idea: pointer i walks word, pointer j walks abbr. A letter in abbr must
    // match the current letter in word. A run of digits is a skip count, so
    // advance i by that amount. At the end, both pointers must be exhausted.
    // ─────────────────────────────────────────────────────────────
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0; // pointer for word
        int j = 0; // pointer for abbreviation

        while (i < word.length() && j < abbr.length()) {
            if (Character.isDigit(abbr.charAt(j))) {
                // Leading zero is not allowed
                if (abbr.charAt(j) == '0') return false;

                // Parse the full number
                int num = 0;
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
                    num = num * 10 + (abbr.charAt(j) - '0');
                    j++;
                }
                // Skip that many characters in word
                i += num;
            } else {
                // Letters must match exactly
                if (word.charAt(i) != abbr.charAt(j)) return false;
                i++;
                j++;
            }
        }

        // Both pointers must reach the end
        return i == word.length() && j == abbr.length();
    }
}

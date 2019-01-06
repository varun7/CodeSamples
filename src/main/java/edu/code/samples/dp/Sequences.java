package edu.code.samples.dp;

public class Sequences {
    /**
     * Given two strings of size m,n and set of operations replace (R), insert (I) and delete (D)
     * all at equal cost. Find minimum number of edits (oeprations) required to convert one string into another.
     */
    public static int levenshteinDistances(String s1, String s2) {
        int [][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i=1; i <= s1.length(); i++) {
            for (int j =1 ; j <= s2.length(); j++) {
                if (i ==0) {
                    dp[i][0] = i;
                } else if (j == 0) {
                    dp[i][j] = j;
                } else if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = minimum(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1;
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        }
        if (b <=c) {
            return b;
        }
        return c;
    }

    /**
     * Given a string, find the minimum number of characters to be inserted to convert it to palindrome.
     */
    public static int minimumInsertionForPalindrom(String s) {
        // Initialize.
        int dp[][] = new int[s.length()][s.length()];
        for (int i=0; i< s.length(); i++) {
            for (int j=0; j<s.length(); j++) {
                dp[i][j] = -1;
            }
        }
        return memoizedPalindrome(s, 0, s.length()-1, dp);
    }

    private static int memoizedPalindrome(String s, int i, int j, int [][]dp) {
        if (i >= j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        } else if (s.charAt(i) == s.charAt(j)) {
            return memoizedPalindrome(s, i+1, j-1, dp);
        } else {
            return Integer.min(memoizedPalindrome(s, i+1, j, dp), memoizedPalindrome(s, i, j-1, dp)) + 1;
        }
    }

    /**
     * Longest increasing subsequence problem is to find a sub sequence of a give sequence in which the
     * sub sequence elements are in sorted order lowest to highest, and in which the subsequence is
     * as long as possible. This subsequence is not necessarily contiguous or unique.
     *
     */
    public static int longestIncreasingSubsequence() {
        int input[] ={0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
        int dp [] = new int[input.length];

        // Compute and Initialize
        int max = Integer.MIN_VALUE;
        for (int i=0; i<input.length; i++) {
            dp [i] = getMaximumSequenceWithSmallerEnd(input, dp, i);
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    private static int getMaximumSequenceWithSmallerEnd(int [] input, int [] dp, int index) {
        int max = -1;
        for (int i=0; i< index; i++) {
            if (input[index] > input[i]) {
                if (dp[i] > max) {
                    max = dp[i];
                }
            }
        }
        return max == -1? 1 : max + 1;
    }
}

package edu.code.samples.dp;

import edu.code.samples.generic.Utils;

public class UnclassifiedDP {

    private final static int SECOND = 1;
    private final static int FIRST = 0;

    /**
     * You are given n pair of number. In every pair, the first number is always smaller than the second number.
     * A pair (c,d) can follow another pair (a,c) if b < c. Chain of pairs can be formed in this fashion.
     * Find the longest chain which can be formed from a given set of pairs.
     * @return
     */
    public static int maxChainLength() {
        int [][] pairs = {{5,24}, {39,60}, {15,28}, {27,40}, {50,90}};
//        return _maxChainLength(pairs, -1, 0);
        return tabulatedMaxChainLength(pairs);
    }

    // Brute force solution.
    private static int _maxChainLength(int[][] pairs, int lastIndex, int index) {
        if (index >= pairs.length) {
            return 0;
        }
        if (lastIndex != -1 && pairs[index][FIRST] <= pairs[lastIndex][SECOND]) {
            return _maxChainLength(pairs, index, index +1);
        }
        return Integer.max(_maxChainLength(pairs, lastIndex, index + 1), _maxChainLength(pairs, index, index + 1) + 1);
    }

    // This is very similar to Longest Increasing Subsequence.
    private static int tabulatedMaxChainLength(int [][] pairs) {
        int dp[] = new int[pairs.length];

        int maxChain = Integer.MIN_VALUE;
        for (int i=0; i<pairs.length; i++) {
            dp[i] = maximumChainSizeWithSmallerEndLink(pairs, dp, i);
            maxChain = dp[i] > maxChain ? dp[i] : maxChain;
        }
        return maxChain;
    }

    private static int maximumChainSizeWithSmallerEndLink(int [][] pairs, int []dp, int index) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i < index; i++) {
            if (pairs[i][SECOND] < pairs[index][FIRST]) {
                max = dp[i] > max ? dp[i] : max;
            }
        }
        return max == Integer.MIN_VALUE ? 1 : max + 1;
    }

    /**
     * There is an integer array consisting of positive numbers only. Find maximum possible
     * sum of elements such that there are no 2 consecutive elements present in the sum.
     * @return
     */
    public static int maximumNonConsecutiveSum() {
        int array[] = {1,2,3,4,5,6,7,8,9};
        int dp[] = new int[array.length];
        dp [0] = array[0];
        dp [1] = array[0] > array[1] ? array[0] : array[1];

        for (int i = 2; i < array.length; i++) {
            dp [i] = Integer.max(dp[i-1], dp[i-2] + array[i]);
        }
        return dp[array.length-1];
    }

    /**
     * Given an array of integers where each elements represents the max number of steps that can be
     * made forward from that element. Write a function to return the minimum number of jumps to reach
     * the end of the array (starting from the first element). If an element is 0, then cannot move through that element.
     */
    public static int minimumJumps(int[] array) {
        int dp[] = new int[array.length];
        dp[0] = 0;

        for (int i=1; i<dp.length; i++) {
            int min = Integer.MAX_VALUE - 1;
            for (int j=i-1; j >=0 ; j--) {
                if (i - j <= array[j] && dp[j] < min) {
                    min = dp[j];
                }
            }
            dp[i] = min + 1;
        }

        return dp[array.length-1];
    }

    /**
     * Given a binary matrix, find out the maximum size squar sub-matrix with all 1s.
     */
    public static int maximumSquareMatrix(int [][]matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int [][]dp = new int[rows][cols];

        int maxSubMatrixSize = Integer.MIN_VALUE;
        for (int i=0; i < rows; i++) {
            for (int j=0; j<cols; j++) {
                if (i==0 || j ==0) {
                    dp[i][j] = matrix[i][j];
                } else if (matrix[i][j] == 1) {
                    dp[i][j] = Utils.minimum(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1;
                } else {
                    dp[i][j] = 0;
                }
                if (dp[i][j] > maxSubMatrixSize) {
                    maxSubMatrixSize = dp[i][j];
                }
            }
        }
        return maxSubMatrixSize;
    }
}

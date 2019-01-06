package edu.code.samples.dp;

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
}

package edu.code.samples.judges;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LeetCodeProblems {

    /**
     * https://leetcode.com/problems/minimum-window-substring/
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
     * <p>
     * Example:
     * <p>
     * Input: S = "ADOBECODEBANC", T = "ABC"
     * Output: "BANC"
     * Note:
     * <p>
     * If there is no such window in S that covers all characters in T, return the empty string "".
     * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
     */
    static class MinimumWindowSubString {
        public String minWindow(String s, String t) {

            if (t.length() > s.length()) {
                return "";
            }

            // crete hash.
            Map<Character, Integer> patternMap = createPatternMap(t);
            Map<Character, LinkedList<Integer>> occurrenceMap = new HashMap<>();
            int gStart = 0, gEnd = Integer.MAX_VALUE;
            boolean found = false;

            for (int i = 0; i < s.length(); i++) {
                Character ch = s.charAt(i);
                if (patternMap.containsKey(ch)) {
                    checkAndUpdateCharacterOccurrence(occurrenceMap, patternMap, i, ch);
                    if (hasAllItemsOccurred(occurrenceMap, t)) {
                        found = true;

                        // Find minimum and maximum
                        int minIndex = Integer.MAX_VALUE, index;
                        Character windowStartChar = null;
                        for (Map.Entry<Character, LinkedList<Integer>> entry : occurrenceMap.entrySet()) {
                            index = entry.getValue().get(0);
                            if (index < minIndex) {
                                minIndex = index;
                                windowStartChar = entry.getKey();
                            }
                        }

                        // Remove the first occurrence char index.
                        if (occurrenceMap.get(windowStartChar).size() == 1) {
                            occurrenceMap.remove(windowStartChar);
                        } else {
                            occurrenceMap.get(windowStartChar).remove(0);
                        }

                        if (gEnd - gStart > i - minIndex) {
                            gStart = minIndex;
                            gEnd = i;
                        }
                    }
                }
            }
            if (found) {
                return s.substring(gStart, gEnd + 1);
            }
            return "";
        }

        private void checkAndUpdateCharacterOccurrence(Map<Character, LinkedList<Integer>> occurrenceMap, Map<Character, Integer> patternMap, int i, Character ch) {
            // Has not occurred previously
            if (!occurrenceMap.containsKey(ch)) {
                LinkedList<Integer> list = new LinkedList<>();
                list.add(i);
                occurrenceMap.put(ch, list);
            } else {
                LinkedList<Integer> occurrenceList = occurrenceMap.get(ch);

                if (occurrenceList.size() >= patternMap.get(ch)) {
                    // Remove first occurrence and insert i at the end.
                    occurrenceList.remove(0);
                    occurrenceList.addLast(i);
                } else {
                    occurrenceList.addLast(i);
                }
            }
        }

        private boolean hasAllItemsOccurred(Map<Character, LinkedList<Integer>> occurrenceMap, String t) {
            int count = 0;
            for (Map.Entry<Character, LinkedList<Integer>> entry : occurrenceMap.entrySet()) {
                count += entry.getValue().size();
            }
            if (count == t.length()) {
                return true;
            }
            return false;
        }

        private Map<Character, Integer> createPatternMap(String t) {
            Map<Character, Integer> patternMap = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                Character ch = t.charAt(i);
                if (patternMap.containsKey(ch)) {
                    patternMap.put(ch, patternMap.get(ch) + 1);
                } else {
                    patternMap.put(ch, 1);
                }
            }
            return patternMap;
        }

    }


    /**
     * https://leetcode.com/problems/trapping-rain-water/
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
     * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
     * <p>
     * Example:
     * <p>
     * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     */
    static class TrappingRainWater {
        public int trap(int[] height) {

            if (height.length <= 2) {
                return 0;
            }

            int[] left = new int[height.length];
            int[] right = new int[height.length];

            // Greater on left
            int max = height[0];
            left[0] = height[0];
            for (int i = 1; i < left.length; i++) {
                if (max < height[i]) {
                    max = height[i];
                }
                left[i] = max;
            }

            // Greater on right
            right[height.length - 1] = height[height.length - 1];
            max = height[height.length - 1];
            for (int i = height.length - 2; i >= 0; i--) {
                if (max < height[i]) {
                    max = height[i];
                }
                right[i] = max;
            }

            int area = 0;
            for (int i = 0; i < height.length; i++) {
                area += Math.min(left[i], right[i]) - height[i];
            }
            return area;
        }

    }


    /**
     * https://leetcode.com/problems/sliding-window-maximum/
     * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
     * <p>
     * Example:
     * <p>
     * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * <p>
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     * Note:
     * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
     * <p>
     * Follow up:
     * Could you solve it in linear time?
     * <p>
     * <p>
     * Time Complexity: O(n).
     * It seems more than O(n) at first look. If we take a closer look, we can observe
     * that every element of array is added and removed at most once.
     * So there are total 2n operations.
     */
    static class SlidingWindowMaximum {

        class Pair<K,V> {
            K key;
            V value;

            Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }
        }

        public int[] maxSlidingWindow(int[] nums, int k) {

            if (k == 0 || k > nums.length) {
                return new int[0];
            }

            Deque<Pair<Integer, Integer>> window = new ArrayDeque<>();
            int[] newarray = new int[nums.length - k + 1];

            // Push first k-1 numbers into window.
            for (int i = 0; i < k; i++) {
                updateWindow(window, k, i, nums[i]);
            }

            for (int i = k - 1; i < nums.length; i++) {
                updateWindow(window, k, i, nums[i]);
                newarray[i - k + 1] = window.peekFirst().getValue();
            }
            return newarray;
        }

        private void updateWindow(Deque<Pair<Integer, Integer>> window, int windowSize, int index, int value) {
            // Remove elements from front of window that are more than windowSize distance from index.
            while (!window.isEmpty() && windowSize <= index - window.peekFirst().getKey()) {
                window.removeFirst();
            }

            while (!window.isEmpty() && value > window.peekLast().getValue()) {
                window.removeLast();
            }

            window.addLast(new Pair<>(index, value));
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
     *
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     *
     * Example 1:
     *
     * Input: [7,1,5,3,6,4]
     * Output: 7
     * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
     *              Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
     * Example 2:
     *
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     *              Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     *              engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     *
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    static class BestTimeToBuyAndSellII {
        public int maxProfit(int[] prices) {
            if (prices.length == 0 || prices.length ==1) {
                return 0;
            }
            int profit = 0;
            int i = 0, buyingPrice = prices[0];
            while (i < prices.length-1) {
                while (i < prices.length-1 && prices[i] >= prices[i+1]) {
                    i++;
                }
                buyingPrice = prices[i];

                while (i < prices.length-1 && prices[i] <= prices[i+1]) {
                    i++;
                }
                profit += prices[i] - buyingPrice;
            }
            return profit;
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     * <p>
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     * <p>
     * Example 1:
     * <p>
     * Input: [3,3,5,0,0,3,1,4]
     * Output: 6
     * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
     * Example 2:
     * <p>
     * Input: [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
     * engaging multiple transactions at the same time. You must sell before buying again.
     * Example 3:
     * <p>
     * Input: [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    static class BestTimeToBuyAndSellIII {
        public int maxProfit(int[] prices) {
            if (prices.length < 2) {
                return 0;
            }

            int[] maxLeft = new int[prices.length];
            int[] maxRight = new int[prices.length];

            // Find max profit from 0 to i.
            int min = prices[0];
            maxLeft[0] = 0; // one cannot buy and sell on same day.
            for (int i = 1; i < prices.length; i++) {
                min = Math.min(prices[i], min);
                maxLeft[i] = Math.max(maxLeft[i - 1], prices[i] - min);
            }

            int max = prices[prices.length - 1];
            maxRight[prices.length - 1] = 0; // one cannot buy and sell on last day.
            for (int i = prices.length - 2; i >= 0; i--) {
                maxRight[i] = Math.max(maxRight[i + 1], max - prices[i]);
                max = Math.max(prices[i], max); // i is not included.
            }

            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                maxProfit = Math.max(maxProfit, maxRight[i] + maxLeft[i]);
            }
            return maxProfit;
        }

        public int maxProfitDP(int[] prices) {

            if (prices.length < 2) {
                return 0;
            }

            int transactions = 2;
            int dp[][] = new int[transactions+1][prices.length];

            for (int t=1; t < dp.length; t++) {
                for (int d = 1; d < dp[0].length; d++) {

                    // When transacting on day-d.
                    int max = Integer.MIN_VALUE;
                    for (int i=0; i<d; i++) {
                        max = Math.max(max, dp[t-1][i] + prices[d] - prices[i]);
                    }

                    // dp[t][d-1] means not transacting on day d.
                    dp[t][d] = Math.max(dp[t][d-1], max);
                }
            }
            return dp[transactions][dp[0].length-1];
        }
    }


    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
     * Say you have an array for which the ith element is the price of a given stock on day i.
     *
     * Design an algorithm to find the maximum profit. You may complete at most k transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     *
     * Example 1:
     *
     * Input: [2,4,1], k = 2
     * Output: 2
     * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
     * Example 2:
     *
     * Input: [3,2,6,5,0,3], k = 2
     * Output: 7
     * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
     *              Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
     */
    static class BestTimeToBuyAndSellIV {
        public int maxProfit(int k, int[] prices) {

            if (k == 0 || prices.length < 2) {
                return 0;
            }

            if (k > prices.length/2) {
                return maxTransactionProfit(prices);
            }

            int transactions = k;
            int dp[][] = new int[transactions+1][prices.length];

            for (int t=1; t < dp.length; t++) {
                for (int d = 1; d < dp[0].length; d++) {
                    for (int i=0; i<d; i++) {
                        dp[t][d] = Math.max(dp[t][d], Math.max(dp[t][d-1], dp[t-1][i] + prices[d] - prices[i]));
                    }
                }
            }
            return dp[transactions][dp[0].length-1];
        }

        private int maxTransactionProfit(int[] prices) {
            int maxProfit = 0;
            for (int i=1; i<prices.length; i++) {
                if (prices[i] > prices[i-1]) {
                    maxProfit += prices[i] - prices[i-1];
                }
            }
            return maxProfit;
        }
    }


    /**
     * https://leetcode.com/problems/dungeon-game/
     * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
     * The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the
     * top-left room and must fight his way through the dungeon to rescue the princess.
     *
     * The knight has an initial health point represented by a positive integer. If at any point his health point drops
     * to 0 or below, he dies immediately. Some of the rooms are guarded by demons, so the knight loses
     * health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs
     * that increase the knight's health (positive integers).
     *
     * In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
     *
     * Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
     * For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
     * -2 (K) -3	3
     * -5	 -10	1
     * 10	  30	-5 (P)
     *
     * Note:
     * The knight's health has no upper bound.
     * Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
     */
    static class DungeonGame {
        public int calculateMinimumHP(int[][] dungeon) {
            int rows = dungeon.length;
            int cols = dungeon[0].length;

            int[][] health = new int[rows][cols];

            // We will fill the matrix from right-most, bottom corner to leftmost top corner cell
            health[rows - 1][cols - 1] = Math.max(1 - dungeon[rows - 1][cols - 1], 1);
            for (int c = cols - 2; c >= 0; c--) {
                // If dungeon[rows-1][c] < 0 then health[rows-1][c+1] - dungeon[rows-1][c] will be greater than 1
                health[rows - 1][c] = Math.max(health[rows - 1][c + 1] - dungeon[rows - 1][c], 1);
            }

            for (int r = rows - 2; r >= 0; r--) {
                health[r][cols - 1] = Math.max(health[r + 1][cols - 1] - dungeon[r][cols - 1], 1);
            }

            for (int r = rows - 2; r >= 0; r--) {
                for (int c = cols - 2; c >= 0; c--) {
                    health[r][c] = Math.min(
                            Math.max(health[r + 1][c] - dungeon[r][c], 1),
                            Math.max(health[r][c + 1] - dungeon[r][c], 1)
                    );
                }
            }
            return health[0][0];
        }
    }


    /**
     * https://leetcode.com/problems/shortest-palindrome/
     * Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it.
     * Find and return the shortest palindrome you can find by performing this transformation.
     * <p>
     * Example 1:
     * <p>
     * Input: "aacecaaa"
     * Output: "aaacecaaa"
     * Example 2:
     * <p>
     * Input: "abcd"
     * Output: "dcbabcd"
     */
    static class ShortestPalindrome {
        public String shortestPalindrome(String s) {
            String reverse = reverseString(s);

            // Best case when string is palindrome.
            if (s.equals(reverse)) {
                return reverse;
            }

            int matchOnwards = -1;
            for (int i = 1; i < s.length(); i++) {
                String substr = reverse.substring(i);
                if (s.startsWith(substr)) {
                    matchOnwards = i;
                    break;
                }
            }

            StringBuilder palindrome = new StringBuilder();
            palindrome.append(reverse.substring(0, matchOnwards)).append(s);
            return palindrome.toString();
        }

        private String reverseString(String s) {
            StringBuilder builder = new StringBuilder();
            for (int i = s.length() - 1; i >= 0; i--) {
                builder.append(s.charAt(i));
            }
            return builder.toString();
        }
    }


    /**
     * https://leetcode.com/problems/the-skyline-problem/
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).
     * <p>
     * Buildings  Skyline Contour
     * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
     * <p>
     * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
     * <p>
     * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
     * <p>
     * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
     * <p>
     * Notes:
     * <p>
     * The number of buildings in any input list is guaranteed to be in the range [0, 10000].
     * The input list is already sorted in ascending order by the left x position Li.
     * The output list must be sorted by the x position.
     * There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
     */
    static class SkyLineProblem {

        static class Line {
            int left;
            int height;

            public Line(int l, int h) {
                this.left = l;
                this.height = h;
            }
        }

        public List<int[]> getSkyline(int[][] buildings) {
            if (buildings.length == 0) {
                return new ArrayList<>();
            }
            return convert(findSkyLines(buildings, 0, buildings.length - 1));
        }

        private List<int[]> convert(List<Line> lines) {
            List<int[]> result = new ArrayList<>();
            for (Line line : lines) {
                int[] array = new int[2];
                array[0] = line.left;
                array[1] = line.height;
                result.add(array);
            }
            return result;
        }

        private List<Line> findSkyLines(int[][] buildings, int low, int high) {
            if (low == high) {
                // Only one element is there
                List<Line> list = new ArrayList<>();
                Line one = new Line(buildings[low][0], buildings[low][2]);
                Line two = new Line(buildings[low][1], 0);
                list.add(one);
                list.add(two);
                return list;
            }
            int mid = low + (high - low) / 2;
            List<Line> leftSkyLine = findSkyLines(buildings, low, mid);
            List<Line> rightSkyLine = findSkyLines(buildings, mid + 1, high);
            return mergeSkyLines(leftSkyLine, rightSkyLine);
        }

        private List<Line> mergeSkyLines(List<Line> leftList, List<Line> rightList) {
            List<Line> mergedSkyLine = new ArrayList<>();

            Line prev = null, leftLine, rightLine;
            int lh = -1, rh = -1, maxH = -1;
            while (!leftList.isEmpty() && !rightList.isEmpty()) {
                leftLine = leftList.get(0);
                rightLine = rightList.get(0);

                if (leftLine.left < rightLine.left) {
                    lh = leftLine.height;
                    maxH = Math.max(lh, rh);
                    if (isPartOfSkyLine(leftLine.left, maxH, prev)) {
                        prev = new Line(leftLine.left, maxH);
                        mergedSkyLine.add(prev);
                    }
                    leftList.remove(0);
                } else if (leftLine.left > rightLine.left) {
                    rh = rightLine.height;
                    maxH = Math.max(lh, rh);

                    if (isPartOfSkyLine(rightLine.left, maxH, prev)) {
                        prev = new Line(rightLine.left, maxH);
                        mergedSkyLine.add(prev);
                    }
                    rightList.remove(0);
                } else {
                    int l;
                    lh = leftLine.height;
                    rh = rightLine.height;
                    if (leftLine.height > rightLine.height) {
                        l = leftLine.left;
                    } else {
                        l = rightLine.left;
                    }
                    maxH = Math.max(lh, rh);
                    if (isPartOfSkyLine(l, maxH, prev)) {
                        prev = new Line(l, maxH);
                        mergedSkyLine.add(prev);
                    }
                    leftList.remove(0);
                    rightList.remove(0);
                }
            }

            while (!leftList.isEmpty()) {
                leftLine = leftList.get(0);
                lh = leftLine.height;
                maxH = Math.max(lh, rh);
                if (isPartOfSkyLine(leftLine.left, maxH, prev)) {
                    prev = new Line(leftLine.left, maxH);
                    mergedSkyLine.add(prev);
                }
                leftList.remove(0);
            }

            while (!rightList.isEmpty()) {
                rightLine = rightList.get(0);
                rh = rightLine.height;
                maxH = Math.max(lh, rh);
                if (isPartOfSkyLine(rightLine.left, maxH, prev)) {
                    prev = new Line(rightLine.left, maxH);
                    mergedSkyLine.add(prev);
                }
                rightList.remove(0);
            }

            return mergedSkyLine;
        }

        private boolean isPartOfSkyLine(int left, int height, Line prev) {
            if (prev != null && (prev.height == height || prev.left == left)) {
                return false;
            }
            return true;
        }
    }


    /**
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
     * You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
     * <p>
     * Example:
     * <p>
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     */
    static class CountSmallerAfterSelf {
        public List<Integer> countSmaller(int[] nums) {
            return divideAndConquerSolution(nums);
        }

        private List<Integer> divideAndConquerSolution(int[] nums) {

            List<Integer> result = new ArrayList<>(nums.length);
            if (nums.length == 0) {
                return result;
            }

            int[] count = new int[nums.length]; // All elements are 0 by default.
            int[] index = new int[nums.length];
            mergeSort(index, count, nums, 0, nums.length - 1);


            for (int i = 0; i < nums.length; i++) {
                result.add(count[i]);
            }
            return result;

        }

        private void mergeSort(int[] index, int[] count, int[] input, int lo, int hi) {
            if (lo == hi) {
                index[lo] = lo;
                return;
            }
            int mid = lo + (hi - lo) / 2;
            mergeSort(index, count, input, lo, mid);
            mergeSort(index, count, input, mid + 1, hi);
            merge(index, count, input, lo, mid, hi);
        }

        private void merge(int[] index, int[] count, int[] input, int lo, int mid, int hi) {

            List<Integer> indexCopy = new ArrayList<>(hi - lo + 1);
            List<Integer> inputCopy = new ArrayList<>(hi - lo + 1);

            int i = lo, j = mid + 1, c = 0;

            while (i <= mid && j <= hi) {
                // Less than equal is important, having only less than will not lead desired result.
                if (input[i] <= input[j]) {
                    indexCopy.add(index[i]);
                    inputCopy.add(input[i]);
                    // Increment the count by C.
                    count[index[i]] += c;
                    i++;
                } else {
                    indexCopy.add(index[j]);
                    inputCopy.add(input[j]);
                    j++;
                    // Every time I copy from right sub-array, keep a count.
                    c++;
                }
            }

            while (i <= mid) {
                indexCopy.add(index[i]);
                inputCopy.add(input[i]);
                count[index[i]] += c;
                i++;
            }

            while (j <= mid) {
                indexCopy.add(index[j]);
                inputCopy.add(input[j]);
                j++;
            }

            for (int k = 0; k < inputCopy.size(); k++) {
                index[lo + k] = indexCopy.get(k);
                input[lo + k] = inputCopy.get(k);
            }
        }

        private List<Integer> bitSolution(int[] nums) {
            if (nums.length == 0) {
                return new ArrayList<>();
            }
            return createBITAndCount(nums, coordinateCompression(nums));
        }


        /**
         * This method creates a BIT with the help of rank Map. And count the inversions during insertion.
         * Step:
         * 1. Iterate over original input array from right to left. i -> [size-1...0]
         * 1.a. Find index/rank of current element array[i] in sorted array. rankInSortedArray
         * 1.b. Find all elements with rankInSortedArray greater than current element's rankInSortedArray. i.e. bit.query([0..rankInSortedArray-1]).
         * 1.c. Now add current element's rank in BIT.
         */
        private List<Integer> createBITAndCount(int[] array, Map<Integer, Integer> ranks) {
            List<Integer> count = new ArrayList<>(array.length);
            int rankInSortedArray;
            BIT bit = new BIT(ranks.size() + 1);
            for (int i = array.length - 1; i > -1; i--) {
                rankInSortedArray = ranks.get(array[i]);
                count.add(0, bit.query(rankInSortedArray - 1));
                bit.update(rankInSortedArray, 1);
            }
            return count;
        }

        /**
         * This method applies coordinate compression.
         * Steps:
         * 1. Copy the input array to copy[]
         * 2. Sort the copy array.
         * 3. Iterate over sorted array and create a map of key -> element and value -> position in sorted array.
         * When array contain duplicate values then first occurrence is put in map.
         * <p>
         * Example
         * input =     [92, 631, 50, 7]
         * copy =      [7, 50, 92, 631]
         * rankMap     7 => 1, 50 => 2, 92 => 3, 631 => 4
         */
        private Map<Integer, Integer> coordinateCompression(int[] array) {
            int[] copy = Arrays.copyOf(array, array.length);
            Arrays.sort(copy);

            Map<Integer, Integer> rankMap = new HashMap<>();
            for (int i = 0, rank = 1; i < copy.length; i++) {
                if (!rankMap.containsKey(copy[i])) {
                    rankMap.put(copy[i], rank++);
                }
            }
            return rankMap;
        }

        static class BIT {
            private int[] tree;

            public BIT(int size) {
                this.tree = new int[size];
            }

            public void update(int index, int val) {
                for (int i = index; i < tree.length; i += (i & -i)) {
                    tree[i] += val;
                }
            }

            public int query(int index) {
                int val = 0;
                for (int i = index; i > 0; i -= (i & -i)) {
                    val += tree[i];
                }
                return val;
            }
        }
    }


    /**
     * https://leetcode.com/problems/reverse-pairs/
     * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
     * <p>
     * You need to return the number of important reverse pairs in the given array.
     * <p>
     * Example1:
     * <p>
     * Input: [1,3,2,3,1]
     * Output: 2
     * Example2:
     * <p>
     * Input: [2,4,3,5,1]
     * Output: 3
     */
    static class ReversePairs {
        public int reversePairs(int[] nums) {
            if (nums.length <= 1) {
                return 0;
            }

            int[] sortedCopy = Arrays.copyOf(nums, nums.length);
            Arrays.sort(sortedCopy);
            int copyLength = 0;

            // Remove the duplicates from sorted array.
            for (int i = 0; i < sortedCopy.length; i++) {
                if (copyLength == 0 || sortedCopy[copyLength - 1] != sortedCopy[i]) {
                    sortedCopy[copyLength++] = sortedCopy[i];
                }
            }

            BIT bit = new BIT(copyLength + 1);
            int rank, rankOfHalf, result = 0;
            for (int i = nums.length - 1; i > -1; i--) {
                rankOfHalf = getRank(sortedCopy, 0, copyLength, 1.0 * nums[i] / 2);
                rank = getRank(sortedCopy, 0, copyLength - 1, nums[i]);
                result += bit.query(rankOfHalf);
                bit.update(rank + 1, 1);
            }
            return result;
        }

        /**
         * Finds the first index with nums[index] * 2 >= val
         */
        private int getRank(int[] nums, int lo, int hi, double val) {
            int mid;
            while (lo < hi) {
                mid = lo + (hi - lo) / 2;
                if (nums[mid] >= val) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }

        static class BIT {
            private int[] tree;

            public BIT(int size) {
                this.tree = new int[size];
            }

            public void update(int index, int val) {
                for (int i = index; i < tree.length; i += (i & -i)) {
                    tree[i] += val;
                }
            }

            public int query(int index) {
                int val = 0;
                for (int i = index; i > 0; i -= (i & -i)) {
                    val += tree[i];
                }
                return val;
            }
        }
    }


    /**
     * https://leetcode.com/problems/regular-expression-matching/
     * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
     * <p>
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     * The matching should cover the entire input string (not partial).
     * <p>
     * Note:
     * <p>
     * s could be empty and contains only lowercase letters a-z.
     * p could be empty and contains only lowercase letters a-z, and characters like . or *.
     * Example 1:
     * <p>
     * Input:
     * s = "aa"
     * p = "a"
     * Output: false
     * Explanation: "a" does not match the entire string "aa".
     */
    static class RegularExpressionMatching {
        public boolean isMatch(String s, String p) {
            return dpPatternMatching(s, p);
        }

        private boolean recursivePatternMatching(String text, String pattern) {
            if (pattern.isEmpty()) {
                return text.isEmpty();
            }

            boolean startMatch = (!text.isEmpty() && doMatch(text.charAt(0), pattern.charAt(0)));

            // When there is no wild-card after first character.
            if (pattern.length() == 1 || pattern.charAt(1) != '*') {
                return startMatch && recursivePatternMatching(text.substring(1), pattern.substring(1));
            }

            /*
             * When there is wild card after first character
             * There are 2 possibilities:
             * 1. Zero occurrence.
             * 2. 1 or more occurrence.
             */
            return recursivePatternMatching(text, pattern.substring(2)) ||
                    (startMatch && recursivePatternMatching(text.substring(1), pattern));
        }

        private boolean dpPatternMatching(String text, String pattern) {
            int rows = text.length() + 1, cols = pattern.length() + 1;
            boolean dp[][] = new boolean[rows][cols];

            // Initialize dp array.
            dp[0][0] = true;
            for (int i = 1; i < rows; i++) {
                dp[i][0] = false;
            }

            for (int j = 1; j < cols; j++) {
                dp[0][j] = pattern.charAt(j - 1) == '*' ? dp[0][j - 2] : false;
            }

            for (int i = 1; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    char p = pattern.charAt(j - 1);
                    char t = text.charAt(i - 1);
                    if (p == '*') {
                        char charBeforeStar = pattern.charAt(j - 2);

                        // if pattern is xa* and text is xaaa
                        // dp [i-1][j] (i=3, j =3) represents that xa* has matched xaa
                        dp[i][j] = dp[i][j - 2]  // zero occurrence
                                || (doMatch(t, charBeforeStar) && dp[i - 1][j]); // 1 or more occurrence
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] && doMatch(t, p);
                    }
                }
            }
            return dp[rows - 1][cols - 1];
        }

        private boolean doMatch(char t, char p) {
            if (t == p || p == '.' || t == '.') {
                return true;
            }
            return false;
        }
    }


    /**
     * https://leetcode.com/problems/partition-equal-subset-sum/
     * Given a non-empty array containing only positive integers, find if the array can be partitioned into
     * two subsets such that the sum of elements in both subsets is equal.
     * <p>
     * Note:
     * Each of the array element will not exceed 100.
     * The array size will not exceed 200.
     * Example 1:
     * <p>
     * Input: [1, 5, 11, 5]
     * <p>
     * Output: true
     * <p>
     * Explanation: The array can be partitioned as [1, 5, 5] and [11].
     */
    static class PartitionEqualSum {

        /**
         * Question can be converted to find subset of array with sum = total sum of array /2.
         */
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum % 2 != 0) {
                return false;
            }
            return isSubsetWithSum(nums, sum / 2);
        }

        private boolean isSubsetWithSum(int[] set, int target) {
            boolean[][] dp = new boolean[set.length + 1][target + 1];

            dp[0][0] = true;
            for (int i = 0; i <= set.length; i++) {
                for (int j = 0; j <= target; j++) {

                    // Initialize first row and column.
                    if (j == 0) {
                        dp[i][j] = true;
                        continue;
                    }
                    if (i == 0) {
                        dp[i][j] = false;
                        continue;
                    }
                    dp[i][j] = dp[i - 1][j] || (j - set[i - 1] >= 0 && dp[i - 1][j - set[i - 1]]);
                }
            }
            return dp[set.length][target];
        }

        private boolean isSubsetWithSpaceOptimized(int[] nums, int target) {
            boolean dp[] = new boolean[target+1];

            dp[0] = true;
            for (int i=0; i < nums.length; i++) {
                for (int t=target; t > 0; t--) {
                    dp[t] = (dp[t] || (t >= nums[i] && dp[t-nums[i]]));
                }
            }
            return dp[target];
        }
    }


    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.
     */
    static class PartiotionKEqualSums {

        class DFSSolution {
            public boolean canPartitionKSubsets(int[] nums, int k) {
                int sum = 0;
                boolean[] visited = new boolean[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    sum += nums[i];
                    visited[i] = false;
                }
                if (sum % k != 0) {
                    return false;
                }
                return isSubsetWithSum(nums, visited, sum / k, k, sum / k, 0);
            }

            /**
             * At first this seems like subset sum partitioning problem but backtracking approach failed
             * as there can be multiple sets:
             * Example:
             * For {10,10,10,7,7,7,7,7,7,6,6,6}
             * if the first set you take is 10, 10, 10 = 30 then there is no other solution but if you take
             * 10 + 6 + 14 (7,7)
             * 10 + 6 + 14 (7,7)
             * 10 + 6 + 14 (7,7)
             * Then this array can be partitioned into 3 subset of equal sum.
             * <p>
             * This solution performs simple dfs.
             */
            private boolean isSubsetWithSum(int[] set, boolean[] visited, int target, int k, int targetSum, int index) {
                if (k == 1) {
                    return true;
                }

                if (target < 0) {
                    return false;
                }

                if (target == 0) {
                    return isSubsetWithSum(set, visited, targetSum, k - 1, targetSum, 0);
                }

                for (int i = index; i < set.length; i++) {
                    if (!visited[i]) {
                        visited[i] = true;
                        if (isSubsetWithSum(set, visited, target - set[i], k, targetSum, index + 1)) {
                            return true;
                        }
                        visited[i] = false;
                    }
                }
                return false;
            }

        }

        class ExhaustiveChoiceSolution {

            private int[] nums;
            private int target;
            private int groups;

            public boolean canPartitionKSubsets(int[] nums, int k) {
                this.nums = nums;
                this.groups = k;

                int sum = Arrays.stream(nums).sum();
                if (sum % k != 0) {
                    return false;
                }
                this.target = sum/k;
                return isSubsetWithSum(new int[groups], 0);
            }

            private boolean isSubsetWithSum(int[] subsets, int index) {
                if (index == nums.length) {
                    return isSolved(subsets);
                }

                // As per free choice, a index in array can be part of any of the k subset
                // Try all of them
                for (int k=0; k<subsets.length; k++) {
                    if (subsets[k] + nums[index] <= target) {

                        // Make a choice to add index to subset-k
                        subsets[k] += nums[index];
                        if (isSubsetWithSum(subsets, index+1)) {
                            return true;
                        }

                        // If this doesn't work out then undo this choice.
                        subsets[k] -= nums[index];
                    }

                }
                return false;
            }

            private boolean isSolved(int[] subsets) {
                for (int i: subsets) {
                    if (i != target) {
                        return false;
                    }
                }
                return true;
            }

        }

        enum Result { TRUE, FALSE }
        class ExhaustiveChoiceSolutionsOptimized {

            boolean search(int used, int todo, Result[] memo, int[] nums, int target) {
                if (memo[used] == null) {
                    memo[used] = Result.FALSE;
                    int targ = (todo - 1) % target + 1;
                    for (int i = 0; i < nums.length; i++) {
                        if ((((used >> i) & 1) == 0) && nums[i] <= targ) {
                            if (search(used | (1<<i), todo - nums[i], memo, nums, target)) {
                                memo[used] = Result.TRUE;
                                break;
                            }
                        }
                    }
                }
                return memo[used] == Result.TRUE;
            }

            public boolean canPartitionKSubsets(int[] nums, int k) {
                int sum = Arrays.stream(nums).sum();
                if (sum % k > 0) return false;

                Result[] memo = new Result[1 << nums.length];
                memo[(1 << nums.length) - 1] = Result.TRUE;
                return search(0, sum, memo, nums, sum / k);
            }
        }
    }


    /**
     * https://leetcode.com/problems/russian-doll-envelopes/
     * You have a number of envelopes with widths and heights given as a pair of integers (w, h).
     * One envelope can fit into another if and only if both the width and height of one envelope
     * is greater than the width and height of the other envelope.
     *
     * What is the maximum number of envelopes can you Russian doll? (put one inside other)
     *
     * Note:
     * Rotation is not allowed.
     *
     * Example:
     * Input: [[5,4],[6,4],[6,7],[2,3]]
     * Output: 3
     * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
     */
    static class RussianDollEnvelopes {
        private static final int WIDTH = 0;
        private static final int HEIGHT = 1;

        public int maxEnvelopes(int[][] envelopes) {
            if (envelopes.length == 0) {
                return 0;
            }
            Arrays.sort(envelopes, (int[] a, int[] b) -> {
                return Integer.compare(a[0], b[0]);
            });

            int[] dp = new int[envelopes.length];
            int max = 0;
            dp[0] = 0;

            for (int i = 1; i < envelopes.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (envelopes[i][WIDTH] > envelopes[j][WIDTH] && envelopes[i][HEIGHT] > envelopes[j][HEIGHT]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                max = Math.max(max, dp[i]);
            }
            return max + 1;
        }
    }


    /**
     * https://leetcode.com/problems/burst-balloons/
     * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums.
     * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
     * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
     * <p>
     * Find the maximum coins you can collect by bursting the balloons wisely.
     * <p>
     * Note:
     * <p>
     * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
     * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
     * Example:
     * <p>
     * Input: [3,1,5,8]
     * Output: 167
     * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
     * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
     */
    static class BurstBalloons {
        public int maxCoins(int[] coins) {
//            int [][] dp = new int[coins.length][coins.length];
//            return maxCoinsTopDown(dp, coins, 0, coins.length-1);
            if (coins.length == 0) {
                return 0;
            }
            return maxCoinsBottomUp(coins);
        }

        private int maxCoinsBottomUp(int[] coins) {
            int[][] dp = new int[coins.length][coins.length];

            for (int e = 0; e < dp.length; e++) {
                for (int s = e; s >= 0; s--) {
                    for (int i = s; i <= e; i++) {
                        int leftSum = sumAt(dp, s, i - 1);
                        int rightSum = sumAt(dp, i + 1, e);
                        dp[s][e] = Math.max(dp[s][e], leftSum + rightSum + coinAt(coins, s - 1) * coinAt(coins, i) * coinAt(coins, e + 1));
                    }
                }
            }
            return dp[0][dp.length - 1];
        }

        private int maxCoinsTopDown(int[][] dp, int[] coins, int start, int end) {
            if (start > end) {
                return 0;
            }

            if (dp[start][end] != 0) {
                return dp[start][end];
            }

            for (int i = start; i <= end; i++) {
                int val = maxCoinsTopDown(dp, coins, start, i - 1) +
                        coinAt(coins, start - 1) * coinAt(coins, i) * coinAt(coins, end + 1) +
                        maxCoinsTopDown(dp, coins, i + 1, end);
                dp[start][end] = Math.max(dp[start][end], val);
            }
            return dp[start][end];
        }

        private int sumAt(int[][] dp, int r, int c) {
            if (r < 0 || c < 0 || r >= dp.length || c >= dp.length) {
                return 0;
            }
            return dp[r][c];
        }

        private int coinAt(int[] coins, int index) {
            if (index < 0 || index >= coins.length) {
                return 1;
            }
            return coins[index];
        }
    }


    /**
     * https://leetcode.com/problems/queue-reconstruction-by-height/
     * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k),
     * where h is the height of the person and k is the number of people in front of this person who have a height greater
     * than or equal to h. Write an algorithm to reconstruct the queue.
     * <p>
     * Note: The number of people is less than 1,100.
     * <p>
     * Example
     * Input:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * Output:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     */
    static class QueueReconstructionByHeight {
        public int[][] reconstructQueue(int[][] people) {
            Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            int[][] result = new int[people.length][2];
            initializeMatrix(result);

            for (int i = 0; i < people.length; i++) {
                int pos = findPosition(result, people[i][0], people[i][1]);
                result[pos][0] = people[i][0];
                result[pos][1] = people[i][1];
            }
            return result;
        }

        private int findPosition(int[][] result, int height, int pos) {
            int emptySpaces = -1;
            for (int i = 0; i < result.length; i++) {
                if (result[i][0] == -1 || result[i][0] == height) {
                    emptySpaces++;
                }

                if (emptySpaces == pos) {
                    return i;
                }
            }
            return result.length - 1;
        }

        private void initializeMatrix(int[][] result) {
            for (int i = 0; i < result.length; i++) {
                result[i][0] = -1;
                result[i][1] = -1;
            }
        }
    }


    /**
     * https://leetcode.com/problems/median-of-two-sorted-arrays/submissions/
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     * <p>
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     * You may assume nums1 and nums2 cannot be both empty.
     * <p>
     * Example 1:
     * nums1 = [1, 3]
     * nums2 = [2]
     * <p>
     * The median is 2.0
     * Example 2:
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * <p>
     * The median is (2 + 3)/2 = 2.5
     */
    static class MedianOfTwoSortedArrays {

        int[] a; // a is always the smaller array.
        int[] b;
        int medianElement;
        boolean isEven;

        public double findMedianSortedArrays(int[] num1, int[] num2) {
            // Ensure a is always the smaller one.
            if (num1.length < num2.length) {
                a = num1;
                b = num2;
            } else {
                a = num2;
                b = num1;
            }

            medianElement = (a.length + b.length + 1) / 2;
            isEven = (a.length + b.length) % 2 == 0;

            // case-0: when one of the array is empty
            Double potentialMedian = medianWhenAisEmpty();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-1: when all elements from A are included.
            potentialMedian = medianIfAllElementsFromA();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-2: when no element from A is included.
            potentialMedian = medianIfNoElementFromA();
            if (potentialMedian != null) {
                return potentialMedian;
            }

            // case-3: when some elements from A are included and some (one/more) from B is included.
            return distributedMedian();
        }

        private Double medianIfNoElementFromA() {
            if (medianElement <= b.length && a[0] >= b[medianElement - 1]) {
                if (!isEven) {
                    return Double.valueOf(b[medianElement - 1]);
                } else if (medianElement == b.length) {
                    return Double.valueOf((b[medianElement - 1] + a[0]) / 2.0);
                } else {
                    return Double.valueOf((b[medianElement - 1] + Math.min(b[medianElement], a[0])) / 2.0);
                }
            }
            return null;
        }

        private Double medianIfAllElementsFromA() {
            int indexB = medianElement == a.length ? 0 : medianElement - a.length - 1;
            // if below condition is true then all elements of A are included.
            if (medianElement >= a.length && a[a.length - 1] <= b[indexB]) {
                if (!isEven) {
                    if (medianElement == a.length) {
                        return Double.valueOf(a[a.length - 1]);
                    } else {
                        return Double.valueOf(b[indexB]);
                    }
                } else {
                    if (medianElement == a.length) {
                        return Double.valueOf((a[a.length - 1] + b[0]) / 2.0);
                    } else {
                        return Double.valueOf((b[indexB] + b[indexB + 1]) / 2.0);
                    }
                }
            }
            return null;
        }

        private Double medianWhenAisEmpty() {
            if (a.length == 0) {
                if (isEven) {
                    return Double.valueOf((b[medianElement] + b[medianElement - 1]) / 2.0);
                }
                return Double.valueOf(b[medianElement - 1]);
            }
            return null;
        }

        private Double distributedMedian() {
            int posA = 0, posB = 0;
            int lo = 0, hi = a.length;

            while (lo <= hi) {
                posA = lo + (hi - lo) / 2;
                posB = medianElement - (posA + 1) - 1;

                // case-1: shift left in A.
                if (posB < b.length - 1 && a[posA] > b[posB + 1]) {
                    hi = posA - 1;
                } else if (posA < a.length - 1 && a[posA + 1] < b[posB]) {
                    lo = posA + 1;
                } else {
                    int maxLeft = Math.max(a[posA], b[posB]);
                    if (!isEven) {
                        return Double.valueOf(maxLeft);
                    } else {
                        int maxRight = Math.min(
                                posA + 1 <= a.length - 1 ? a[posA + 1] : Integer.MAX_VALUE,
                                posB + 1 <= b.length - 1 ? b[posB + 1] : Integer.MAX_VALUE
                        );
                        return Double.valueOf((maxLeft + maxRight) / 2.0);
                    }
                }
            }
            return 0d;
        }
    }


    /**
     * https://leetcode.com/problems/range-sum-query-2d-immutable/
     * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
     * <p>
     * Range Sum Query 2D
     * The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.
     * <p>
     * Example:
     * Given matrix = [
     * [3, 0, 1, 4, 2],
     * [5, 6, 3, 2, 1],
     * [1, 2, 0, 1, 5],
     * [4, 1, 0, 1, 7],
     * [1, 0, 3, 0, 5]
     * ]
     * <p>
     * sumRegion(2, 1, 4, 3) -> 8
     * sumRegion(1, 1, 2, 2) -> 11
     * sumRegion(1, 2, 2, 4) -> 12
     * Note:
     * You may assume that the matrix does not change.
     * There are many calls to sumRegion function.
     * You may assume that row1 ≤ row2 and col1 ≤ col2.
     */
    static class RangeSumQuery2D {

        static class CacheSolution {
            private int[][] cache;

            public CacheSolution(int[][] matrix) {
                constructCache(matrix);
            }

            public void constructCache(int[][] matrix) {
                if (matrix.length == 0 || matrix[0].length == 0) return;
                cache = new int[matrix.length + 1][matrix[0].length + 1];
                for (int r = 0; r < matrix.length; r++) {
                    for (int c = 0; c < matrix[0].length; c++) {
                        cache[r + 1][c + 1] = cache[r + 1][c] + cache[r][c + 1] + matrix[r][c] - cache[r][c];
                    }
                }
            }

            public int sumRegion(int row1, int col1, int row2, int col2) {
                return cache[row2 + 1][col2 + 1] - cache[row1][col2 + 1] - cache[row2 + 1][col1] + cache[row1][col1];
            }
        }

        static class BITSolution {

            class BIT2D {
                int[][] tree;
                int[][] input;

                public BIT2D(int[][] input) {
                    if (input.length == 0) {
                        return;
                    }
                    tree = new int[input.length + 1][input[0].length + 1];
                    for (int i = 0; i < input.length; i++) {
                        for (int j = 0; j < input[0].length; j++) {
                            update(i + 1, j + 1, input[i][j]);
                        }
                    }
                }

                public void update(int x, int y, int val) {
                    for (int i = x; i < tree.length; i += (i & -i)) {
                        for (int j = y; j < tree[0].length; j += (j & -j)) {
                            tree[i][j] += val;
                        }
                    }
                }

                public int query(int x, int y) {
                    int sum = 0;
                    for (int i = x; i > 0; i -= (i & -i)) {
                        for (int j = y; j > 0; j -= (j & -j)) {
                            sum += tree[i][j];
                        }
                    }
                    return sum;
                }
            }

            BIT2D bit;
            int[][] grid;

            public BITSolution(int[][] matrix) {
                this.grid = matrix;
                bit = new BIT2D(matrix);
            }

            public int sumRegion(int row1, int col1, int row2, int col2) {
                if (grid.length == 0) {
                    return 0;
                }
                int r1 = row1 + 1, r2 = row2 + 1, c1 = col1 + 1, c2 = col2 + 1;
                return bit.query(r2, c2) - bit.query(r2, c1 - 1) - bit.query(r1 - 1, c2) + bit.query(r1 - 1, c1 - 1);
            }
        }
    }


    /**
     * https://leetcode.com/problems/sort-characters-by-frequency/
     * Given a string, sort it in decreasing order based on the frequency of characters.
     * <p>
     * Example 1:
     * <p>
     * Input:
     * "tree"
     * <p>
     * Output:
     * "eert"
     * <p>
     * Explanation:
     * 'e' appears twice while 'r' and 't' both appear once.
     * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
     * Example 2:
     * <p>
     * Input:
     * "cccaaa"
     * <p>
     * Output:
     * "cccaaa"
     * <p>
     * Explanation:
     * Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
     * Note that "cacaca" is incorrect, as the same characters must be together.
     * Example 3:
     * <p>
     * Input:
     * "Aabb"
     * <p>
     * Output:
     * "bbAa"
     * <p>
     * Explanation:
     * "bbaA" is also a valid answer, but "Aabb" is incorrect.
     * Note that 'A' and 'a' are treated as two different characters.
     */
    class SortCharacterByFrequency {

        class Pair {
            char ch;
            int count;

            public Pair(char ch, int count) {
                this.ch = ch;
                this.count = count;
            }
        }

        public String frequencySort(String s) {
//        return arraySortSolution(s);
            return heapSolution(s);
        }

        public String arraySortSolution(String s) {
            Map<Character, Integer> charMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                int count = 0;
                if (charMap.containsKey(ch)) {
                    count = charMap.get(ch);
                }
                charMap.put(ch, count + 1);
            }

            Pair[] pairs = new Pair[charMap.size()];
            int len = 0;
            for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
                pairs[len++] = new Pair(entry.getKey(), entry.getValue());
            }

            Comparator<Pair> comparator = (a, b) -> b.count - a.count;
            Arrays.sort(pairs, comparator);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < pairs.length; i++) {
                for (int j = 0; j < pairs[i].count; j++) {
                    result.append(pairs[i].ch);
                }
            }
            return result.toString();
        }

        public String heapSolution(String s) {
            // Step-1: Create a map.
            Map<Character, Integer> charMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                int count = 0;
                if (charMap.containsKey(ch)) {
                    count = charMap.get(ch);
                }
                charMap.put(ch, count + 1);
            }

            // Step-2: Create a priority queue
            Comparator<Map.Entry<Character, Integer>> comparator = (a, b) -> b.getValue() - a.getValue();
            PriorityQueue<Map.Entry<Character, Integer>> heap = new PriorityQueue<>(comparator);
            heap.addAll(charMap.entrySet());

            // Step-3: Create result
            StringBuilder result = new StringBuilder();
            while (!heap.isEmpty()) {
                Map.Entry<Character, Integer> entry = heap.poll();
                for (int j = 0; j < entry.getValue(); j++) {
                    result.append(entry.getKey());
                }
            }
            return result.toString();
        }
    }


    /**
     * https://leetcode.com/problems/top-k-frequent-elements/
     * Given a non-empty array of integers, return the k most frequent elements.
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     * Example 2:
     * <p>
     * Input: nums = [1], k = 1
     * Output: [1]
     * Note:
     * <p>
     * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
     * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
     */
    class KFrequentElements {
        public List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> countMap = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int count = countMap.getOrDefault(nums[i], Integer.valueOf(0));
                countMap.put(nums[i], count + 1);
            }

            Comparator<Map.Entry<Integer, Integer>> comparator = (a, b) -> b.getValue() - a.getValue();
            PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue(comparator);
            // Instead of doing add all we can limit it to k elements.
            heap.addAll(countMap.entrySet());

            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < k && !heap.isEmpty(); i++) {
                result.add(heap.poll().getKey());
            }
            return result;
        }
    }


    /**
     * https://leetcode.com/problems/circular-array-loop/
     * <p>
     * You are given a circular array nums of positive and negative integers. If a number k at an index is positive,
     * then move forward k steps. Conversely, if it's negative (-k), move backward k steps. Since the array is circular,
     * you may assume that the last element's next element is the first element, and the first element's previous element is the last element.
     * <p>
     * Determine if there is a loop (or a cycle) in nums. A cycle must start and end at the same index and the cycle's length > 1.
     * Furthermore, movements in a cycle must all follow a single direction. In other words, a cycle must not consist of both forward and backward movements.
     * <p>
     * Example 1:
     * <p>
     * Input: [2,-1,1,2,2]
     * Output: true
     * Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
     * Example 2:
     * <p>
     * Input: [-1,2]
     * Output: false
     * Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle's length is 1. By definition the cycle's length must be greater than 1.
     * Example 3:
     * <p>
     * Input: [-2,1,-1,-2,-2]
     * Output: false
     * Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from index 1 -> 2 is a forward movement, but movement from index 2 -> 1 is a backward movement. All movements in a cycle must follow a single direction.
     */
    class CircularArrayLoop {

        class DFSSolution {
            boolean visited[];
            int[] nums;
            int len;

            public boolean circularArrayLoop(int[] nums) {
                this.len = nums.length;
                this.visited = new boolean[len];
                this.nums = nums;

                int s = 0;
                int n = 0;
                Set<Integer> parent = new HashSet<>();
                while (true) {
                    s = nextStartIndex();
                    if (s == -1) {
                        return false;
                    }
                    n = nextJumpPosition(s);
                    visited[s] = true;
                    parent.clear();
                    parent.add(s);
                    if (s != n && dfs(s, n, nums[s] > 0, parent)) {
                        return true;
                    }
                }
            }

            private boolean dfs(int startIndex, int index, boolean isPositive, Set<Integer> parent) {
                if (parent.contains(index)) {
                    return true;
                }

                if ((isPositive && nums[index] < 0) || (!isPositive && nums[index] > 0)) {
                    return false;
                }

                if (parent.size() == visited.length) {
                    return false;
                }

                parent.add(index);
                visited[index] = true;
                int nextIndex = nextJumpPosition(index);

                // self loop.
                if (nextIndex == index) {
                    return false;
                }
                return dfs(startIndex, nextIndex, isPositive, parent);
            }

            private int nextStartIndex() {
                for (int i = 0; i < nums.length; i++) {
                    if (!visited[i]) {
                        return i;
                    }
                }
                return -1;
            }

            private int nextJumpPosition(int index) {
                int nextIndex = (index + nums[index]) % len; // check this for -ve numbers
                if (nextIndex < 0) {
                    return len + nextIndex;
                }
                return nextIndex;
            }
        }

        class NoExtraSpaceSolution {

            final int SENTINEL = 10000;
            int[] nums;
            int len;

            public boolean circularArrayLoop(int[] nums) {
                this.len = nums.length;
                this.nums = nums;

                for (int i = 0; i < nums.length; i++) {
                    if (nums[i] > SENTINEL) {
                        continue;
                    }

                    if (search(i, nums[i], SENTINEL + i + 1)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean search(int index, int direction, int cycleCode) {
                if (nums[index] == cycleCode) {
                    return true;
                }

                if (nums[index] > SENTINEL || direction * nums[index] < 0) {
                    return false;
                }

                int nextIndex = nextJumpPosition(index);
                nums[index] = cycleCode;

                // self loop.
                if (nextIndex == index) {
                    return false;
                }
                return search(nextIndex, direction, cycleCode);
            }

            private int nextJumpPosition(int index) {
                int nextIndex = (index + nums[index]) % len;
                if (nextIndex < 0) {
                    return len + nextIndex;
                }
                return nextIndex;
            }
        }

        class FastAndSlowPointerSolution {
            public boolean circularArrayLoop(int[] nums) {
                if (nums == null || nums.length < 2) {
                    return false;
                }

                int slow, fast, N = nums.length;
                for (int i = 0; i < N; i++) {
                    slow = fast = i;
                    do {
                        slow = moveIndex(slow, nums[slow], N);
                        fast = moveIndex(fast, nums[fast], N);
                        fast = moveIndex(fast, nums[fast], N);
                    } while (slow != fast);

                    if (nums[slow] % N != 0 && uniDirectionLoop(slow, nums, N)) {
                        return true;
                    }
                }

                return false;
            }

            private boolean uniDirectionLoop(int slow, int[] nums, int N) {
                int current = slow;
                do {
                    current = moveIndex(current, nums[current], N);
                    if (nums[current] > 0 ^ nums[slow] > 0) {
                        return false;
                    }
                } while (current != slow);

                return true;
            }

            private int moveIndex(int index, int value, int N) {
                index += value;
                index %= N;

                if (index < 0) {
                    index += N;
                }
                return index;
            }
        }
    }


    /**
     * https://leetcode.com/problems/ipo/
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital,
     * LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources,
     * it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize
     * its total capital after finishing at most k distinct projects.
     *
     * You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is needed
     * to start the corresponding project. Initially, you have W capital. When you finish a project, you will obtain its
     * pure profit and the profit will be added to your total capital.
     *
     * To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital,
     * and output your final maximized capital.
     *
     * Example 1:
     * Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
     *
     * Output: 4
     *
     * Explanation: Since your initial capital is 0, you can only start the project indexed 0.
     *              After finishing it you will obtain profit 1 and your capital becomes 1.
     *              With capital 1, you can either start the project indexed 1 or the project indexed 2.
     *              Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
     *              Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
     * Note:
     * You may assume all numbers in the input are non-negative integers.
     * The length of Profits array and Capital array will not exceed 50,000.
     * The answer is guaranteed to fit in a 32-bit signed integer.
     */
    static class IPO {

        static class Project {
            int capital;
            int profit;

            public Project(int c, int p) {
                this.capital = c;
                this.profit = p;
            }
        }

        static class PriorityQueueSolution {

            public int findMaximizedCapital(int k, int w, int[] profits, int[] capitals) {
                List<Project> projects = createProjectList(profits, capitals);
                projects = projects.stream().sorted(Comparator.comparingInt(p -> p.capital)).collect(Collectors.toList());

                PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a,b) -> b - a);
                int totalCapital = w;
                int index = addToQueue(projects, priorityQueue, 0, totalCapital);

                while (k > 0 && !priorityQueue.isEmpty()) {
                    totalCapital += priorityQueue.poll();
                    index = addToQueue(projects, priorityQueue, index, totalCapital);
                    k--;
                }
                return totalCapital;
            }

            private List<Project> createProjectList(int[] profits, int[] capitals) {
                List<Project> projectList = new ArrayList<>(profits.length);
                for (int i = 0; i < profits.length; i++) {
                    projectList.add(new Project(capitals[i], profits[i]));
                }
                return projectList;
            }

            private int addToQueue(List<Project> projects, PriorityQueue<Integer> priorityQueue, int index, int totalCapital) {
                while (index < projects.size() && projects.get(index).capital <= totalCapital) {
                    priorityQueue.add(projects.get(index).profit);
                    index++;
                }
                return index;
            }
        }

        static class BinarySearchSolution {
            public int findMaximizedCapital(int k, int w, int[] profits, int[] capitals) {

                List<Project> projects = createProjectList(profits, capitals);
                projects = projects.stream().sorted(Comparator.comparingInt(p -> p.capital)).collect(Collectors.toList());

                int totalCapital = w;
                while (k > 0) {
                    int p = findMaxProfitProject(projects, totalCapital);

                    if (p == -1) {
                        break;
                    }

                    // Update totalCapital and startCapital.
                    totalCapital += projects.get(p).profit;

                    // Move these elements to the end of array so they are not considered again.
                    projects.remove(p);

                    // We have selected a project.
                    k--;
                }
                return totalCapital;
            }

            private List<Project> createProjectList(int[] profits, int[] capitals) {
                List<Project> projectList = new ArrayList<>(profits.length);
                for (int i = 0; i < profits.length; i++) {
                    projectList.add(new Project(capitals[i], profits[i]));
                }
                return projectList;
            }

            private int findMaxProfitProject(List<Project> projects, int capital) {
                int index = binarySearch(projects, capital);
                if (index < 0) {
                    return -1;
                }
                int maxIndex = 0;
                for (int i = 0; i <= index; i++) {
                    if (projects.get(i).profit > projects.get(maxIndex).profit) {
                        maxIndex = i;
                    }
                }
                return maxIndex;
            }

            private int binarySearch(List<Project> projects, int capital) {

                if (projects.isEmpty() || projects.get(0).capital > capital) {
                    return -1;
                }

                int lo = 0, hi = projects.size() - 1, mid = 0;
                while (lo <= hi) {
                    mid = lo + (hi - lo) / 2;

                    if (projects.get(mid).capital <= capital) {
                        if (mid + 1 <= hi && projects.get(mid + 1).capital <= capital) {
                            lo = mid + 1;
                        } else {
                            return mid;
                        }
                    } else {
                        hi = mid - 1;
                    }
                }
                return -1;
            }
        }
    }


    /**
     * https://leetcode.com/problems/coin-change-2/
     * You are given coins of different denominations and a total amount of money.
     * Write a function to compute the number of combinations that make up that amount.
     * You may assume that you have infinite number of each kind of coin.
     *
     * Example 1:
     *
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     * Example 2:
     *
     * Input: amount = 3, coins = [2]
     * Output: 0
     * Explanation: the amount of 3 cannot be made up just with coins of 2.
     */
    static class CoinChange2 {

        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    dp[i] += dp[i-coin];
                }
            }
            return dp[amount];
        }
    }


    /**
     * https://leetcode.com/problems/coin-change/
     *You are given coins of different denominations and a total amount of money amount.
     * Write a function to compute the fewest number of coins that you need to make up that amount.
     * If that amount of money cannot be made up by any combination of the coins, return -1.
     *
     * Example 1:
     *
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     * Example 2:
     *
     * Input: coins = [2], amount = 3
     * Output: -1
     */
    static class CoinChange {
        public int coinChange(int[] coins, int amount) {
            return tabulated(coins, amount);
        }

        private int tabulated2D(int[] coins, int amount) {
            int[][] dp = new int[coins.length][amount+1];

            for (int i=0; i<dp.length; i++) {
                for (int j=0; j < dp[0].length; j++) {
                    if (j==0) {
                        dp[i][j] = 0;
                    } else if(i==0 && j % coins[0] == 0) {
                        dp[i][j] = j/coins[0];
                    } else {
                        dp[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            for (int i=1; i < dp.length; i++) {
                for (int j=1; j < dp[0].length; j++) {
                    dp[i][j] = Math.min(dp[i-1][j], Integer.MAX_VALUE);
                    if (j >= coins[i] && dp[i][j-coins[i]] != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j-coins[i]]+ 1);
                    }
                }
            }
            return dp[coins.length-1][amount] == Integer.MAX_VALUE ? - 1: dp[coins.length-1][amount];
        }

        private int tabulated(int[] coins, int amount) {
            int[] cache = new int[amount+1];
            cache[0] = 0;
            for (int i=1; i<=amount; i++) {
                cache[i] = Integer.MAX_VALUE;
                for (int coin: coins) {
                    if (i >= coin && cache[i-coin] != Integer.MAX_VALUE) {
                        cache[i] = Math.min(cache[i], cache[i-coin] + 1);
                    }
                }
            }
            return cache[amount] == Integer.MAX_VALUE ? -1 : cache[amount];
        }
    }


    /**
     * https://leetcode.com/problems/array-nesting/
     * A zero-indexed array A of length N contains all integers from 0 to N-1.
     * Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... }
     * subjected to the rule below.
     *
     * Suppose the first element in S starts with the selection of element A[i] of index = i,
     * the next element in S should be A[A[i]], and then A[A[A[i]]]… By that analogy, we stop adding right before
     * a duplicate element occurs in S.
     *
     * Example 1:
     *
     * Input: A = [5,4,0,3,1,6,2]
     * Output: 4
     * Explanation:
     * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
     *
     * One of the longest S[K]:
     * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
     *
     *
     * Note:
     *
     * N is an integer within the range [1, 20,000].
     * The elements of A are all distinct.
     * Each element of A is an integer within the range [0, N-1].
     */
    static class ArrayNesting {
        public int arrayNesting(int[] nums) {
            int count;
            int maxCount = 0;
            for (int i=0; i<nums.length; i++) {
                // count =1, when the element is at right position. a[i] = i.
                count = 1;
                // Swap until you find a cycle.
                while (nums[i] != i) {
                    swap(nums, i, nums[i]);
                    count++;
                }
                maxCount = Math.max(maxCount, count);
            }
            return maxCount;
        }

        // We can make it faster by using bit operators for swapping and avoiding function call.
        private void swap(int[] arr, int x, int y) {
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
        }
    }


    /**
     *
     * Given an unsorted integer array, find the smallest missing positive integer.
     *
     * Example 1:
     *
     * Input: [1,2,0]
     * Output: 3
     * Example 2:
     *
     * Input: [3,4,-1,1]
     * Output: 2
     * Example 3:
     *
     * Input: [7,8,9,11,12]
     * Output: 1
     * Note:
     *
     * Your algorithm should run in O(n) time and uses constant extra space.
     */
    static class FirstMissingPositive {
        public int firstMissingPositive(int[] nums) {
            int sentinel = Integer.MIN_VALUE;
            int maxN = nums.length;
            int correctPos = 0;

            int i=0;
            while (i < nums.length) {
                if (nums[i]-1 == i) {
                    nums[i] = sentinel;
                    i++;
                } else if (nums[i] > maxN || nums[i] <= 0) {
                    // Not in consideration range
                    i++;
                } else {
                    correctPos = nums[i] -1;

                    if (nums[correctPos] != sentinel) {
                        nums[i] = nums[correctPos];
                        nums[correctPos] = sentinel;
                    } else {
                        i++;
                    }

                }
            }

            for (int j=0; j<maxN; j++) {
                if (nums[j] != sentinel) {
                    return j+1;
                }
            }
            return maxN+1;
        }
    }


    /**
     * https://leetcode.com/problems/largest-rectangle-in-histogram/submissions/
     * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
     *
     * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
     *
     * The largest rectangle is shown in the shaded area, which has area = 10 unit.
     *
     *  Example:
     * Input: [2,1,5,6,2,3]
     * Output: 10
     */
    static class LargestRectangleInHistogram {

        class Pair<K,V> {
            K index;
            V height;
            Pair(K index, V value) { this.index = index; this.height = value; }
        }

        public int largestRectangleArea(int[] heights) {
            int maxArea = 0, width, height;
            Stack<Pair<Integer, Integer>> stack = new Stack<>();

            for (int i=0; i<heights.length; i++) {
                while (!stack.isEmpty() && stack.peek().height >= heights[i]) {
                    Pair<Integer, Integer> top = stack.pop();
                    width = stack.isEmpty() ? i : i - stack.peek().index - 1;
                    height = top.height;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(new Pair<>(i, heights[i]));
            }

            while (!stack.isEmpty()) {
                Pair<Integer, Integer> top = stack.pop();
                height = top.height;
                width = stack.isEmpty() ?  heights.length : heights.length - stack.peek().index - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            return maxArea;
        }
    }


    /**
     * https://leetcode.com/problems/longest-valid-parentheses/
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
     *
     * Example 1:
     * Input: "(()"
     * Output: 2
     * Explanation: The longest valid parentheses substring is "()"
     *
     * Example 2:
     * Input: ")()())"
     * Output: 4
     *
     * Explanation: The longest valid parentheses substring is "()()"
     */
    static class LongestValidParenthesis {
        public int longestValidParentheses(String s) {
            int maxans = 0;
            Stack<Integer> stack = new Stack<>();
            stack.push(-1);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    stack.push(i);
                } else {
                    stack.pop();
                    if (stack.empty()) {
                        stack.push(i);
                    } else {
                        maxans = Math.max(maxans, i - stack.peek());
                    }
                }
            }
            return maxans;
        }
    }


    /**
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * Example:
     *
     * Input:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * Output: 1->1->2->3->4->4->5->6
     */
    static class MergeKSortedList {
        class ListNode {
            Integer val;
            ListNode next;
            ListNode(int x) { val = x; }
        }

        Comparator<ListNode> minComparator = (a,b) -> Integer.compare(a.val, b.val);

        public ListNode mergeKLists(ListNode[] lists) {
            PriorityQueue<ListNode> minHeap = createPriorityQueue(lists);
            ListNode head = null;
            ListNode tail = null;

            while (!minHeap.isEmpty()) {
                ListNode top = minHeap.poll();

                if (tail == null) {
                    head = top;
                } else {
                    tail.next = top;
                }


                if (top.next != null) {
                    minHeap.add(top.next);
                }
                tail = top;
                tail.next = null;
            }
            return head;
        }

        private PriorityQueue<ListNode> createPriorityQueue(ListNode[] lists) {
            if (lists.length == 0) {
                return new PriorityQueue<>();
            }
            List<ListNode> firstElement = new ArrayList<>(lists.length);
            for(ListNode node: lists) {
                if (node != null) {
                    firstElement.add(node);
                }
            }
            PriorityQueue<ListNode> minHeap = new PriorityQueue<>(lists.length, minComparator);
            minHeap.addAll(firstElement);
            return minHeap;
        }
    }


    /**
     * https://leetcode.com/problems/generate-parentheses/
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     *
     * For example, given n = 3, a solution set is:
     *
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     */
    static class GenerateParenthesis {
        public List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            if (n == 0) {
                return result;
            }
            updateParen(result, "", n, n);
            return result;
        }

        public void updateParen(List<String> result, String pattern, int open, int close) {
            if (open < 0 || close <0) {
                return;
            }

            if (open > close) {
                return;
            }

            if (open == 0 && close == 0) {
                result.add(pattern);
                return;
            }

            updateParen(result, pattern + "(", open-1, close);
            updateParen(result, pattern + ")", open, close-1);
        }
    }


    /**
     * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
     * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
     *
     * Example 1:
     *
     * Input:
     *   s = "barfoothefoobarman",
     *   words = ["foo","bar"]
     * Output: [0,9]
     * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
     * The output order does not matter, returning [9,0] is fine too.
     * Example 2:
     *
     * Input:
     *   s = "wordgoodgoodgoodbestword",
     *   words = ["word","good","best","word"]
     * Output: []
     */
    static class SubstringWithConcatenationOfAllWords {
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> result = new ArrayList<>();
            if (words.length == 0) {
                return result;
            }

            Map<String, Integer> wordMap = createWordMap(words);
            int wordLength = words[0].length();

            for (int i=0; i<= s.length() - wordLength; i++) {
                Map<String, Integer> occurrenceMap = new HashMap<>(wordMap);
                if (containsAllWords(occurrenceMap, s, i, wordLength)) {
                    result.add(i);
                }
            }
            return result;
        }

        private boolean containsAllWords(Map<String, Integer> occurrenceMap, String s, int startIndex, int wordLength) {
            while (startIndex + wordLength <= s.length() ) {
                String word = s.substring(startIndex, startIndex + wordLength);
                if (occurrenceMap.containsKey(word)) {
                    int freq = occurrenceMap.get(word);
                    if (freq > 1) {
                        occurrenceMap.put(word, freq-1);
                    } else {
                        occurrenceMap.remove(word);
                    }
                } else {
                    return false;
                }

                if (occurrenceMap.isEmpty()) {
                    return true;
                }

                startIndex += wordLength;
            }
            return false;
        }

        private Map<String, Integer> createWordMap(String[] words) {
            Map<String, Integer> wordMap = new HashMap<>();
            for (String word: words) {
                int freq = 1;
                if (wordMap.containsKey(word)) {
                    freq = wordMap.get(word) + 1;
                }
                wordMap.put(word, freq);
            }
            return wordMap;
        }
    }


    /**
     * https://leetcode.com/problems/reverse-nodes-in-k-group/
     * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
     *
     * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
     *
     * Example:
     *
     * Given this linked list: 1->2->3->4->5
     *
     * For k = 2, you should return: 2->1->4->3->5
     *
     * For k = 3, you should return: 3->2->1->4->5
     *
     * Note:
     *
     * Only constant extra memory is allowed.
     * You may not alter the values in the list's nodes, only nodes itself may be changed.
     */
    static class ReverseKGroups {

        public static class ListNode {
            int val;
            ListNode next;
            ListNode(int x) { val = x; }
        }

        ListNode newHead = null;
        ListNode prevTail = null;
        ListNode currStart = null;
        ListNode currTail = null;
        ListNode start;
        ListNode newStart;

        public ListNode reverseKGroup(ListNode head, int k) {

//            if (head == null) {
//                return head;
//            }

            start = head;
            while (start != null) {
                if (!hasKNodes(start, k)) {
                    if (prevTail != null) {
                        prevTail.next = start;
                    }
                    break;
                }

                currTail = reverse(start, k-1);

                if (prevTail != null) {
                    prevTail.next = currStart;
                }

                prevTail = currTail;
                start = newStart;
                currTail.next = null;
            }

            return newHead == null ? head : newHead;
        }

        private boolean hasKNodes(ListNode node, int k) {
            int count = 0;
            for (; node != null && count < k; node = node.next) {
                count++;
            }
            return count == k;
        }

        private ListNode reverse(ListNode root, int k) {
            if (k == 0 || root.next == null) {
                if (newHead == null) {
                    newHead = root;
                }
                currStart = root;
                newStart = root.next;
                return root;
            }

            ListNode previous = reverse(root.next, k-1);
            previous.next = root;
            return root;
        }
    }


    /**
     * https://leetcode.com/problems/maximal-rectangle/
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
     *
     * Example:
     *
     * Input:
     * [
     *   ["1","0","1","0","0"],
     *   ["1","0","1","1","1"],
     *   ["1","1","1","1","1"],
     *   ["1","0","0","1","0"]
     * ]
     * Output: 6
     */
    static class MaximalRectangle {

        public int maximalRectangle(char[][] matrix) {
            if (matrix.length == 0) {
                return 0;
            }

            int[] dp = new int[matrix[0].length];
            int maxArea = 0;

            for (int i=0; i < matrix.length; i++) {
                for (int j=0; j<matrix[0].length; j++) {
                    if (matrix[i][j] == '0') {
                        dp[j] = 0;
                    } else {
                        dp[j] += 1;
                    }
                }
                maxArea = Math.max(maxArea, largestRectangleArea(dp));
            }
            return maxArea;
        }

        class Pair<K,V> {
            K index;
            V height;
            Pair(K index, V value) { this.index = index; this.height = value; }
        }

        public int largestRectangleArea(int[] heights) {
            int maxArea = 0, width, height;
            Stack<Pair<Integer, Integer>> stack = new Stack<>();

            for (int i=0; i<heights.length; i++) {
                while (!stack.isEmpty() && stack.peek().height >= heights[i]) {
                    Pair<Integer, Integer> top = stack.pop();
                    width = stack.isEmpty() ? i : i - stack.peek().index - 1;
                    height = top.height;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(new Pair<>(i, heights[i]));
            }

            while (!stack.isEmpty()) {
                Pair<Integer, Integer> top = stack.pop();
                height = top.height;
                width = stack.isEmpty() ?  heights.length : heights.length - stack.peek().index - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            return maxArea;
        }
    }


    /**
     * https://leetcode.com/problems/recover-binary-search-tree/
     * Two elements of a binary search tree (BST) are swapped by mistake.
     *
     * Recover the tree without changing its structure.
     *
     * Example 1:
     *
     * Input: [1,3,null,null,2]
     *
     *    1
     *   /
     *  3
     *   \
     *    2
     *
     * Output: [3,1,null,null,2]
     *
     *    3
     *   /
     *  1
     *   \
     *    2
     * Example 2:
     *
     * Input: [3,1,4,null,null,2]
     *
     *   3
     *  / \
     * 1   4
     *    /
     *   2
     *
     * Output: [2,1,4,null,null,3]
     *
     *   2
     *  / \
     * 1   4
     *    /
     *   3
     * Follow up:
     *
     * A solution using O(n) space is pretty straight forward.
     * Could you devise a constant space solution?
     */
    static class RecoverBST {

        public static class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        public void recoverTree(TreeNode root) {
//            recoverTreeNoSpace(root);
            recoverTreeNoSpace(root);
        }

        public void recoverTreeSpace(TreeNode root) {
            List<TreeNode> sortedList = new ArrayList<>();
            inorder(root, sortedList);
            TreeNode first = null, second = null;

            for (int i=0; i<sortedList.size(); i++) {
                int previous = i == 0 ? Integer.MIN_VALUE : sortedList.get(i-1).val;
                int next = i == sortedList.size() -1 ? Integer.MAX_VALUE : sortedList.get(i+1).val;
                int current = sortedList.get(i).val;

                if (current < previous || current > next) {
                    if (first == null) {
                        first = sortedList.get(i);
                    } else {
                        second = sortedList.get(i);
                    }
                }

            }
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }

        private void inorder(TreeNode root, List<TreeNode> sortedList) {
            if (root == null) {
                return;
            }
            inorder(root.left, sortedList);
            sortedList.add(root);
            inorder(root.right, sortedList);
        }

        public void recoverTreeNoSpace(TreeNode root) {
            inorderTraversal(root);
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }


        TreeNode previous = null, first = null, second = null;
        private void inorderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }

            inorderTraversal(root.left);

            if (previous != null && first == null && previous.val > root.val) {
                first = previous;
            }
            if (previous != null && first != null && previous.val > root.val) {
                second = root;
            }
            previous = root;

            inorderTraversal(root.right);
        }
    }


    /**
     * https://leetcode.com/problems/insert-interval/
     * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
     *
     * You may assume that the intervals were initially sorted according to their start times.
     *
     * Example 1:
     *
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     * Example 2:
     *
     * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     * Output: [[1,2],[3,10],[12,16]]
     * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
     */
    static class InsertInterval {
        final int START = 0;
        final int END = 1;
        // Comparator<int[]> comparator = (a, b) ->  a[START] - b[START] != 0 ? a[START] - b[START] : a[END] - b[END] ;

        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> result = new ArrayList<>(intervals.length + 1);

            int i=0;
            // Add all smaller and non-overlapping intervals.
            while (i < intervals.length && !doOverlap(intervals[i], newInterval) && intervals[i][START] < newInterval[START]) {
                result.add(intervals[i++]);
            }

            // Merge all overlapping intervals and then add the merged interval.
            while (i < intervals.length && doOverlap(intervals[i], newInterval)) {
                newInterval = merge(intervals[i++], newInterval);
            }
            result.add(newInterval);

            // Add intervals with start greater than end of new interval if any.
            while (i < intervals.length) {
                result.add(intervals[i++]);
            }

            return convertToMatrix(result);
        }

        private boolean doOverlap(int[] a, int[] b) {
            if (b[END] < a[START] || b[START] > a[END]) {
                return false;
            }
            return true;
        }

        private int[] merge(int[] a, int[] b) {
            int[] result = new int[2];
            result[START] = Math.min(a[START], b[START]);
            result[END] = Math.max(a[END], b[END]);
            return result;
        }

        private int[][] convertToMatrix(List<int[]> result) {
            int[][] matrix = new int[result.size()][2];
            int i=0;
            for (int[] r: result) {
                matrix[i++] = r;
            }
            return matrix;
        }
    }


    /**
     * https://leetcode.com/problems/interleaving-string/
     * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
     *
     * Example 1:
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
     * Output: true
     * Example 2:
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
     * Output: false
     */
    static class InterleavingStrings {
        private String a;
        private String b;
        private String c;
        private int[][] cache;

        public boolean isInterleave(String s1, String s2, String s3) {
            a = s1; b = s2; c = s3;
            if (c.length() != a.length() + b.length()) {
                return false;
            }
            cache = new int[a.length()][b.length()];
            return isInterleaveMemoized(0,0,0);
        }

        private boolean isInterleaveRecursive(int i, int j, int k) {
            if (k == c.length()) {
                return true;
            }

            char chC = c.charAt(k);
            if (i < a.length() && chC == a.charAt(i) && isInterleaveRecursive(i+1, j, k+1)) {
                return true;
            }

            if (j < b.length() && chC == b.charAt(j) && isInterleaveRecursive(i, j+1, k+1)) {
                return true;
            }
            return false;
        }

        private boolean isInterleaveMemoized(int i, int j, int k) {

            if (i < a.length() && j < b.length() && cache[i][j] != 0) {
                return cache[i][j] == -1 ? false : true;
            }

            if (k == c.length()) {
                return true;
            }

            char chC = c.charAt(k);
            boolean ans = false;
            if (i < a.length() && chC == a.charAt(i) && isInterleaveRecursive(i+1, j, k+1)
            || (j < b.length() && chC == b.charAt(j) && isInterleaveRecursive(i, j+1, k+1))) {
                ans = true;
            }

            if (i < a.length() && j < b.length()) {
                cache[i][j] = ans ? 1 : -1;
            }
            return ans;
        }

        private boolean isInterleaveTabulated() {
            boolean [][] dp = new boolean[a.length()+1][b.length()+1];

            for (int i=0; i<dp.length; i++) {
                for (int j=0; j<dp[0].length; j++) {
                    if (i==0 && j ==0) {
                        dp[i][j] = true;
                    } else if (i==0) {
                        dp[i][j] = dp[i][j-1] && b.charAt(j-1) == c.charAt(i+j- 1);
                    } else if (j==0) {
                        dp[i][j] = dp[i-1][j] && a.charAt(i-1) == c.charAt(i+j-1);
                    } else {
                        dp[i][j] = dp[i-1][j] && a.charAt(i-1) == c.charAt(i+j-1) || dp[i][j-1] && b.charAt(j-1) == c.charAt(i+j-1);
                    }
                }
            }
            return dp[a.length()][b.length()];
        }
    }


    /**
     * Given a non-empty binary tree, find the maximum path sum.
     *
     * For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.
     *
     * Example 1:
     *
     * Input: [1,2,3]
     *
     *        1
     *       / \
     *      2   3
     *
     * Output: 6
     * Example 2:
     *
     * Input: [-10,9,20,null,null,15,7]
     *
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * Output: 42
     */
    static class MaximumPathSumInBinaryTree {

        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        public int maxPathSum(TreeNode root) {
            AtomicInteger globalMax = new AtomicInteger(Integer.MIN_VALUE);
            _maxPathSum(root, globalMax);
            return globalMax.get();
        }

        private int _maxPathSum(TreeNode root, AtomicInteger globalMax) {

            if (root == null) {
                return 0;
            }

            int left = _maxPathSum(root.left, globalMax);
            int right = _maxPathSum(root.right, globalMax);
            int candidate = left + right + root.val;

            if (globalMax.get() < candidate) {
                globalMax.set(candidate);
            }

            int path = root.val + Math.max(left, right);
            return path > 0 ? path : 0;
        }
    }


    /**
     * Serialization is the process of converting a data structure or object into a sequence of bits
     * so that it can be stored in a file or memory buffer, or transmitted across a network connection
     * link to be reconstructed later in the same or another computer environment.
     *
     * Design an algorithm to serialize and deserialize a binary tree.
     * There is no restriction on how your serialization/deserialization algorithm should work.
     * You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized
     * to the original tree structure.
     *
     * Example:
     *
     * You may serialize the following tree:
     *
     *     1
     *    / \
     *   2   3
     *      / \
     *     4   5
     *
     * as "[1,2,3,null,null,4,5]"
     * Clarification: The above format is the same as how LeetCode serializes a binary tree.
     * You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
     *
     * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
     * should be stateless.
     */
    static class CodecForBinaryTree {

        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            public TreeNode(int v) { this.val = v;}
        }

        private final String NULL_NODE = "null";
        class SerializationContext {
            boolean isEmpty;
            int index;

            public SerializationContext() {
                isEmpty = true;
                index = 0;
            }
        }

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder builder = new StringBuilder();
            serialize(root, new SerializationContext(), builder);
            return builder.toString();
        }

        private void serialize(TreeNode root, SerializationContext context, StringBuilder builder) {
            append(builder, context, root);

            if (root == null) {
                return;
            }
            serialize(root.left, context, builder);
            serialize(root.right, context, builder);
        }

        private void append(StringBuilder builder, SerializationContext context, TreeNode root) {
            if (context.isEmpty) {
                context.isEmpty = false;
            } else {
                builder.append(",");
            }

            if (root == null) {
                builder.append(NULL_NODE);
            } else {
                builder.append(root.val);
            }
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }

            String[] nodes = data.split(",");
            if (nodes.length == 0) {
                return null;
            }
            return _construct(nodes, new SerializationContext());
        }

        private TreeNode _construct(String[] nodes, SerializationContext context) {
            if (context.index >= nodes.length) {
                return null;
            }

            TreeNode newNode = createNode(nodes[context.index++]);
            if (newNode == null) {
                return newNode;
            }
            newNode.left = _construct(nodes, context);
            newNode.right = _construct(nodes, context);
            return newNode;
        }

        private TreeNode createNode(String n) {
            if (NULL_NODE.equals(n)) {
                return null;
            }
            return new TreeNode(Integer.valueOf(n));
        }
    }
}

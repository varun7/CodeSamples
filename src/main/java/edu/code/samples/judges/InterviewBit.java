package edu.code.samples.judges;

import edu.code.samples.ds.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InterviewBit {

    /**
     * https://www.interviewbit.com/problems/n3-repeat-number/
     * You’re given a read only array of n integers. Find out if any integer occurs more than n/3 times in the array in linear time and constant additional space.
     *
     * If so, return the integer. If not, return -1.
     *
     * If there are multiple solutions, return any one.
     *
     * Example :
     *
     * Input : [1 2 3 1 1]
     * Output : 1
     * 1 occurs 3 times which is more than 5/3 times.
     */
    public static class RepeastN3 {
        // DO NOT MODIFY THE LIST
        public int repeatedNumber(final List<Integer> a) {
            int size = a.size();
            if (size == 0) {
                return -1;
            }

            if (size <= 2) {
                return a.get(0);
            }

            int n1 = Integer.MAX_VALUE; int c1 = 0;
            int n2 = Integer.MAX_VALUE; int c2 = 0;
            int n;

            for (int i = 0; i < a.size(); i++) {
                n = a.get(i);
                if (n1 == n) {
                    c1++;
                } else if (n2 == n) {
                    c2++;
                } else if (c1 == 0) {
                    n1 = n;
                    c1 = 1;
                } else if (c2 == 0) {
                    n2 = n;
                    c2 = 1;
                } else {
                    c1--;
                    c2--;
                }
            }

            c1 = 0; c2 =0;
            for (int i=0; i<a.size(); i++) {
                if (n1 == a.get(i)) {
                    c1++;
                }
                if (n2 == a.get(i)) {
                    c2++;
                }
            }

            if (c1 > size/3) {
                return n1;
            }

            if (c2 > size/3) {
                return n2;
            }
            return -1;
        }
    }

    /**
     * Given a collection of intervals, merge all overlapping intervals.
     *
     * For example:
     *
     * Given [1,3],[2,6],[8,10],[15,18],
     *
     * return [1,6],[8,10],[15,18].
     *
     * }
     */
    public static class MergeOverlappingIntervals {

        public class Interval {
            int start;
            int end;
            Interval() { start = 0;end = 0; }
            Interval(int s, int e) { start = s;end = e; }
        }

        public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
            ArrayList<Interval> result = new ArrayList<>();
            if (intervals.isEmpty()) {
                return result;
            }

            Comparator<Interval> comparator = new Comparator<Interval>() {
                @Override
                public int compare(Interval o1, Interval o2) {
                    return o1.start - o2.start;
                }
            };
            intervals.sort(comparator);
            Interval top = intervals.get(0), current = null;
            for (int i = 1; i < intervals.size(); i++) {
                current = intervals.get(i);
                if (doOverlap(top, current)) {
                    top = merge(top, current);
                } else {
                    result.add(top);
                    result.add(current);
                    top = current;
                }
            }
            if (result.isEmpty() || result.get(result.size() - 1) != top) {
                result.add(top);
            }
            return result;
        }

        private Interval merge(Interval a, Interval b) {
            Interval interval = new Interval();
            interval.start = Math.min(a.start, b.start);
            interval.end = Math.max(a.end, b.end);
            return interval;
        }

        private boolean doOverlap(Interval a, Interval b) {
            if (a.start > b.end || a.end < b.start) {
                return false;
            }
            return true;
        }
}

    /**
     * https://www.interviewbit.com/problems/max-non-negative-subarray/
     * Find out the maximum sub-array of non negative numbers from an array.
     * The sub-array should be continuous. That is, a sub-array created by choosing the second and fourth element and skipping the third element is invalid.
     *
     * Maximum sub-array is defined in terms of the sum of the elements in the sub-array. Sub-array A is greater than sub-array B if sum(A) > sum(B).
     *
     * Example:
     *
     * A : [1, 2, 5, -7, 2, 3]
     * The two sub-arrays are [1, 2, 5] [2, 3].
     * The answer is [1, 2, 5] as its sum is larger than [2, 3]
     * NOTE: If there is a tie, then compare with segment's length and return segment which has maximum length
     * NOTE 2: If there is still a tie, then return the segment with minimum starting index
     */
    public static class MaxNonNegativeSubArray {
        public ArrayList<Integer> maxset(ArrayList<Integer> A) {

            ArrayList<Integer> result = new ArrayList<>();
            if (A.isEmpty()) {
                return result;
            }

            long sum = 0;
            long maxSum = Integer.MIN_VALUE;
            int start = 0;
            int gStart = -1, gEnd = 0;
            int c;

            for (int i=0; i<A.size(); i++) {
                c = A.get(i);

                if (c < 0) {
                    start = i+1;
                    sum = 0;
                    continue;
                }

                sum += c;

                if (sum > maxSum || (maxSum == sum && (gEnd - gStart < i - start))) {
                    maxSum = sum;
                    gStart = start;
                    gEnd = i;
                }
            }

            for (int i=gStart; i >=0 && i <= gEnd; i++) {
                result.add(A.get(i));
            }
            return result;
        }
    }

    /**
     * https://www.interviewbit.com/problems/max-distance/
     * Given an array A of integers, find the maximum of j - i subjected to the constraint of A[i] <= A[j].
     *
     * If there is no solution possible, return -1.
     *
     * Example :
     *
     * A : [3 5 4 2]
     *
     * Output : 2
     * for the pair (3, 4)
     */
    public static class MaxDistance {
        // DO NOT MODIFY THE LIST. IT IS READ ONLY
        public int maximumGap(final List<Integer> A) {
            if (A.isEmpty()) {
                return -1;
            }

            if (A.size() == 1) {
                return 0;
            }
            int [] input = new int[A.size()];
            int [] index = new int[A.size()];

            for (int i=0; i < A.size(); i++) {
                input[i] = A.get(i);
                index[i] = i;
            }
            mergeSort(index, input, 0, A.size()-1);
            return maxIndexGap(index);
        }

        private void mergeSort(int[] index, int[] input, int lo, int hi) {
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi-lo)/2;
            mergeSort(index, input, lo, mid);
            mergeSort(index, input, mid+1, hi);
            merge(index, input, lo, mid, hi);
        }

        private void merge(int[] index, int[] input, int lo, int mid, int hi) {

            List<Integer> indexCopy = new ArrayList<>(hi-lo+1);
            List<Integer> inputCopy = new ArrayList<>(hi-lo+1);

            int i= lo, j = mid+1;

            while (i <= mid && j <= hi) {
                // Less than equal is important, having only less than will not lead desired result.
                if (input[i] <= input[j]) {
                    indexCopy.add(index[i]);
                    inputCopy.add(input[i]);
                    i++;
                } else {
                    indexCopy.add(index[j]);
                    inputCopy.add(input[j]);
                    j++;
                }
            }

            while (i <= mid) {
                indexCopy.add(index[i]);
                inputCopy.add(input[i]);
                i++;
            }

            while (j <= mid) {
                indexCopy.add(index[j]);
                inputCopy.add(input[j]);
                j++;
            }

            for (int k=0; k < inputCopy.size(); k++) {
                index[lo + k] = indexCopy.get(k);
                input[lo + k] = inputCopy.get(k);
            }
        }

        int maxIndexGap(int[] index) {
            int maxIndex = Integer.MIN_VALUE;
            int maxGap = -1;
            for (int i=index.length-1; i>=0; i--) {
                maxIndex = Math.max(maxIndex, index[i]);
                maxGap = Math.max(maxGap, maxIndex - index[i]);
            }
            return maxGap;
        }
    }

    /**
     * https://www.interviewbit.com/problems/justified-text/
     * Given an array of words and a length L, format the text such that each line has exactly L characters and is fully (left and right) justified.
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
     *
     * Pad extra spaces ‘ ‘ when necessary so that each line has exactly L characters.
     * Extra spaces between words should be distributed as evenly as possible.
     * If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     *
     * Your program should return a list of strings, where each string represents a single line.
     *
     * Example:
     *
     * words: ["This", "is", "an", "example", "of", "text", "justification."]
     *
     * L: 16.
     *
     * Return the formatted lines as:
     *
     * [
     *    "This    is    an",
     *    "example  of text",
     *    "justification.  "
     * ]
     */
    public static class JustifyText {
        public ArrayList<String> fullJustify(List<String> words, int lineLength) {

            ArrayList<String> result = new ArrayList<>();
            StringBuilder line = new StringBuilder();

            int i = 0;
            String word = null;
            while (i < words.size()) {
                word = words.get(i);
                if (canAddWordToLine(word, line.length(), lineLength)) {
                    addWordToLine(word, line);
                    i++;
                } else {
                    result.add(justify(line, lineLength));
                    line = new StringBuilder();
                }
            }
            if (line.length() != 0) {
                // Don't justify last line.
                addSpaces(line, lineLength - line.length());
                result.add(line.toString());
            }
            return result;
        }

        private boolean canAddWordToLine(String word, int currentLineLength, int lineLength) {
            if (currentLineLength == 0) {
                // All words are less than L.
                return true;
            }
            if (currentLineLength + word.length() + 1 <= lineLength) { // 1 for space
                return true;
            }
            return false;
        }

        private String justify(StringBuilder line, int lineLength) {

            String notJustifiedLine = line.toString();

            // If current line length equals the ideal line length.
            int currentLineLength = line.toString().length();
            if (currentLineLength == lineLength) {
                return line.toString();
            }

            String[] words = line.toString().split(" ");
            StringBuilder resultBuilder = new StringBuilder();

            // If only one word in line
            if (words.length == 1) {
                resultBuilder.append(words[0]);
                addSpaces(resultBuilder, lineLength - words[0].length());
                return resultBuilder.toString();
            }

            // All other cases.
            int slots = words.length - 1;
            int totalNewSpaces = lineLength - currentLineLength;

            int spacesInEachSlot = totalNewSpaces/slots;
            int remainder = totalNewSpaces % slots;

            StringBuilder justifiedLine = new StringBuilder(words[0]);
            for (int i=1; i < words.length; i++) {
                justifiedLine.append(" ");
                for (int j = 0; j < spacesInEachSlot; j++) {
                    justifiedLine.append(" ");
                }
                if (remainder != 0) {
                    justifiedLine.append(" ");
                    remainder--;
                }
                justifiedLine.append(words[i]);
            }
            return justifiedLine.toString();
        }

        private void addSpaces(StringBuilder builder, int spaceCount) {
            for (int i=0; i < spaceCount; i++) {
                builder.append(" ");
            }
        }

        private void addWordToLine(String word, StringBuilder line) {
            if (line.length() == 0) {
                line.append(word);
                return;
            }
            line.append(" "); // add space before word
            line.append(word);
        }
    }

    /**
     * https://www.interviewbit.com/problems/sliding-window-maximum/
     * A long array A[] is given to you. There is a sliding window of size w which is moving from the very left of the array to the very right. You can only see the w numbers in the window. Each time the sliding window moves rightwards by one position. You have to find the maximum for each window. The following example will give you more clarity.
     *
     * Example :
     *
     * The array is [1 3 -1 -3 5 3 6 7], and w is 3.
     *
     * Window position	Max
     *
     * [1 3 -1] -3 5 3 6 7	3
     * 1 [3 -1 -3] 5 3 6 7	3
     * 1 3 [-1 -3 5] 3 6 7	5
     * 1 3 -1 [-3 5 3] 6 7	5
     * 1 3 -1 -3 [5 3 6] 7	6
     * 1 3 -1 -3 5 [3 6 7]	7
     * Input: A long array A[], and a window width w
     * Output: An array B[], B[i] is the maximum value of from A[i] to A[i+w-1]
     * Requirement: Find a good optimal way to get B[i]
     */
    public static class MaxInSlidingWindow {

        class Pair {
            public final int index;
            public final int value;

            public Pair(int i, int v) {
                this.index = i;
                this.value = v;
            }
        }

        public ArrayList<Integer> slidingMaximum(final List<Integer> input, int windowSize) {
            ArrayList<Integer> result = new ArrayList<>();

            if (input.isEmpty() || windowSize == 0) {
                return result;
            }

            Deque<Pair> window = new ArrayDeque<>(windowSize);
            for (int i=0; i < input.size(); i++) {
                addToWindow(window, new Pair(i, input.get(i)), windowSize);
                if (i >= windowSize-1) {
                    result.add(getMaxInWindow(window, i, windowSize));
                }
            }
            return result;
        }

        private void addToWindow(Deque<Pair> window, Pair newItem, int windowSize) {
            while (!window.isEmpty() && (window.peekLast().value < newItem.value || newItem.index - window.peekLast().index + 1 > windowSize)) {
                window.removeLast();
            }
            window.addLast(newItem);
        }

        private int getMaxInWindow(Deque<Pair> window, int index, int windowSize) {
            while(!window.isEmpty() && index - window.peekFirst().index + 1 > windowSize) {
                window.removeFirst();
            }
            return window.peekFirst().value;
        }
    }

    /**
     * https://www.interviewbit.com/problems/word-ladder-i/
     * Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
     *
     * You must change exactly one character in every transformation
     * Each intermediate word must exist in the dictionary
     * Example :
     *
     * Given:
     *
     * start = "hit"
     * end = "cog"
     * dict = ["hot","dot","dog","lot","log"]
     * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     * return its length 5.
     *
     * Note that we account for the length of the transformation path instead of the number of transformation itself.
     *
     *  Note:
     * Return 0 if there is no such transformation sequence.
     * All words have the same length.
     * All words contain only lowercase alphabetic characters.
     */
    public static class WordLadderOne {
        public int ladderLength(String start, String end, ArrayList<String> dictionary) {
            dictionary.add(start);
            dictionary.add(end);
            Graph<String> ladder = new Graph.AdjacencyListGraph<>();
            Set<String> nodesInGraph = new HashSet<>();

            for (String word : dictionary) {
                if (!nodesInGraph.contains(word)) {
                    ladder.insert(word);
                    nodesInGraph.add(word);
                    updateGraph(ladder, nodesInGraph, word);
                }
            }
            // We don't need to perform dijkstra bfs is good enough, but we haven't implemente
            // bfs to return distance from source node hence used dijkstra.
            Double distance = ladder.singleSourceShortestPathDijkstra(start).get(end);
            return distance == Double.MAX_VALUE - 1 ? 0 : distance.intValue() + 1;
        }

        private void updateGraph(Graph<String> ladder, Set<String> nodesInGraph, String word) {
            String similarWord;
            for (int i=0; i<word.length(); i++) {
                String prefix = i == 0 ? new StringBuilder(word.charAt(0)).toString() : word.substring(0, i);
                String suffix = i == word.length() - 1 ? "" : word.substring(i+1);
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    similarWord = prefix + ch + suffix;
                    if (nodesInGraph.contains(similarWord) && !similarWord.equals(word)) {
                        ladder.connect(word, similarWord, 1.0);
                    }
                }
            }
        }
    }

    /**
     * https://www.interviewbit.com/problems/remove-duplicates-from-sorted-array/
     * Remove duplicates from Sorted Array
     * Given a sorted array, remove the duplicates in place such that each element appears only once and return the new length.
     *
     * Note that even though we want you to return the new length, make sure to change the original array as well in place
     *
     * Do not allocate extra space for another array, you must do this in place with constant memory.
     *
     *  Example:
     * Given input array A = [1,1,2],
     * Your function should return length = 2, and A is now [1,2].
     */
    public static class RemoveDuplicatesFromSortedList {

        public int removeDuplicates(ArrayList<Integer> a) {

            if (a.isEmpty()) {
                return 0;
            }
            int pos = 1, current, previous;
            for (int i=1; i < a.size(); i++) {
                // a.get(i) will not be equal to a.get(i-1) as both of them
                // will have different hashcode and they are objects not primitive ints.
                if (a.get(i).intValue() != a.get(i-1).intValue()) {
                    a.set(pos, a.get(i));
                    pos++;
                }
            }
            return pos;
        }
    }

    /**
     * https://www.interviewbit.com/problems/regular-expression-ii/
     * Implement regular expression matching with support for '.' and '*'.
     *
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     *
     * The matching should cover the entire input string (not partial).
     *
     * The function prototype should be:
     *
     * int isMatch(const char *s, const char *p)
     * Some examples:
     *
     * isMatch("aa","a") → 0
     * isMatch("aa","aa") → 1
     * isMatch("aaa","aa") → 0
     * isMatch("aa", "a*") → 1
     * isMatch("aa", ".*") → 1
     * isMatch("ab", ".*") → 1
     * isMatch("aab", "c*a*b") → 1
     * Return 0 / 1 ( 0 for false, 1 for true ) for this problem
     */
    public static class RegularExpressionII {
        public int isMatch(final String text, final String pattern) {
            if (recursivePatternMatching(text, pattern)) {
                return 1;
            }
            return 0;
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
            dp [0][0] = true;
            for (int i=1; i < rows; i++) {
                dp[i][0] = false;
            }

            for (int j=1; j < cols; j++) {
                dp[0][j] = pattern.charAt(j-1) == '*' ? dp[0][j-2] : false;
            }

            for (int i = 1; i < rows; i ++) {
                for (int j = 1; j < cols; j++) {
                    char p = pattern.charAt(j-1);
                    char t = text.charAt(i-1);
                    if (p == '*') {
                        char charBeforeStar = pattern.charAt(j-2);

                        // if pattern is xa* and text is xaaa
                        // dp [i-1][j] (i=3, j =3) represents that xa* has matched xaa
                        dp[i][j] = dp[i][j-2]  // zero occurrence
                                || (doMatch(t, charBeforeStar) && dp[i-1][j]); // 1 or more occurrence
                    } else {
                        dp[i][j] = dp[i-1][j-1] && doMatch(t, p);
                    }
                }
            }
            return dp[rows-1][cols-1];
        }

        private boolean doMatch(char t, char p) {
            if (t == p || p == '.' || t == '.') {
                return true;
            }
            return false;
        }
    }

    /**
     * https://www.interviewbit.com/problems/rod-cutting/
     * There is a rod of length N lying on x-axis with its left end at x = 0 and right end at x = N.
     * Now, there are M weak points on this rod denoted by positive integer values(all less than N) A1, A2, …, AM.
     * You have to cut rod at all these weak points. You can perform these cuts in any order. After a cut,
     * rod gets divided into two smaller sub-rods. Cost of making a cut is the length of the sub-rod in which you are making a cut.
     *
     * Your aim is to minimise this cost. Return an array denoting the sequence in which you will make cuts.
     * If two different sequences of cuts give same cost, return the lexicographically smallest.
     *
     * Notes:
     *
     * Sequence a1, a2 ,…, an is lexicographically smaller than b1, b2 ,…, bm, if and only if at the first i where ai and bi differ, ai < bi, or if no such i found, then n < m.
     * N can be upto 109.
     * For example,
     *
     * N = 6
     * A = [1, 2, 5]
     *
     * If we make cuts in order [1, 2, 5], let us see what total cost would be.
     * For first cut, the length of rod is 6.
     * For second cut, the length of sub-rod in which we are making cut is 5(since we already have made a cut at 1).
     * For third cut, the length of sub-rod in which we are making cut is 4(since we already have made a cut at 2).
     * So, total cost is 6 + 5 + 4.
     *
     * Cut order          | Sum of cost
     * (lexicographically | of each cut
     *  sorted)           |
     * ___________________|_______________
     * [1, 2, 5]          | 6 + 5 + 4 = 15
     * [1, 5, 2]          | 6 + 5 + 4 = 15
     * [2, 1, 5]          | 6 + 2 + 4 = 12
     * [2, 5, 1]          | 6 + 4 + 2 = 12
     * [5, 1, 2]          | 6 + 5 + 4 = 15
     * [5, 2, 1]          | 6 + 5 + 2 = 13
     *
     *
     * So, we return [2, 1, 5].
     */
    public static class RodCutting {

        long [][] dp ;
        int [][] footPrint;
        int [] rod;
        ArrayList<Integer> result;

        /**
         * We rewrite our problem as given N cut points(and you cannot make first and last cut), decide order of these cuts to minimise the cost. So, we insert 0 and N at beginning and end of vector B. Now, we have solve our new problem with respect to this new array(say A).
         *
         * We define dp(i, j) as minimum cost for making cuts Ai, Ai+1, …, Aj. Note that you are not making cuts Ai and Aj, but they decide the cost for us.
         *
         * For solving dp(i, j), we iterate k from i+1 to j-1, assuming that the first cut we make in this interval is Ak. The total cost required(if we make first cut at Ak) is Aj - Ai + dp(i, k) + dp(k, j).
         *
         * This is our solution. We can implement this DP recursively with memoisation. Total complexity will be O(N3).
         * For actually building the solution, after calculating dp(i, j), we can store the index k which gave the minimum cost and then we can build the solution backwards.
         */
        public ArrayList<Integer> rodCut(int length, ArrayList<Integer> cuts) {
            result = new ArrayList<>();
            if (cuts.isEmpty()) {
                return result;
            }

            dp = new long[cuts.size() + 2][cuts.size() + 2];
            footPrint = new int[cuts.size() +2][cuts.size()+2];
            rod = new int[cuts.size()+2];

            rod[0] = 0;
            rod[cuts.size()+1] = length;
            for (int i=0; i <cuts.size(); i++) {
                rod[i+1] = cuts.get(i);
            }

            for (int e= 2; e < dp.length; e++) {
                for (int s = e-2; s >=0; s--) {
                    for (int i=s+1; i < e; i++) {
                        long val = dp[s][i] + dp[i][e] + rod[e] - rod[s];
                        if (dp[s][e] ==0 || val < dp[s][e]) {
                            dp[s][e] = val;
                            footPrint[s][e] = i;
                        }
                    }
                }
            }

            backTrack(0, dp.length-1);
            return result;
        }

        private void backTrack(int s, int e) {
            if (s + 1 >= e) {
                return;
            }
            result.add(rod[footPrint[s][e]]);
            backTrack(s, footPrint[s][e]);
            backTrack(footPrint[s][e], e);
        }
    }

    /**
     * https://www.interviewbit.com/problems/wave-array/
     * Given an array of integers, sort the array into a wave like array and return it,
     * In other words, arrange the elements into a sequence such that a1 >= a2 <= a3 >= a4 <= a5.....
     *
     * Example
     *
     * Given [1, 2, 3, 4]
     *
     * One possible answer : [2, 1, 4, 3]
     * Another possible answer : [4, 1, 3, 2]
     *  NOTE : If there are multiple answers possible, r
     */
    public static class WaveArray{
        public ArrayList<Integer> wave(ArrayList<Integer> input) {

            ArrayList<Integer> sortedList = input.stream().sorted().collect(Collectors.toCollection(() -> new ArrayList<>()));


            for (int i=0; i < input.size()-1; i+=2) {
                int curr = sortedList.get(i);
                int next = sortedList.get(i+1);
                sortedList.set(Integer.valueOf(i), next);
                sortedList.set(Integer.valueOf(i+1), curr);
            }
            return sortedList;
        }
    }

    /**
     * https://www.interviewbit.com/problems/painters-partition-problem/
     * https://www.geeksforgeeks.org/painters-partition-problem/
     * https://www.geeksforgeeks.org/painters-partition-problem-set-2/
     *
     * You have to paint N boards of length {A0, A1, A2, A3 … AN-1}.
     * There are K painters available and you are also given how much time a painter takes to paint 1 unit of board.
     * You have to get this job done as soon as possible under the constraints that any painter will only paint
     * contiguous sections of board.
     *
     * 2 painters cannot share a board to paint. That is to say, a board
     * cannot be painted partially by one painter, and partially by another.
     * A painter will only paint contiguous boards. Which means a
     * configuration where painter 1 paints board 1 and 3 but not 2 is
     * invalid.
     * Return the ans % 10000003
     *
     * Input :
     * K : Number of painters
     * T : Time taken by painter to paint 1 unit of board
     * L : A List which will represent length of each board
     *
     * Output:
     *      return minimum time to paint all boards % 10000003
     * Example
     *
     * Input :
     *   K : 2
     *   T : 5
     *   L : [1, 10]
     * Output : 50
     */
    public static class PainterPartition {
        class BinaryIndexedTree {
            private long[] tree;

            public BinaryIndexedTree(ArrayList<Integer> list) {
                constructTree(list);
            }

            public void update(int index, int val) {
                for (int pos = index+1; pos < tree.length; pos += pos & (-pos)) {
                    tree[pos] += val;
                }
            }

            public int query(int index) {
                int val = 0;
                for (int i=index; i>0; i -= (i & -i)) {
                    val += tree[i];
                }
                return val;
            }

            private void constructTree(ArrayList<Integer> list) {
                this.tree = new long[list.size()+1];
                for (int i=0; i<list.size(); i++) {
                    update(i, list.get(i));
                }
            }
        }

        private ArrayList<Integer> fence;
        BinaryIndexedTree tree;

        public int paint(int painters, int time, ArrayList<Integer> board) {
            fence = board;
            int boardSize = board.size();

            long [][] dp = new long[painters][boardSize];
            tree = new BinaryIndexedTree(board);

            // When there is only one painter
            long sum = 0;
            for (int b=0; b<boardSize ; b++) {
                sum += board.get(b);
                dp[0][b] =  sum;
            }

            // When there is only one board.
            for (int p=0; p < dp.length; p++) {
                dp[p][0] = board.get(0);
            }

            for (int p = 1; p<dp.length; p++) {
                for (int b =1; b < dp[0].length; b++) {

                    long max = Integer.MAX_VALUE;
                    for (int i = 0; i<=b; i++) {
                        max = Math.min(max, Math.max(dp[p-1][i], sumOfInterval(i+1,b+1)));
                    }
                    dp[p][b] = max;
                }
            }
            time = time %  10000003;
            long result = dp[painters-1][boardSize-1]  % 10000003;
            result = (result*time) % 10000003;
            return (int) result;
        }

        private long sumOfInterval(int from, int to) {
            return tree.query(to) - tree.query(from);
        }
    }

    /**
     * https://www.interviewbit.com/problems/invert-the-binary-tree/
     * Given a binary tree, invert the binary tree and return it.
     * Look at the example for more details.
     *
     * Example :
     * Given binary tree
     *
     *      1
     *    /   \
     *   2     3
     *  / \   / \
     * 4   5 6   7
     * invert and return
     *
     *      1
     *    /   \
     *   3     2
     *  / \   / \
     * 7   6 5   4
     */
    public static class InvertBinaryTree {

        class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode(int x) {
                  val = x;
                  left=null;
                  right=null;
             }
        }

        public TreeNode invertTree(TreeNode A) {
            invert(A);
            return A;
        }

        private void invert(TreeNode root) {
            if (root == null) {
                return;
            }
            invert(root.left);
            invert(root.right);

            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }
    }

    /**
     * https://www.interviewbit.com/problems/clone-graph/
     * Clone an undirected graph. Each node in the graph contains a
     * label and a list of its neighbors.
     */
    public static class CloneGraph {
        class UndirectedGraphNode {
            int label;
            List<UndirectedGraphNode> neighbors;
            UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<>(); }
        }

        public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
            Map<UndirectedGraphNode, UndirectedGraphNode> nodeMap = new HashMap<>();
            return _cloneGraph(node, nodeMap);
        }

        private UndirectedGraphNode _cloneGraph(UndirectedGraphNode node, Map<UndirectedGraphNode, UndirectedGraphNode> visited) {
            if (visited.containsKey(node)) {
                return visited.get(node);
            }

            UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
            visited.put(node, newNode);
            if (node.neighbors.isEmpty()) {
                return newNode;
            }

            for (UndirectedGraphNode n: node.neighbors) {
                newNode.neighbors.add(_cloneGraph(n, visited));
            }
            return newNode;
        }
    }

    /**
     * https://www.interviewbit.com/problems/distinct-subsequences/
     * Given two sequences S, T, count number of unique ways in sequence S, to form a subsequence that is identical to the sequence T.
     *
     *  Subsequence : A subsequence of a string is a new string which is formed from the original string by deleting
     *  some (can be none ) of the characters without disturbing the relative positions of the remaining characters.
     *  (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
     *
     * Example :
     * S = "rabbbit"
     * T = "rabbit"
     * Return 3. And the formations as follows:
     *
     * S1= "ra_bbit"
     * S2= "rab_bit"
     * S3="rabb_it"
     * "_" marks the removed character.
     */
    public static class DistinctSubsequence {
        public int numDistinct(String A, String B) {
            return ways(A, B);
        }

        private int ways(String str, String pattern) {
            if (pattern.length() == 0) {
                return 1;
            }

            if (str.length() < pattern.length()) {
                return 0;
            }

            int countWays = 0;
            if (str.charAt(0) != pattern.charAt(0)) {
                countWays += ways(str.substring(1), pattern);
            } else {
                countWays += ways(str.substring(1), pattern.substring(1));
                countWays += ways(str.substring(1), pattern);
            }
            return countWays;
        }
    }

    /**
     * https://www.interviewbit.com/problems/intersection-of-sorted-arrays/
     * Find the intersection of two sorted arrays.
     * OR in other words,
     * Given 2 sorted arrays, find all the elements which occur in both the arrays.
     *
     * Example :
     *
     * Input :
     *     A : [1 2 3 3 4 5 6]
     *     B : [3 3 5]
     *
     * Output : [3 3 5]
     *
     * Input :
     *     A : [1 2 3 3 4 5 6]
     *     B : [3 5]
     *
     * Output : [3 5]
     */
    public class IntersectionOfSortedArrays {
        // DO NOT MODIFY THE LIST. IT IS READ ONLY
        public ArrayList<Integer> intersect(final List<Integer> A, final List<Integer> B) {

            ArrayList<Integer> result = new ArrayList<>();

            int i=0, j=0;
            int a, b;
            while (i < A.size() && j < B.size()) {
                a = A.get(i);
                b = B.get(j);
                if (a == b) {
                    result.add(Integer.valueOf(a));
                    i++; j++;
                } else if (a < b) {
                    i++;
                } else {
                    j++;
                }
            }
            return result;
        }
    }

    /**
     * https://www.interviewbit.com/problems/3-sum-zero/
     * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     *
     * Note:
     *
     *  Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
     * The solution set must not contain duplicate triplets. For example, given array S = {-1 0 1 2 -1 -4}, A solution set is:
     * (-1, 0, 1)
     * (-1, -1, 2)
     */
    public class ThreeSumZero {
        public ArrayList<ArrayList<Integer>> threeSum(ArrayList<Integer> input) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();
            Collections.sort(input);

            int a, b, c, i, j,k;
            for (j=0; j < input.size() - 2; j++) {

                if (j > 0 && input.get(j).intValue() == input.get(j-1).intValue()) {
                    continue;
                }

                i = j+1;
                k = input.size() - 1;
                a = input.get(j);

                while (i < k) {
                    b = input.get(i);
                    c = input.get(k);

                    if (a+b+c == 0) {
                        ArrayList<Integer> triplet = new ArrayList<>(Arrays.asList(a,b,c));
                        result.add(triplet);
                        i++; k--;

                        while (i < k  && input.get(k) == c) {
                            k--;
                        }

                        while (i < k && input.get(i) == b) {
                            i++;
                        }

                    } else if (a+b+c > 0) {
                        k--;
                    } else {
                        i++;
                    }
                }
            }
            return result;
        }
    }

}

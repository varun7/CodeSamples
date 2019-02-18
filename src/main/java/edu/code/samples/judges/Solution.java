package edu.code.samples.judges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public double findMedianSortedArrays(int[] a, int[] b) {
        int totalElements = a.length + b.length;
        int medianElement = (totalElements + 1)/2;

        if (a.length > b.length) {
            int[] temp = a; a = b; b = temp;
        }

        int posA = 0, posB = 0, lo = 0, hi = a.length -1;

        while (lo <= hi) {
            posA = lo + (hi-lo)/2;
            posB = medianElement - posA - 2;

            if (canMoveLeft(lo, posA) && isMedianInLeft(a, b, posA, posB)) {
                hi = posA -1;
            } else if (canMoveRight(hi, posA) && isMedianInRight(a, b, posA, posB)) {
                lo = posA + 1;
            } else {
                // if posA is -ve that means no element from A is included.
                if (posA<0) {
                    return medianWhenNoElementFromA(a, b, medianElement);
                } else if (posA >= a.length) {
                    return medianWhenAllElementFromAIncluded(a, b, medianElement);
                } else {
                    boolean isEven = (a.length + b.length) % 2 == 0;
                    if (!isEven) {
                        return Math.max(a[posA], a[posB]);
                    } else {
                        return (a[posA] + b[posB])/2.0;
                    }
                }
            }
        }
        return 0.0;
    }

    private boolean canMoveLeft(int lo, int posA) {
        if (posA > lo) {
            return true;
        }
        return false;
    }

    private boolean canMoveRight(int hi, int posA) {
        if (hi > posA) {
            return true;
        }
        return false;
    }

    private boolean isMedianInLeft(int[] a, int []b, int posA, int posB) {
        if (a[posA] > b[posB+1]) {
            return true;
        }
        return false;
    }

    private boolean isMedianInRight(int[] a, int []b, int posA, int posB) {
        if (a[posA+1] < b[posB]) {
            return true;
        }
        return false;
    }

    private boolean isMedian(int[] a, int []b, int posA, int posB) {
        if (posB+1 != b.length && posA + 1 != a.length && a[posA] <= b[posB+1] && b[posB] <= a[posA]) {
            return true;
        }
        return false;
    }

    private double medianWhenNoElementFromA(int[] a, int []b, int medianElement) {
        boolean isEven = (a.length + b.length) % 2 == 0;
        if (isEven) {
            return (b[medianElement] + b[medianElement-1])/2.0;
        }
        return b[medianElement-1];
    }

    private double medianWhenAllElementFromAIncluded(int[] a, int []b, int medianElement) {
        boolean isEven = (a.length + b.length) % 2 == 0;
        if (a.length == medianElement) {
            if (isEven) {
                return (a[medianElement-1] + b[0]) / 2.0;
            }
            return a[medianElement-1];
        } else {
            medianElement = medianElement - a.length;
            if (isEven) {
                return b[medianElement] + b[medianElement -1];
            } else {
                return b[medianElement-1];
            }
        }
    }
}

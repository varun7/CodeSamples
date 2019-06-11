package edu.code.samples.generic;

public class KMP {

    public static boolean contains(String text, String pattern) {
        int [] kmp = preProcessing(pattern);
        int i=0, j=0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == pattern.length()) {
                    return true;
                }
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = kmp[j];
                }
            }
        }
        return false;
    }

    private static int[] preProcessing(String pattern) {
        int [] kmp = new int[pattern.length()];
        kmp [0] = 0;
        int i = 1, len = 0;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                i++; len++;
                kmp[i] = len;
            } else {
                if (len == 0) {
                    kmp[i++] = 0;
                } else {
                    len = kmp[len-1];
                }
            }
        }
        return kmp;
    }
}

package edu.code.samples.generic;

import java.util.Random;

public final class Utils {

    public static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void printArray(int [] array) {
        for (int i: array) {
            System.out.print(i + " ");
        }
    }

    public static int[] createRandomIntArray(int size) {
        Random random = new Random(System.currentTimeMillis());
        int [] array = new int[size];
        for (int i=0; i < size; i++) {
            array[i] = random.nextInt() % 103;
        }
        return array;
    }

    public static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        }
        if (b <=c) {
            return b;
        }
        return c;
    }
}

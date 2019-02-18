package edu.code.samples.judges;

import org.junit.Test;

import java.util.ArrayList;

public class InterviewBitTest {

    @Test
    public void testRemoveDuplicate() {
        ArrayList<Integer> input = new ArrayList<>();
        input.add(5000);
        input.add(5000);
        input.add(5000);
        InterviewBit.RemoveDuplicatesFromSortedList solution = new InterviewBit.RemoveDuplicatesFromSortedList();
        System.out.println(solution.removeDuplicates(input));
    }

}
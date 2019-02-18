package edu.code.samples.judges;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeetCodeProblemsTest {

    @Test
    public void burstBalloons() {
        LeetCodeProblems.BurstBalloons balloons = new LeetCodeProblems.BurstBalloons();
        int[] input = {3,1,5,8};
        System.out.println("Max coin top down = " + balloons.maxCoins(input));
    }

}
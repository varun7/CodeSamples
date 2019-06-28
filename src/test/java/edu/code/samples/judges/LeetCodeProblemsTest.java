package edu.code.samples.judges;

import edu.code.samples.concurrent.ThreadBasedProducerConsumer;
import org.junit.Test;

public class LeetCodeProblemsTest {

    @Test
    public void burstBalloons() {
        LeetCodeProblems.BurstBalloons balloons = new LeetCodeProblems.BurstBalloons();
        int[] input = {3, 1, 5, 8};
        System.out.println("Max coin top down = " + balloons.maxCoins(input));
    }

    @Test
    public void ipoTest() {
        int k = 10;
        int w = 0;
        int[] profits = {1, 2, 3};
        int[] capitals = {0, 1, 2};
        LeetCodeProblems.IPO.PriorityQueueSolution solution = new LeetCodeProblems.IPO.PriorityQueueSolution();
        solution.findMaximizedCapital(k, w, profits, capitals);
    }

    @Test
    public void testThreadedProducerConsumer() {
        ThreadBasedProducerConsumer.UniDataBroker broker = new ThreadBasedProducerConsumer.UniDataBroker();
        new Thread(new ThreadBasedProducerConsumer.ThreadProducer(broker)).start();
        new Thread(new ThreadBasedProducerConsumer.ThreadConsumer(broker)).start();
    }
}
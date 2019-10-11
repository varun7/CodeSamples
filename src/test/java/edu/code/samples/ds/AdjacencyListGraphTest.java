package edu.code.samples.ds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class AdjacencyListGraphTest {

    private Graph.AdjacencyListGraph<Integer> graph = new Graph.AdjacencyListGraph<>(true);

    @Before
    public void init() {
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.connect(1,2, 1.0);
        graph.connect(1,3, 4.0);
        graph.connect(2,3, 2.0);
        graph.connect(2,4, 5.0);
        graph.connect(3,4, 4.0);
    }

    @Test
    public void testDijkstra() {
        Map<Integer,Double> result =  graph.dijkstra(1);
        Assert.assertEquals(result.get(1), Double.valueOf(0));
        Assert.assertEquals(result.get(2), Double.valueOf(1));
        Assert.assertEquals(result.get(3), Double.valueOf(3));
        Assert.assertEquals(result.get(4), Double.valueOf(6));
    }
}

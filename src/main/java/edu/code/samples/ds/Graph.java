package edu.code.samples.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Graph<V> {

    void insert(V vertex);

    void delete(V vertex);

    Set<V> vertices();

    Set<V> successors(V vertex);

    Set<V> predecessors(V vertex);

    void connect(V v1, V v2);

    void connect(V v1, V v2, double weight);

    boolean isPresent(V vertex);

    double edgeWeight(V v1, V v2);

    boolean bfs(V startingVertex, V element);

    boolean dfs(V startingVertex, V element);

    Map<V, Double> singleSourceShortestPathDijkstra(V source);

    /**
     * Only for directed graph.
     */
    void topologicalSort();

    boolean isCyclic();

    void stronglyConnectedComponents();

    class AdjacencyListGraph<V> implements Graph<V> {

        protected class Edge {
            private final double weight;
            private final V to;

            public Edge(V to, double weight) {
                this.weight = weight;
                this.to = to;
            }


            public Edge(V to) {
                this.weight = 1;
                this.to = to;
            }
        }

        private Map<V, Set<Edge>> vertexMap;
        private boolean isDirected = false;

        public AdjacencyListGraph(boolean isDirected) {
            vertexMap = new HashMap<>();
            this.isDirected = isDirected;
        }

        public AdjacencyListGraph() {
            vertexMap = new HashMap<>();
        }

        @Override
        public void insert(V vertex) {
            if (isPresent(vertex)) {
                throw new IllegalArgumentException("Tried to add duplicate node.");
            }
            vertexMap.put(vertex, new HashSet<>());
        }

        @Override
        public void delete(V vertex) {
            if (!isPresent(vertex)) {
                throw new IllegalArgumentException("Vertex is not present in the graph.");
            }
            throw new UnsupportedOperationException("Not yet implemented.");
        }

        @Override
        public Set<V> vertices() {
            return vertexMap.keySet();
        }

        @Override
        public Set<V> successors(V vertex) {
            if (isPresent(vertex)) {
                return vertexMap.get(vertex).stream().map(e -> e.to).collect(Collectors.toSet());
            }
            throw new IllegalArgumentException("node is not present in graph");
        }

        @Override
        public Set<V> predecessors(V vertex) {
            if (!isPresent(vertex)) {
                throw new IllegalArgumentException("Node is not present in graph.");
            }
            final Set<V> predecessors = new HashSet<>();
            vertexMap.entrySet()
                    .forEach(entry ->entry.getValue()
                            .forEach(edge -> {
                                if (edge.to == vertex) {
                                    predecessors.add(entry.getKey());
                                }
                            })
                    );
            return predecessors;
        }

        @Override
        public void connect(V v1, V v2) {
            if (!isPresent(v1) || !isPresent(v2)) {
                throw new IllegalArgumentException("One or both node not present in graph.");
            }
            vertexMap.get(v1).add(new Edge(v2));

            if (!isDirected) {
                vertexMap.get(v2).add(new Edge(v1));
            }
        }

        @Override
        public void connect(V v1, V v2, double weight) {
            if (!isPresent(v1) || !isPresent(v2)) {
                throw new IllegalArgumentException("One or both node not present in graph.");
            }
            vertexMap.get(v1).add(new Edge(v2, weight));

            if (!isDirected) {
                vertexMap.get(v2).add(new Edge(v1, weight));
            }
        }

        @Override
        public boolean isPresent(V vertex) {
            return vertexMap.containsKey(vertex);
        }

        @Override
        public double edgeWeight(V v1, V v2) {
            Optional<Edge> edge = vertexMap.get(v1).stream().filter(e -> e.to == v2).findFirst();
            if (edge.isPresent()) {
                return edge.get().weight;
            }
            throw new IllegalArgumentException("Passed nodes are not connected.");
        }

        @Override
        public boolean bfs(V startingVertex, V element) {
            Set<V> explored = new HashSet<>();
            while(startingVertex != null) {
                if (_bfs(startingVertex, element, explored)) {
                    return true;
                }
                startingVertex = nextUnexploredNode(explored);
            }
            return false;
        }

        private boolean _bfs(V startingVertex, V element, Set<V> explored) {
            Queue<V> queue = new LinkedBlockingQueue<>();
            queue.add(startingVertex);

            while (!queue.isEmpty()) {
                V front = queue.remove();
                if (!explored.contains(front)) {
                    explored.add(front);
                    if (front == element) {
                        return true;
                    }
                    queue.addAll(this.successors(front));
                }
            }
            return false;
        }

        @Override
        public boolean dfs(V startingVertex, V element) {
            Set<V> explored = new HashSet<>();
            while(startingVertex != null) {
                if (_dfs(startingVertex, element, explored)) {
                    return true;
                }
                startingVertex = nextUnexploredNode(explored);
            }
            return false;
        }

        private boolean _dfs(V startingVertex, V element, Set<V> explored) {
            if (startingVertex == element) {
                return true;
            }
            if (explored.contains(startingVertex)) {
                return false;
            }

            explored.add(startingVertex);
            for (V vertex: this.successors(startingVertex)) {
                if (!explored.contains(vertex) && _dfs(vertex, element, explored)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void topologicalSort() {
            Set<V> explored = new HashSet<>();
            V startingVertex = nextUnexploredNode(explored);
            while(startingVertex != null) {
                _topologicalSort(startingVertex, explored);
                startingVertex = nextUnexploredNode(explored);
            }
        }

        private void _topologicalSort(V startingVertex, Set<V> explored) {
            explored.add(startingVertex);
            System.out.print(startingVertex + " ");
            for (V vertex: this.successors(startingVertex)) {
                if (!explored.contains(vertex)) {
                    _topologicalSort(vertex, explored);
                }
            }
        }

        @Override
        public boolean isCyclic() {
            Set<V> explored = new HashSet<>();
            Set<V> ancestor = new HashSet<>();
            V startingVertex = nextUnexploredNode(explored);
            while (startingVertex != null) {
                if (_isCyclic(startingVertex, explored, ancestor)) {
                    return true;
                }
                startingVertex = nextUnexploredNode(explored);
            }
            return false;
        }

        private boolean _isCyclic(V startingVertex, Set<V> explored, Set<V> ancestor) {
            if (ancestor.contains(startingVertex)) {
                return true;
            }
            explored.add(startingVertex);
            ancestor.add(startingVertex);

            for(V vertex: this.successors(startingVertex)) {
                if (explored.contains(vertex) && _isCyclic(vertex, explored, ancestor)) {
                    return true;
                }
            }
            ancestor.remove(startingVertex);
            return false;
        }

        @Override
        public void stronglyConnectedComponents() {
            Set<V> explored = new HashSet<>();
            List<V> exploredOrder = new ArrayList<>();

            // Step-1: DFS on reversed graph and keep track of explored time of each node.
            V startingVertex = nextUnexploredNode(explored);
            while (startingVertex != null) {
                _reverseDFS(startingVertex, explored, exploredOrder);
                startingVertex = nextUnexploredNode(explored);
            }

            // Step-2: DFS on original graph in descending order of explored time.
            Collections.reverse(exploredOrder);
            explored.clear();
            for (V leaderVertex: exploredOrder) {
                if (!explored.contains(leaderVertex)) {
                    _printDFS(leaderVertex, explored);
                    System.out.println();
                }
            }
        }

        private void _reverseDFS(V startingVertex, Set<V> explored, List<V> exploredOrder) {
            explored.add(startingVertex);
            for (V vertex: this.predecessors(startingVertex)) { // this is the only change for reverse DFS
                if (!explored.contains(startingVertex)) {
                    _reverseDFS(vertex, explored, exploredOrder);
                }
            }
            exploredOrder.add(startingVertex);
        }

        private void _printDFS(V startingVertex, Set<V> explored) {
            explored.add(startingVertex);
            System.out.print(startingVertex + " ");
            for (V vertex: this.successors(startingVertex)) {
                if (!explored.contains(vertex)) {
                    _printDFS(vertex, explored);
                }
            }
        }

        @Override
        public Map<V, Double> singleSourceShortestPathDijkstra(V source) {
            // Initializations.
            final Double INF = Double.MAX_VALUE - 1;
            Set<V> explored = new HashSet<>();
            Set<V> unexplored = this.vertices().stream().collect(Collectors.toSet()); // Creates a copy of vertices.
            Map<V, Double> distances = this.vertices().stream().filter(v ->  v != source).collect(Collectors.toMap(v -> v, v -> INF));
            distances.put(source, 0d);

            // We will take one vertex from unexplored and move it to explored set.
            while (!unexplored.isEmpty()) {
                V candidate = minimumDistanceVertex(explored, distances);

                // Add candidate to explored set.
                explored.add(candidate);
                unexplored.remove(candidate);

                // Revisit distance of all neighbors of candidate.
                this.successors(candidate).stream()
                        .forEach(v -> {
                            Double neighborDistance = distances.get(candidate) + this.edgeWeight(candidate, v);
                            if (distances.get(v) > neighborDistance) {
                                distances.put(v, neighborDistance);
                            }
                        });

            }
            return distances;
        }

        private V minimumDistanceVertex(Set<V> explored, final Map<V, Double> distances) {
            Comparator<V> comparator = (o1, o2) -> {
                if (distances.get(o1) == distances.get(o2)) {
                    return 0;
                } else if (distances.get(o1) > distances.get(o2)) {
                    return 1;
                } else {
                    return -1;
                }
            };
            return this.vertices().stream()
                    .filter(v -> !explored.contains(v))
                    .min(comparator)
                    .get();
        }

        private void validateAndUpdateDistanceOfNeighbors(V candidate, Set<V> explored, Map<V, Double> distances) {
            // Revisit distance of all neighbors
            this.successors(candidate).stream()
                    .filter(v -> explored.contains(v)) // check only vertices that are explored.
                    .forEach(v -> {
                        Double neighborDistance = distances.get(v);
                        if (neighborDistance > distances.get(candidate) + this.edgeWeight(candidate, v)) {
                            neighborDistance = distances.get(candidate) + this.edgeWeight(candidate, v); // Since they share the reference, it should update value in Map.
                        }
                    });
        }

        private V nextUnexploredNode(Set<V> explored) {
            for (V v: vertices()) {
                if (!explored.contains(v)) {
                    return v;
                }
            }
            return null;
        }
    }
}

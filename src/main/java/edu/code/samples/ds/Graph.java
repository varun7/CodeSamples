package edu.code.samples.ds;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
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

    boolean bfs(V element);

    boolean dfs(V startingVertex, V element);

    boolean dfs(V element);

    Map<V, Double> singleSourceShortestPathDijkstra(V source);

    Map<V, Double> dijkstra(V source);

    void mstPrims();

    /**
     * Only for directed graph.
     */
    void topologicalSort();

    boolean isCyclic();

    void stronglyConnectedComponents();

    class AdjacencyListGraph<V> implements Graph<V> {

        class Edge {
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
        public boolean bfs(V element) {
            Set<V> unexplored = new HashSet<>(this.vertices());
            while (!unexplored.isEmpty()) {
                V start = unexplored.stream().findFirst().get();
                if (_bfsUnexplored(start, element, unexplored)) {
                    return true;
                }
            }
            return false;
        }

        private boolean _bfsUnexplored(V start, V element, Set<V> unexplored) {
            Queue<V> queue = new LinkedBlockingQueue<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                V vertex = queue.remove();
                unexplored.remove(vertex);

                if (vertex.equals(element)) {
                    return true;
                }

                queue.addAll(this.successors(vertex)
                            .stream()
                            .filter(v -> unexplored.contains(v))
                            .collect(Collectors.toList()));
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

        public boolean dfs(V element) {
            Set<V> unexplored = new HashSet<>(this.vertices());
            while (!unexplored.isEmpty()) {
                V start = unexplored.stream().findFirst().get();
                if (_dfsUnexplored(start, element, unexplored)) {
                    return true;
                }
            }
            return false;
        }

        private boolean _dfsUnexplored(V start, V element, Set<V> unexplored) {
            if (!unexplored.contains(start)) {
                return false;
            }
            unexplored.remove(start);
            if (start.equals(element)) {
                return true;
            }

            for (V vertex: this.successors(start)) {
                if (unexplored.contains(vertex) && _dfsUnexplored(vertex, element, unexplored)) {
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
                // check for self-loop.
                if (vertex == startingVertex) {
                    return true;
                }
                if (!explored.contains(vertex) && _isCyclic(vertex, explored, ancestor)) {
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
            // Creates a copy of set of vertices.
            Set<V> unexplored = new HashSet<>(this.vertices());
            Map<V, Double> distances = this.vertices().stream()
                                            .filter(v ->  v != source)
                                            .collect(Collectors.toMap(v -> v, v -> INF));
            distances.put(source, 0d);

            // We will take one vertex from unexplored and move it to explored set.
            while (!unexplored.isEmpty()) {
                V candidate = minimumDistanceVertex(explored, distances);

                // Add candidate to explored set.
                explored.add(candidate);
                unexplored.remove(candidate);

                // Revisit distance of all neighbors of candidate.
                this.successors(candidate)
                        .forEach(v -> {
                            Double neighborDistance = distances.get(candidate) + this.edgeWeight(candidate, v);
                            if (distances.get(v) > neighborDistance) {
                                distances.put(v, neighborDistance);
                            }
                        });

            }
            return distances;
        }

        private class Pair<T,U> {
            private T first;
            private U second;

            public Pair(T first, U second) {
                this.first = first;
                this.second = second;
            }

            public T getFirst() {
                return first;
            }

            public U getSecond() {
                return second;
            }

            @Override
            public int hashCode() {
                return first.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return first.equals(((Pair) obj).getFirst());
            }
        }

        @Override
        public Map<V, Double> dijkstra(V source) {
            final Double INF = Double.MAX_VALUE - 1;
            Set<V> explored = new HashSet<>();

            // Initializations.
            Map<V, Double> distances = this.vertices().stream().collect(Collectors.toMap(v -> v, v-> INF));
            distances.put(source, 0d);

            // Treeset and TreeMap both are implemented on balanced binary search tree.
            TreeSet<Pair<V, Double>> treeSet = new TreeSet<>((p, q) -> Double.compare(p.getSecond(), q.getSecond()));
            this.vertices().stream().forEach(v -> treeSet.add(new Pair<>(v, INF)));
            treeSet.add(new Pair<>(source, 0d));

            // We will take one vertex from unexplored and move it to explored set.
            while (!treeSet.isEmpty()) {

                // Remove minimum distance vertex and mark it as explored.
                Pair<V, Double> candidate = treeSet.pollFirst(); // Removes the first element from the tree, replacement of lowest function.
                explored.add(candidate.getFirst());

                // Revisit distance of all neighbors of candidate.
                this.successors(candidate.getFirst())
                        .stream()
                        .filter(v -> !explored.contains(v))
                        .forEach(v -> {
                            Double neighborDistance = distances.get(candidate.getFirst()) + this.edgeWeight(candidate.getFirst(), v);
                            if (distances.get(v) > neighborDistance) {
                                distances.put(v, neighborDistance);
                                treeSet.add(new Pair<>(v, neighborDistance));
                            }
                        });

            }
            return distances;
        }

        private V minimumDistanceVertex(Set<V> explored, final Map<V, Double> distances) {
            Comparator<V> comparator = (o1, o2) -> {
                if (distances.get(o1).doubleValue() == distances.get(o2).doubleValue()) {
                    return 0;
                } else if (distances.get(o1) > distances.get(o2)) {
                    return 1;
                } else {
                    return -1;
                }
            };
            return this.vertices().stream()
                    .filter(v -> !explored.contains(v)) // this is same as unexplored set, we can use that directly.
                    .min(comparator)
                    .get();
        }

        @Override
        public void mstPrims() {
            // Step-1 initializations.
            V source = this.vertices().stream().findAny().get();
            Set<V> unexplored = this.vertices().stream()
                    .filter(v -> v != source)
                    .collect(Collectors.toSet());
            Set<V> explored = new HashSet<>();
            Map<V, Double> distances = unexplored.stream()
                    .collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE -1 ));
            distances.put(source, 0d);

            // At every iteration we move a vertex from unexplored to explored set.
            while (!unexplored.isEmpty()) {
                // Step-2: Pick unexplored vertex at minimum distance from explored set of vertices.
                V candidate = minimumDistanceVertex(explored, distances);
                explored.add(candidate);
                unexplored.remove(candidate);

                System.out.println(candidate + " cost = " + distances.get(candidate));

                // Step-3: For the newly explored vertex re-compute the distances.
                this.successors(candidate).stream()
                        .filter(v -> !explored.contains(v))
                        .forEach(v -> {
                            double neighborDistance = this.edgeWeight(candidate, v);
                            if (neighborDistance < distances.get(v)) {
                                distances.put(v, neighborDistance);
                            }
                        });
            }
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

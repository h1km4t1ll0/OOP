package ru.nsu.dolgov.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Class for Graph implementation.
 *
 * @author Долгов Даниил
 * @version 1.0
 */
class Graph<T> {
    private final Map<T, List<T>> verticesMap = new HashMap<>();

    /**
     * This method adds a new vertex to the graph.
     *
     * @param vertex - vertex to be added.
     */
    public void addNewVertex(T vertex) {
        verticesMap.put(vertex, new LinkedList<>());
    }

    /**
     * This method adds an edge between source and destination.
     *
     * @param source        - source vertex.
     * @param destination   - destination vertex.
     * @param bidirectional - whether an edge bidirectional or not.
     */
    public void addNewEdge(T source, T destination, boolean bidirectional) {
        if (!verticesMap.containsKey(source))
            addNewVertex(source);
        if (!verticesMap.containsKey(destination)) {
            addNewVertex(destination);
        }

        verticesMap.get(source).add(destination);

        if (bidirectional) {
            verticesMap.get(destination).add(source);
        }
    }

    /**
     * The method counts the number of vertices.
     *
     * @return - integer value of vertices in the graph.
     */
    public int countVertices() {
        return verticesMap.keySet().size();
    }

    /**
     * The method counts the number of edges.
     *
     * @param bidirectionally - count bidirectionally or not.
     * @return - integer value of edges in the graph.
     */
    public int countEdges(boolean bidirectionally) {
        int count = 0;
        for (T v : verticesMap.keySet()) {
            count = count + verticesMap.get(v).size();
        }
        if (bidirectionally) {
            count = count / 2;
        }
        return count;
    }

    /**
     * Checks a graph has vertex or not.
     *
     * @param vertex - vertex to be checked.
     * @return - whether a graph contains a vertex or not.
     */
    public boolean containsVertex(T vertex) {
        return verticesMap.containsKey(vertex);
    }

    /**
     * Checks a graph has edge or not.
     *
     * @param sourceVertex      - source vertex.
     * @param destinationVertex - destination vertex.
     * @return - true if the graph contains an edge else false.
     */
    public boolean containsEdge(T sourceVertex, T destinationVertex) {
        return verticesMap.get(sourceVertex).contains(destinationVertex);
    }

    /**
     * Returns an
     *
     * @return - string representation of the adjacency matrix.
     */
    public String getAdjacencyList() {
        StringBuilder builder = new StringBuilder();
        for (T v : verticesMap.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : verticesMap.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }
        return (builder.toString());
    }

    public String getAdjacencyMatrix() {
        StringBuilder builder = new StringBuilder();

        builder.append("   ");
        for (T v : verticesMap.keySet()) {
            builder.append(v.toString() + " ");
        }
        builder.append("\n");
        builder.append("   ");
        for (T v : verticesMap.keySet()) {
            builder.append("- ");
        }
        builder.append("\n");
        for (T v : verticesMap.keySet()) {
            builder.append(v.toString() + "| ");

            var currentVertexList = verticesMap.get(v);
            for (T v_ : verticesMap.keySet()) {
                if (currentVertexList.contains(v_)) {
                    builder.append("1 ");
                } else {
                    builder.append( "0 ");
                }
            }
//            for (T w : verticesMap.get(v)) {
//                builder.append(w.toString() + " ");
//            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

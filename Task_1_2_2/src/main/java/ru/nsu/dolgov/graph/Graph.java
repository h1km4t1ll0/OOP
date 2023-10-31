package ru.nsu.dolgov.graph;

import java.util.*;

/**
 * Class for Graph implementation.
 *
 * @author Долгов Даниил
 * @version 1.0
 */
class Graph<T> {
    private final Map<T, ArrayList<Edge<T>>> incidenceList = new HashMap<>();

    /**
     * This method adds a new vertex to the graph.
     *
     * @param vertex vertex to be added.
     */
    public void addNewVertex(T vertex) {
        incidenceList.put(vertex, new ArrayList<>());
    }

    /**
     * This method adds an edge between source and destination.
     *
     * @param source        source vertex.
     * @param destination   destination vertex.
     * @param bidirectional whether an edge bidirectional or not.
     */
    public void addEdge(T source, T destination, T value, boolean bidirectional) {
        if (!incidenceList.containsKey(source))
            addNewVertex(source);
        if (!incidenceList.containsKey(destination)) {
            addNewVertex(destination);
        }

        incidenceList.get(source).add(new Edge<>(value, source, destination));

        if (bidirectional) {
            incidenceList.get(destination).add(new Edge<>(value, destination, source));
        }
    }

    /**
     * The method counts the number of vertices.
     *
     * @return - integer value of vertices in the graph.
     */
    public int countVertices() {
        return incidenceList.keySet().size();
    }

    /**
     * The method counts the number of edges.
     *
     * @param bidirectionally - count bidirectionally or not.
     * @return - integer value of edges in the graph.
     */
    public int countEdges(boolean bidirectionally) {
        int count = 0;
        for (T v : incidenceList.keySet()) {
            count += incidenceList.get(v).size();
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
        return incidenceList.containsKey(vertex);
    }

    /**
     * Checks a graph has edge or not.
     *
     * @param sourceVertex      - source vertex.
     * @param destinationVertex - destination vertex.
     * @return - true if the graph contains an edge else false.
     */
    public boolean containsEdge(T sourceVertex, T destinationVertex) {
        return incidenceList.get(sourceVertex).stream().filter(
                edge -> edge.destVertex == destinationVertex).toList().size() != 0;
    }

    public ArrayList<Edge<T>> getEdges() {
        HashSet<Edge<T>> edgesSet = new HashSet<>();
        this.incidenceList.values().forEach(edgesSet::addAll);
        return new ArrayList<>(edgesSet);
    }

    public ArrayList<T> getVertices() {
        return new ArrayList<>(this.incidenceList.keySet());
    }

    /**
     * Returns an
     *
     * @return - string representation of the adjacency matrix.
     */
    public ArrayList<ArrayList<T>> getIncidentMatrix(ArrayList<Edge<T>> edges, ArrayList<T> vertices) {
        ArrayList<ArrayList<T>> incidentMatrix = new ArrayList<>();
        int counter = 0;
        for (T vertex : vertices) {
            incidentMatrix.add(counter, new ArrayList<>());
            for (Edge<T> edge : edges) {
                if (edge.incident(vertex)) {
                    incidentMatrix.get(counter).add(edge.value);
                } else {
                    incidentMatrix.get(counter).add(null);
                }
            }
            counter++;
        }

        return incidentMatrix;
    }

    public ArrayList<ArrayList<T>> getAdjacencyMatrix() {
        ArrayList<ArrayList<T>> adjacencyMatrix = new ArrayList<>();
        int counter = 0;
        for (T sourceVertex : this.incidenceList.keySet()) {
            adjacencyMatrix.add(counter, new ArrayList<>());
            for (T destVertex : this.incidenceList.keySet()) {
                for (Edge<T> edge : this.incidenceList.get(destVertex)) {
                    if (edge.sourceVertex == sourceVertex && edge.destVertex == destVertex) {
                        adjacencyMatrix.get(counter).add(edge.value);
                    } else {
                        adjacencyMatrix.get(counter).add(null);
                    }
                }
            }
            counter++;
        }

        return adjacencyMatrix;
    }

    public Map<T, ArrayList<Edge<T>>> getIncidenceList() {
        return this.incidenceList;
    }
}

package ru.nsu.dolgov.graph.utils;

import ru.nsu.dolgov.graph.Edge;
import ru.nsu.dolgov.graph.Graph;

import java.util.*;

public class TopologicalSort<T> {
    Graph<T> graph;

    TopologicalSort(Graph<T> graph) {
        this.graph = graph;
    }

    void topologicalSortUtil(T vertex, HashMap<T, Boolean> visited,
                             Stack<T> stack) {
        visited.put(vertex, true);

        for (Edge<T> edge : graph.getIncidenceList().get(vertex)) {
            if (!visited.get(edge.destVertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        stack.push(vertex);
    }

    ArrayList<T> topologicalSort(T startVertex) {
        Stack<T> stack = new Stack<>();

        HashMap<T, Boolean> visited = new HashMap<>();

        for (T vertex : graph.getVertices()) {
            visited.put(vertex, false);
        }

        ArrayList<T> vertices = graph.getVertices();

        vertices.remove(startVertex);
        vertices.add(0, startVertex);

        for (T vertex : graph.getVertices()) {
            if (!visited.get(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        return new ArrayList<>(stack);
    }

}

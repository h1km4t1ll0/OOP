package ru.nsu.dolgov.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


/**
 * Tests for the graph implementation class.
 */
public class GraphTest {
    @Test
    void checkRoot() {
        Graph<Integer> graph=new Graph<>();

        graph.addNewEdge(0, 1, true);
        graph.addNewEdge(0, 4, true);
        graph.addNewEdge(1, 2, true);
        graph.addNewEdge(1, 3, false);
        graph.addNewEdge(1, 4, true);
        graph.addNewEdge(2, 3, true);
        graph.addNewEdge(2, 4, true);
        graph.addNewEdge(3, 0, true);
        graph.addNewEdge(2, 0, true);


        System.out.println("Adjacency matrix: ");
        System.out.println(graph.getAdjacencyMatrix());

        System.out.println("Adjacency List: ");
        System.out.println(graph.getAdjacencyList());
        System.out.println("Total number of edges: " + graph.countVertices());
        System.out.println("Total number of edges: " + graph.countEdges(true));
        System.out.println("A graph contains an edge between 3 and 4: " +
                graph.containsEdge(3, 4));
        System.out.println("A graph contains an edge between 2 and 4: " +
                graph.containsEdge(2, 4));
    }

}

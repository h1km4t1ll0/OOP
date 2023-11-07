package ru.nsu.dolgov.graph.utils;

import ru.nsu.dolgov.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GraphInitializer class provides methods to initialize graphs from input files.
 */
public class GraphParser {
    /**
     * Initialize an adjacency list graph from an input file.
     *
     * @param inputFile   The path to the input file.
     * @param matrixType  The type of matrix representation to create
     *                    (e.g., Adjacency List, Adjacency Matrix, Incidence Matrix).
     * @return The initialized graph.
     * @throws FileNotFoundException if the input file is not found.
     */
    public static Graph initAdjListGraph(String inputFile, Graph.MatrixType matrixType)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFile));
        int v = scanner.nextInt();
        Graph graph = new Graph(v, matrixType);
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                graph.addEdge(i, j, 0);
            }
        }

        while (scanner.hasNext()) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            double weight = scanner.nextDouble();
            graph.changeWeight(source, destination, weight);
        }
        scanner.close();

        return graph;
    }

    /**
     * Initialize an adjacency matrix graph from an input file.
     *
     * @param inputFile   The path to the input file.
     * @param matrixType  The type of matrix representation to create
     *                    (e.g., Adjacency List, Adjacency Matrix, Incidence Matrix).
     * @return The initialized graph.
     * @throws FileNotFoundException if the input file is not found.
     */
    public static Graph initAdjMatGraph(String inputFile, Graph.MatrixType matrixType)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFile));
        int v = scanner.nextInt();
        Graph graph = new Graph(v, matrixType);
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                double weight = scanner.nextDouble();
                if (weight != 0.0) {
                    graph.addEdge(i, j, weight);
                }
            }
        }
        scanner.close();
        return graph;
    }

    /**
     * Initialize an incidence matrix graph from an input file.
     *
     * @param inputFile   The path to the input file.
     * @param matrixType  The type of matrix representation to create
     *                    (e.g., Adjacency List, Adjacency Matrix, Incidence Matrix).
     * @return The initialized graph.
     * @throws FileNotFoundException if the input file is not found.
     */
    public static Graph initIncMatGraph(String inputFile, Graph.MatrixType matrixType)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFile));
        int v = scanner.nextInt();
        int e = scanner.nextInt();
        Graph graph = new Graph(v, matrixType);
        List<List<Integer>> edges = new ArrayList<>();
        scanner.nextLine();
        String edgePairsLine = scanner.nextLine();
        String[] edgePairs = edgePairsLine.split(" ");
        for (String edgePair : edgePairs) {
            String[] vertices = edgePair.split("-");
            int startVertex = Integer.parseInt(vertices[0]);
            int endVertex = Integer.parseInt(vertices[1]);
            List<Integer> edge = new ArrayList<>();
            edge.add(startVertex);
            edge.add(endVertex);
            edges.add(edge);
        }

        for (int i = 0; i < v; i++) {
            int source = scanner.nextInt();
            for (int j = 0; j < e; j++) {
                double weight = scanner.nextDouble();
                int destination;
                if (edges.get(j).get(0) == source) {
                    destination = edges.get(j).get(1);
                } else if (edges.get(j).get(1) == source) {
                    destination = edges.get(j).get(0);
                } else {
                    continue;
                }
                if (weight != 0.0) {
                    graph.addEdge(source, destination, weight);
                }
            }
        }

        scanner.close();
        return graph;
    }
}

package ru.nsu.dolgov.graph;

public class Edge<E>{
    public E value;
    public E sourceVertex;
    public E destVertex;

    Edge(E value, E sourceVertex, E destVertex) {
        this.value = value;
        this.sourceVertex = sourceVertex;
        this.destVertex = destVertex;
    }

    boolean incident(E vertex) {
        return this.destVertex == vertex || this.sourceVertex == vertex;
    }

    void changeValue(E value) {
        this.value = value;
    }

}
package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphSorterTest {
    Graph graph;
    GraphSorter sorter;

    Vertex v1;
    Vertex v2;
    Vertex v3;

    @BeforeEach
    void setUp() {
        sorter = new GraphSorter();
        graph = new Graph();
        v1 = new Vertex("V1");
        v2 = new Vertex("V2");
        v3 = new Vertex("V3");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);
    }

    @Test
    void topologicalSort() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(v3);
        result.add(v2);
        result.add(v1);
        sorter.topologicalSort(graph);

        assertTrue(result.equals( graph.vertices));
    }
}

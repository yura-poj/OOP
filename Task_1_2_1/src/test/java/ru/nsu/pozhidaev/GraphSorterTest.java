package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphSorterTest {
    GraphSorter<String> sorter;

    Vertex<String> v1;
    Vertex<String> v2;
    Vertex<String> v3;
    Edge<String> e1;
    Edge<String> e2;

    @BeforeEach
    void setUp() {
        sorter = new GraphSorter<String>();
        v1 = new Vertex<String>("V1");
        v2 = new Vertex<String>("V2");
        v3 = new Vertex<String>("V3");
        e1 = new Edge<String>(v1, v2);
        e2 = new Edge<String>(v2, v3);
    }

    @Test
    void sortListAdjacency() {
        GraphListAdjacency<String> graph = new GraphListAdjacency<String>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);

        ArrayList<Vertex<String>> result = new ArrayList<>();
        result.add(v3);
        result.add(v2);
        result.add(v1);
        ArrayList<Vertex<String>> n = sorter.topologicalSort(graph);

        assertEquals(result, n);
    }

    @Test
    void sortMatrixAdjacency() {
        GraphMatrixAdjacency<String> graph = new GraphMatrixAdjacency<String>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);

        ArrayList<Vertex<String>> result = new ArrayList<>();
        result.add(v3);
        result.add(v2);
        result.add(v1);
        ArrayList<Vertex<String>> n = sorter.topologicalSort(graph);

        assertEquals(result, n);
    }

    @Test
    void sortMatrixIncidence() {
        GraphMatrixIncidence<String> graph = new GraphMatrixIncidence<String>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);

        ArrayList<Vertex<String>> result = new ArrayList<>();
        result.add(v3);
        result.add(v2);
        result.add(v1);
        ArrayList<Vertex<String>> n = sorter.topologicalSort(graph);

        assertEquals(result, n);
    }
}

package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphMatrixIncidenceTest {
    GraphMatrixIncidence<String> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphMatrixIncidence<String>();
        Vertex<String> v1 = new Vertex<String>("a");
        Vertex<String> v2 = new Vertex<String>("b");
        Vertex<String> v3 = new Vertex<String>("c");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        Edge<String> e1 = new Edge<String>(v1, v2);
        Edge<String> e2 = new Edge<String>(v2, v3);
        Edge<String> e3 = new Edge<String>(v3, v1);
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
    }

    @Test
    void addVertex() {
        Vertex v1 = new Vertex("V0");
        int beforeSize = graph.getVertices().size();
        graph.addVertex(v1);
        assertTrue(beforeSize == graph.getVertices().size() - 1);
    }

    @Test
    void removeVertex() {
        int beforeSize = graph.getVertices().size();
        graph.removeVertex(graph.getVertices().get(0));
        assertTrue(beforeSize == graph.getVertices().size() + 1);
    }

    @Test
    void addEdge() {
        int beforeSize = graph.getEdges().size();
        Edge e1 = new Edge(graph.getVertices().get(2), graph.getVertices().get(1));
        graph.addEdge(e1);
        assertTrue(beforeSize == graph.getEdges().size() - 1);
    }

    @Test
    void removeEdge() {
        int beforeSize = graph.getEdges().size();
        graph.removeEdge(graph.getEdges().get(0));
        assertTrue(beforeSize == graph.getEdges().size() + 1);
    }

    @Test
    void findVertexNeighbors() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.add(graph.getVertices().get(0));
        vertices.add(graph.getVertices().get(2));
        assertEquals(vertices, graph.getAdjacentVertices(graph.getVertices().get(1)));
    }

    @Test
    void testToString() {
        assertTimeout(
                Duration.ofMillis(500),
                () -> {
                    graph.toString();
                });
    }

    @Test
    void testEquals() {
        GraphMatrixIncidence<String> graph2 = new GraphMatrixIncidence<String>();
        Vertex<String> v1 = new Vertex<String>("a");
        Vertex<String> v2 = new Vertex<String>("b");
        Vertex<String> v3 = new Vertex<String>("c");
        graph2.addVertex(v1);
        graph2.addVertex(v2);
        graph2.addVertex(v3);
        Edge<String> e1 = new Edge<String>(v1, v2);
        Edge<String> e2 = new Edge<String>(v2, v3);
        graph2.addEdge(e1);
        graph2.addEdge(e2);

        assertFalse(graph2.equals(graph));

        Edge<String> e3 = new Edge<String>(v3, v1);
        graph2.addEdge(e3);

        assertTrue(graph2.equals(graph));
    }

    @Test
    void testParse() {
        GraphMatrixIncidence<String> graph2 = new GraphMatrixIncidence<String>();
        graph2.parse("src/test/java/ru/nsu/pozhidaev/files/incidenceMatrix.txt");
        assertTrue(graph2.equals(graph));
    }
}

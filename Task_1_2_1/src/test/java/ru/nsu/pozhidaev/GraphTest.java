package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class GraphTest {
    Graph graph;

    @BeforeEach
    void setUp() {
        Vertex v1 = new Vertex("V1");
        Vertex v2 = new Vertex("V2");
        Vertex v3 = new Vertex("V3");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        graph = new Graph();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);
      }

    @Test
    void addVertex() {
        Vertex v1 = new Vertex("V0");
        int beforeSize = graph.vertices.size();
        graph.addVertex(v1);
        assertTrue(beforeSize == graph.vertices.size() - 1);
      }

    @Test
    void removeVertex() {
        int beforeSize = graph.vertices.size();
        graph.removeVertex(graph.vertices.get(0));
        assertTrue(beforeSize == graph.vertices.size() + 1);
      }

    @Test
    void addEdge() {
        int beforeSize = graph.edges.size();
        Edge e1 = new Edge(graph.vertices.get(2), graph.vertices.get(1));
        graph.addEdge(e1);
        assertTrue(beforeSize == graph.edges.size() - 1);
      }

    @Test
    void removeEdge() {
        int beforeSize = graph.edges.size();
        graph.removeEdge(graph.edges.get(0));
        assertTrue(beforeSize == graph.edges.size() + 1);
      }

    @Test
    void findVertexNeighbors() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.add(graph.vertices.get(0));
        vertices.add(graph.vertices.get(2));
        assertEquals(vertices, graph.findVertexNeighbors(graph.vertices.get(1)));
      }

    @Test
    void testToString() {
        assertTimeout(Duration.ofMillis(10), () -> { graph.toString(); });
      }

    @Test
    void testEquals() {
        Graph graph1 = new Graph();
        Vertex v1 = new Vertex("V1");
        Vertex v2 = new Vertex("V2");
        Vertex v3 = new Vertex("V3");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        graph1.addVertex(v1);
        graph1.addVertex(v2);
        graph1.addVertex(v3);
        graph1.addEdge(e1);

        assertFalse(graph1.equals(graph));

        graph1.addEdge(e2);

        assertTrue(graph1.equals(graph));

      }
}
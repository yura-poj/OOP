package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class GraphMatrixIncidenceTest {
    GraphMatrixIncidence graphMatrixIncidence;

    @BeforeEach
    void setUp() {
        Vertex v1 = new Vertex("V1");
        Vertex v2 = new Vertex("V2");
        Vertex v3 = new Vertex("V3");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        graphMatrixIncidence = new GraphMatrixIncidence();
        graphMatrixIncidence.addVertex(v1);
        graphMatrixIncidence.addVertex(v2);
        graphMatrixIncidence.addVertex(v3);
        graphMatrixIncidence.addEdge(e1);
        graphMatrixIncidence.addEdge(e2);
      }

    @Test
    void addVertex() {
        Vertex v1 = new Vertex("V0");
        int beforeSize = graphMatrixIncidence.vertices.size();
        graphMatrixIncidence.addVertex(v1);
        assertTrue(beforeSize == graphMatrixIncidence.vertices.size() - 1);
      }

    @Test
    void removeVertex() {
        int beforeSize = graphMatrixIncidence.vertices.size();
        graphMatrixIncidence.removeVertex(graphMatrixIncidence.vertices.get(0));
        assertTrue(beforeSize == graphMatrixIncidence.vertices.size() + 1);
      }

    @Test
    void addEdge() {
        int beforeSize = graphMatrixIncidence.edges.size();
        Edge e1 = new Edge(graphMatrixIncidence.vertices.get(2), graphMatrixIncidence.vertices.get(1));
        graphMatrixIncidence.addEdge(e1);
        assertTrue(beforeSize == graphMatrixIncidence.edges.size() - 1);
      }

    @Test
    void removeEdge() {
        int beforeSize = graphMatrixIncidence.edges.size();
        graphMatrixIncidence.removeEdge(graphMatrixIncidence.edges.get(0));
        assertTrue(beforeSize == graphMatrixIncidence.edges.size() + 1);
      }

    @Test
    void findVertexNeighbors() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.add(graphMatrixIncidence.vertices.get(0));
        vertices.add(graphMatrixIncidence.vertices.get(2));
        assertEquals(vertices, graphMatrixIncidence.findVertexNeighbors(graphMatrixIncidence.vertices.get(1)));
      }

    @Test
    void testToString() {
        assertTimeout(Duration.ofMillis(100), () -> { graphMatrixIncidence.toString(); });
      }

    @Test
    void testEquals() {
        GraphMatrixIncidence graphMatrixIncidence1 = new GraphMatrixIncidence();
        Vertex v1 = new Vertex("V1");
        Vertex v2 = new Vertex("V2");
        Vertex v3 = new Vertex("V3");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        graphMatrixIncidence1.addVertex(v1);
        graphMatrixIncidence1.addVertex(v2);
        graphMatrixIncidence1.addVertex(v3);
        graphMatrixIncidence1.addEdge(e1);

        assertFalse(graphMatrixIncidence1.equals(graphMatrixIncidence));

        graphMatrixIncidence1.addEdge(e2);

        assertTrue(graphMatrixIncidence1.equals(graphMatrixIncidence));

      }
}
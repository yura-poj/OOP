package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GraphMatrixIncidenceParserTest {
    GraphParser graphParser;
    GraphMatrixIncidence graphMatrixIncidence;

    @BeforeEach
    void setUp() {
        graphParser = new GraphParser();
        graphMatrixIncidence = new GraphMatrixIncidence();
        Vertex v1 = new Vertex("a");
        Vertex v2 = new Vertex("b");
        Vertex v3 = new Vertex("c");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        Edge e3 = new Edge(v3, v1);
        graphMatrixIncidence.addVertex(v1);
        graphMatrixIncidence.addVertex(v2);
        graphMatrixIncidence.addVertex(v3);
        graphMatrixIncidence.addEdge(e1);
        graphMatrixIncidence.addEdge(e2);
        graphMatrixIncidence.addEdge(e3);
      }

    @Test
    void parseAdjacencyList() {
        GraphMatrixIncidence graphMatrixIncidence2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/adjacencyList.txt");
        assertTrue(graphMatrixIncidence2.equals(graphMatrixIncidence));
      }
//    @Test
    void parseAdjacencyMatrix() {
        GraphMatrixIncidence graphMatrixIncidence2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/adjacencyMatrix.txt");
        assertTrue(graphMatrixIncidence2.equals(graphMatrixIncidence));
    }

//    @Test
    void parseIncidenceMatrix() {
        GraphMatrixIncidence graphMatrixIncidence2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/incidenceMatrix.txt");
        assertTrue(graphMatrixIncidence2.equals(graphMatrixIncidence));
    }

}
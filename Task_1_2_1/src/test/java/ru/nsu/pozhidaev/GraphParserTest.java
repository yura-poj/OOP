package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GraphParserTest {
    GraphParser graphParser;
    Graph graph;

    @BeforeEach
    void setUp() {
        graphParser = new GraphParser();
        graph = new Graph();
        Vertex v1 = new Vertex("a");
        Vertex v2 = new Vertex("b");
        Vertex v3 = new Vertex("c");
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        Edge e3 = new Edge(v3, v1);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
      }

    @Test
    void parseAdjacencyList() {
        Graph graph2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/adjacencyList.txt");
        assertTrue(graph2.equals(graph));
      }
//    @Test
    void parseAdjacencyMatrix() {
        Graph graph2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/adjacencyMatrix.txt");
        assertTrue(graph2.equals(graph));
    }

//    @Test
    void parseIncidenceMatrix() {
        Graph graph2 = graphParser.parse("src/test/java/ru/nsu/pozhidaev/files/incidenceMatrix.txt");
        assertTrue(graph2.equals(graph));
    }

}
package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Comparator;

public class Graph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public ArrayList<Vertex> findVertexNeighbors(Vertex vertex) {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from.equals(vertex)) {
                neighbors.add(edge.to);
            } else if (edge.to.equals(vertex)) {
                neighbors.add(edge.from);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        String result = "";
        for (Edge edge : edges) {
            result = result + edge.toString() + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        Comparator<Edge> compareByEdge = Comparator.comparing(Edge::toString);
        edges.sort(compareByEdge);
        graph.edges.sort(compareByEdge);
        Comparator<Vertex> compareByVertex = Comparator.comparing(Vertex::toString);
        vertices.sort(compareByVertex);
        graph.vertices.sort(compareByVertex);

        return edges.equals(graph.edges) && vertices.equals(graph.vertices);
    }
}

package ru.nsu.pozhidaev;

import java.util.ArrayList;

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

    public void findVertexNeighbor(Vertex vertex) {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if(edge.from.equals(vertex)) {
                neighbors.add(edge.to);
            } else if(edge.to.equals(vertex)) {
                neighbors.add(edge.from);
            }
        }
    }

}
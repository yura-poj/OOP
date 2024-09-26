package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class GraphListAdjacency<T> implements Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;

    public GraphListAdjacency()  {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

/**
*
 * @param vertex
*/
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
    }

/**
*
 * @param edge
*/
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

/**
*
 * @param vertex
*/
    @Override
    public void removeVertex(Vertex<T> vertex) {
        vertices.remove(vertex);
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(vertex) || edge.getTo().equals(vertex)) {
                removeEdge(edge);
            }
        }
    }

/**
*
 * @param edge
*/
    @Override
    public void removeEdge(Edge<T> edge) {
        edges.remove(edge);
    }

/**
*
 * @return
*/
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices);
    }

/**
*
 * @param vertex
 * @return
*/
    @Override
    public ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        ArrayList<Vertex<T>> neighbors = new ArrayList<>();
        for (Edge<T> edge : edges) {
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
        for (Edge<T> edge : edges) {
            result = result + edge.toString() + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        GraphListAdjacency graph = (GraphListAdjacency) o;
        Comparator<Edge<T>> compareByEdge = Comparator.comparing(Edge::toString);
        graph.edges.sort(compareByEdge);
        edges.sort(compareByEdge);
        Comparator<Vertex<T>> compareByVertex = Comparator.comparing(Vertex::toString);
        vertices.sort(compareByVertex);
        graph.vertices.sort(compareByVertex);

        return edges.equals(graph.edges) && vertices.equals(graph.vertices);
    }

/**
*
 * @param fileName
*/
    @Override
    public void parse(String fileName) {
        String[] tokens;
        HashMap<String, Vertex<T>> hashVertices = new HashMap<>();
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);

        while (true) {
            tokens = utils.newLine().split(">");

            if (tokens.length != 2) {
                break;
            }

            from = checkVertex(tokens[0], hashVertices);
            to = checkVertex(tokens[1], hashVertices);


            addEdge(new Edge<T>(from, to));
        }
    }

    private Vertex<T> checkVertex(String token, HashMap<String, Vertex<T>> hashVertices){
        Vertex<T> vertex;
        if(!hashVertices.containsKey(token)){
            vertex = new Vertex<T>(token);
            hashVertices.put(token, vertex);
            addVertex(vertex);
        } else {
            vertex = hashVertices.get(token);
        }

        return vertex;
    }

}

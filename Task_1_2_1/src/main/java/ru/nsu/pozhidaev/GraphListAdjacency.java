package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.HashMap;

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
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getFrom().equals(vertex) || edges.get(i).getTo().equals(vertex)) {
                removeEdge(edges.get(i));
            }
        }
        vertices.remove(vertex);
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

    @Override
    public ArrayList<Edge<T>> getEdges() {
        return new ArrayList<>(edges);
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
        return hashCode() == o.hashCode();
    }

/**
*
 * @param fileName
*/
    @Override
    public void parse(String fileName) {
        String[] tokens;
        HashMap<T, Vertex<T>> hashVertices = new HashMap<>();
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);

        while (true) {
            tokens = utils.newLine().split(">");

            if (tokens.length != 2) {
                break;
            }

            from = checkVertex((T) tokens[0], hashVertices);
            to = checkVertex((T) tokens[1], hashVertices);


            addEdge(new Edge<T>(from, to));
        }
    }

    private Vertex<T> checkVertex(T token, HashMap<T, Vertex<T>> hashVertices){
        Vertex<T> vertex;
        if(!hashVertices.containsKey(token)){
            vertex = new Vertex<T> (token);
            hashVertices.put(token, vertex);
            addVertex(vertex);
        } else {
            vertex = hashVertices.get(token);
        }

        return vertex;
    }

    @Override
    public int hashCode(){
        int result = 0;
        for (Edge<T> edge : edges) {
            result += edge.hashCode();
        }
        for (Vertex<T> vertex : vertices) {
            result += vertex.hashCode();
        }
        return result;
    }

}

package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class GraphMatrixIncidence<T> implements Graph<T>{
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private ArrayList<ArrayList<Boolean>> matrixIncidence;

    public GraphMatrixIncidence() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixIncidence = new ArrayList<>();
    }

    /**
*
 * @param vertex
*/
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);

        for (int i = 0; i < edges.size(); i++) {
            matrixIncidence.get(i).add(false);
        }
    }

/**
*
 * @param edge
*/
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
        ArrayList<Boolean> edgeArr = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edgeArr.add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).equals(edge.to) || vertices.get(i).equals(edge.from)) {
                edgeArr.set(i, true);
            }
        }
        matrixIncidence.add(edgeArr);
    }

/**
*
 * @param vertex
*/
    @Override
    public void removeVertex(Vertex<T> vertex) {
        vertices.remove(vertex);
        int id = -1;
        for (int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).equals(vertex)) {
                id = i;
                break;
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            matrixIncidence.get(i).remove(id);
        }
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
        int id = -1;
        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).equals(edge)) {
                id = i;
                break;
            }
        }
        matrixIncidence.remove(id);
    }

/**
*
 * @return
*/
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
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
        Comparator<Vertex<String>> compareByVertex = Comparator.comparing(Vertex::toString);
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
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);

        tokens = utils.newLine().split("//|");

        for (String token : tokens) {
            vertices.add(new Vertex<T>(token));
            addVertex(vertices.get(vertices.size()));
        }
        while(true){
            tokens = utils.newLine().split("//|");
            if(tokens.length == 0){
                break;
            }
            for(int i = 0; i < tokens.length; i++){
                tokens = utils.newLine().split("//|");
                for(int j = 0; j < vertices.size(); j++){
                    switch (tokens[j]){
                        case "1":
                            from = vertices.get(i);
                            break;
                        case "-1":
                            to = vertices.get(j);
                            break;
                        case "2":
                            from = vertices.get(j);
                            to = vertices.get(j);
                            break;
                    }
                }
                addEdge(new Edge<T>(from, to));
            }
        }
    }
}

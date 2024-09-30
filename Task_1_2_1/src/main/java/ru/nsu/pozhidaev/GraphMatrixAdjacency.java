package ru.nsu.pozhidaev;

import java.io.File;
import java.util.*;

public class GraphMatrixAdjacency<T> implements Graph<T> {
    private Scanner scanner;
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private ArrayList<ArrayList<Boolean>> matrixAdjacency;

    public GraphMatrixAdjacency() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixAdjacency = new ArrayList<>();
    }

    /**
*
 * @param vertex
*/
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        ArrayList<Boolean> array = new ArrayList<>();
        for(int i = 0; i < vertices.size() - 1; i++) {
            matrixAdjacency.get(i).add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            array.add(false);
        }
        matrixAdjacency.add(array);
    }

/**
*
 * @param edge
*/
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
        int id_from = 0, id_to = 0;
        for(int i = 0; i < vertices.size() - 1; i++) {
            if(vertices.get(i).equals(edge.getFrom())) {
                id_from = i;
            }
            if(vertices.get(i).equals(edge.getTo())) {
                id_to = i;
            }
        }
        matrixAdjacency.get(id_from).set(id_to, true);
    }

/**
*
 * @param vertex
*/
    @Override
    public void removeVertex(Vertex<T> vertex) {
        vertices.remove(vertex);
        int id = 0;
        for(int i = 0; i < vertices.size() - 1; i++) {
            if(vertices.get(i).equals(vertex)) {
                id = i;
            }
        }
        matrixAdjacency.remove(id);
        for(int i = 0; i < vertices.size() - 1; i++) {
            matrixAdjacency.get(i).remove(id);
        }

        for (int i = 0; i < edges.size() - 1; i++) {
      if (edges.get(i).getFrom().equals(vertex) || edges.get(i).getTo().equals(vertex)) {
        matrixAdjacency.get(i).set(vertices.indexOf(edges.get(i).getTo()), false);
        matrixAdjacency.get(i).set(vertices.indexOf(edges.get(i).getTo()), false);
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
        int id_from = 0, id_to = 0;
        for(int i = 0; i < vertices.size() - 1; i++) {
            if(vertices.get(i).equals(edge.getFrom())) {
                id_from = i;
            }
            if(vertices.get(i).equals(edge.getTo())) {
                id_to = i;
            }
        }
        matrixAdjacency.get(id_from).set(id_to, false);
    }

/**
*
 * @return
*/
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return new ArrayList<> (vertices);
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
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);
        tokens = utils.newLine().split("[/|]");
        for (String token : tokens) {
            addVertex(new Vertex<T>((T) token));
        }
        for(int i = 0; i < vertices.size(); i++){
            tokens = utils.newLine().split("[/|]");
            for(int j = 1; j <= vertices.size(); j++){
                if(Objects.equals(tokens[j], "1")){
                    addEdge(new Edge<T>(vertices.get(i), vertices.get(j-1)));
                }
            }
        }
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

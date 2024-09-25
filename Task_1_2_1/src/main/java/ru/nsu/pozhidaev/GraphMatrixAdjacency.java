package ru.nsu.pozhidaev;

import java.io.File;
import java.util.*;

public class GraphMatrixAdjacency implements Graph {
    private Scanner scanner;
    private ArrayList<Vertex<String>> vertices;
    private ArrayList<Edge> edges;
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
    public void addVertex(Vertex<String> vertex) {
        vertices.add(vertex);
        ArrayList<Boolean> array = new ArrayList<>();
        for(int i = 0; i < vertices.size() - 1; i++) {
            array.add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            matrixAdjacency.get(i).add(false);
        }
    }

/**
*
 * @param edge
*/
    @Override
    public void addEdge(Edge edge) {
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
    public void removeVertex(Vertex<String> vertex) {
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

    }

/**
*
 * @param edge
*/
    @Override
    public void removeEdge(Edge edge) {
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
    public ArrayList<Vertex<String>> getVertices() {
        return vertices;
    }

/**
*
 * @param vertex
 * @return
*/
    @Override
    public ArrayList<Vertex<String>> getAdjacentVertices(Vertex<String> vertex) {
        ArrayList<Vertex<String>> neighbors = new ArrayList<>();
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
        GraphListAdjacency graph = (GraphListAdjacency) o;
        Comparator<Edge> compareByEdge = Comparator.comparing(Edge::toString);
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
        File file;
        String line;
        String[] tokens;
        HashMap<String, Vertex<String>> vertices = new HashMap<>();
        Vertex<String> from = null;
        Vertex<String> to = null;
        tokens = newLine().split("//|");
        for (String token : tokens) {
            addVertex(new Vertex<String>(token));
        }
        for(int i = 0; i < vertices.size(); i++){
            tokens = newLine().split("//|");
            for(int j = 1; j < vertices.size(); j++){
                if(Objects.equals(tokens[j], "1")){
                    addEdge(new Edge(vertices.get(i), vertices.get(j)));
                }
            }
        }
    }

    private String newLine(){
        if(!scanner.hasNextLine()){
            return "";
        }
        return scanner.nextLine().replace(" ", "");
    }
}

package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class GraphListAdjacency implements Graph {
    private Scanner scanner;
    private ArrayList<Vertex<String>> vertices;
    private ArrayList<Edge> edges;

    public GraphListAdjacency()  {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

/**
*
 * @param vertex
*/
    @Override
    public void addVertex(Vertex<String> vertex) {
        vertices.add(vertex);
    }

/**
*
 * @param edge
*/
    @Override
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

/**
*
 * @param vertex
*/
    @Override
    public void removeVertex(Vertex<String> vertex) {
        vertices.remove(vertex);
    }

/**
*
 * @param edge
*/
    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge);
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
        file = new File(fileName);

        try{
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            tokens = newLine().split(">");

            if (tokens.length != 2) {
                break;
            }

            from = checkVertex(tokens[0], vertices);
            to = checkVertex(tokens[1], vertices);


            addEdge(new Edge(from, to));
        }
    }

    private Vertex<String> checkVertex(String token, HashMap<String, Vertex<String>> vertices){
        Vertex<String> vertex;
        if(!vertices.containsKey(token)){
            vertex = new Vertex<String>(token);
            vertices.put(token, vertex);
            addVertex(vertex);
        } else {
            vertex = vertices.get(token);
        }

        return vertex;
    }

    private String newLine(){
        if(!scanner.hasNextLine()){
            return "";
        }
        return scanner.nextLine().replace(" ", "");
    }
}

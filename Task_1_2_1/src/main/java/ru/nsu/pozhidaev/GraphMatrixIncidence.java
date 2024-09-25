package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class GraphMatrixIncidence implements Graph{
    private Scanner scanner;
    private ArrayList<Vertex<String>> vertices;
    private ArrayList<Edge> edges;
    private ArrayList<ArrayList<Boolean>> matrixIncidence;

    public GraphMatrixIncidence() {
        scanner = new Scanner(System.in);
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixIncidence = new ArrayList<>();
    }

    /**
*
 * @param vertex
*/
    @Override
    public void addVertex(Vertex<String> vertex) {
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
    public void addEdge(Edge edge) {
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
    public void removeVertex(Vertex<String> vertex) {
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
    }

/**
*
 * @param edge
*/
    @Override
    public void removeEdge(Edge edge) {
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
        Vertex<String> from = null;
        Vertex<String> to = null;
        file = new File(fileName);

        try{
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tokens = newLine().split("//|");

        for (String token : tokens) {
            vertices.add(new Vertex<String>(token));
            addVertex(vertices.get(vertices.size()));
        }
        while(true){
            tokens = newLine().split("//|");
            if(tokens.length == 0){
                break;
            }
            for(int i = 0; i < tokens.length; i++){
                tokens = newLine().split("//|");
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
                addEdge(new Edge(from, to));
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

package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class GraphParser {
    File file;
    Graph graph;
    Scanner scanner;
    String line;
    String[] tokens;
    public Graph parse(String graphFile) {
        graph = new Graph();
        file = new File(graphFile);
        try{
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = scanner.nextLine();
        switch (line){
            case "incidence matrix":
                return incidenceMatrix();
            case "adjacency matrix":
                return adjacencyMatrix();
            case "adjacent list":
                return adjacencyList();
        }
        return null;
    }
    private Graph incidenceMatrix() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        Vertex from = null;
        Vertex to = null;
        for (String token : tokens) {
            vertices.add(new Vertex(token));
            graph.addVertex(vertices.get(vertices.size()));
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
                graph.addEdge(new Edge(from, to));
            }
        }
        return graph;
    }

    private Graph adjacencyList() {
        HashMap<String, Vertex> vertices = new HashMap<>();
        Vertex from;
        Vertex to;

        while (true) {
            tokens = newLine().split(">");

            if (tokens.length != 2) {
                System.err.println("Wrong file format: ");
                break;
            }

            from = checkVertex(tokens[0], vertices);
            to = checkVertex(tokens[1], vertices);


            graph.addEdge(new Edge(from, to));
        }
        return graph;
    }

    private Vertex checkVertex(String token, HashMap<String, Vertex> vertices){
        Vertex vertex;
        if(!vertices.containsKey(token)){
            vertex = new Vertex(token);
            vertices.put(token, vertex);
        } else {
            vertex = vertices.get(token);
        }

        return vertex;
    }

    private Graph adjacencyMatrix() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        tokens = newLine().split("//|");
        for (String token : tokens) {
            vertices.add(new Vertex(token));
            graph.addVertex(vertices.get(vertices.size()));
        }
        for(int i = 0; i < vertices.size(); i++){
            tokens = newLine().split("//|");
            for(int j = 1; j < vertices.size(); j++){
                if(Objects.equals(tokens[j], "1")){
                    graph.addEdge(new Edge(vertices.get(i), vertices.get(j)));
                }
            }
        }
        return graph;
    }

    private String newLine(){
        return scanner.nextLine().replace(" ", "");
    }
}

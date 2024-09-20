package ru.nsu.pozhidaev;

import java.io.FileInputStream;
import java.io.IOException;

public class GraphParser {
    FileInputStream file;
    Graph graph;

    public Graph parse(String graphFile) {
        graph = new Graph();
        try{
             file = new FileInputStream("notes.txt");
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        return graph;
    }
}

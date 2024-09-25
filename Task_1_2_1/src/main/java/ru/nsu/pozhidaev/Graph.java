package ru.nsu.pozhidaev;

import java.util.ArrayList;

public interface Graph {
  public void addVertex(Vertex<String> vertex);

  public void addEdge(Edge edge);

  public void removeVertex(Vertex<String> vertex);

  public void removeEdge(Edge edge);

  public ArrayList<Vertex<String>> getVertices();

  public ArrayList<Vertex<String>> getAdjacentVertices(Vertex<String> vertex);

  public void parse(String fileName);

  public String toString();

  public boolean equals(Object o);

}

package ru.nsu.pozhidaev;

import java.util.ArrayList;

public interface Graph<T> {
  public void addVertex(Vertex<T> vertex);

  public void addEdge(Edge<T> edge);

  public void removeVertex(Vertex<T> vertex);

  public void removeEdge(Edge<T> edge);

  public ArrayList<Vertex<T>> getVertices();

  public ArrayList<Edge<T>> getEdges();

  public ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertex);

  public void parse(String fileName);

  public String toString();

  public boolean equals(Object o);

}

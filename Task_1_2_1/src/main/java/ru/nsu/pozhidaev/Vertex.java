package ru.nsu.pozhidaev;


public class Vertex {
  String name;

  public Vertex(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "V: " + name;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass() != this.getClass()) return false;
    Vertex vertex = (Vertex) o;
    return name.equals(vertex.getName());
  }
}

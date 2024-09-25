package ru.nsu.pozhidaev;

public class Vertex<T> {
    T name;

    int color;

    public Vertex(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public int getColor() {
      return color;
    }

    public void setColor(int color) {
      if(color >= 0 && color <= 2){
        this.color = color;
      }

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

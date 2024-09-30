package ru.nsu.pozhidaev;

public class Vertex<T> {
    T name;

    public Vertex(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
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

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

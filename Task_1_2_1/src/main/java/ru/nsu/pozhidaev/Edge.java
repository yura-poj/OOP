package ru.nsu.pozhidaev;

public class Edge<T> {
    Vertex<T> from;
    Vertex<T> to;

    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    public Vertex<T> getFrom() {
        return from;
    }

    public Vertex<T> getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        Edge<T> edge = (Edge<T>) o;
        return from.equals(edge.from) && to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }
}

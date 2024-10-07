package ru.nsu.pozhidaev;

/**
 * edge.
 *
 * @param <T> type of all.
 */
public class Edge<T> {
    private Vertex<T> from;
    private Vertex<T> to;

    /**
     * constructor.
     *
     * @param from vertex.
     * @param to vertex.
     */
    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    /**
     * get from vertex.
     *
     * @return from vertex.
     */
    public Vertex<T> getFrom() {
        return from;
    }

    /**
     * return to vertex.
     *
     * @return vertex 'to'.
     */
    public Vertex<T> getTo() {
        return to;
    }

    /**
     * vertex to string.
     *
     * @return string of edge like (V: 1, V: 2).
     */
    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }

    /**
     * equals graphs or not.
     *
     * @param o object that we check.
     *
     * @return bool equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Edge<T> edge = (Edge<T>) o;
        return from.equals(edge.from) && to.equals(edge.to);
    }

    /**
     * hashcode of edge that equals sum of hashcode vertexes.
     *
     * @return int hashcode.
     */
    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }
}

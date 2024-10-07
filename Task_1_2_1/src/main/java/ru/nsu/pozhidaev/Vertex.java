package ru.nsu.pozhidaev;

/**
 * vertex.
 *
 * @param <T> type of name variable.
 */
public class Vertex<T> {
    private T name;

    /**
     * constructor.
     *
     * @param name name of vertex.
     */
    public Vertex(T name) {
        this.name = name;
    }

    /**
     * getter.
     *
     * @return name.
     */
    public T getName() {
        return name;
    }

    /**
     * setter.
     *
     * @param name name of vertex.
     */
    public void setName(T name) {
        this.name = name;
    }

    /**
     * convert vertex to string like V: name.
     *
     * @return sting of name.
     */
    @Override
    public String toString() {
        return "V: " + name;
    }

    /**
     * check object equals or not.
     *
     * @param o object ot compare.
     *
     * @return bool equals or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return name.equals(vertex.getName());
    }

    /**
     * hashcode of vertex based on name.
     *
     * @return int hashcode.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

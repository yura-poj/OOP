package ru.nsu.pozhidaev;

import java.util.ArrayList;

/**
 * interface graph.
 *
 * @param <T> type that accept vertex as name.
 */
public interface Graph<T> {

    /**
     * add vertex to graph.
     *
     * @param vertex that needed to bee added.
     */
    public void addVertex(Vertex<T> vertex);

    /**
     * add edge to graph.
     *
     * @param edge that should be added.
     */
    public void addEdge(Edge<T> edge);

    /**
     * remove vertex from graph.
     *
     * @param vertex that should be removed.
     */
    public void removeVertex(Vertex<T> vertex);

    /**
     * remove edge from graph.
     *
     * @param edge that should be removed.
     */
    public void removeEdge(Edge<T> edge);

    /**
     * get array of vertices of graph.
     *
     * @return vertices array.
     */
    public ArrayList<Vertex<T>> getVertices();

    /**
     * get edges array of graph.
     *
     * @return edges array.
     */
    public ArrayList<Edge<T>> getEdges();

    /**
     * get adjacent vertices with on vertex.
     *
     * @param vertex which neighbors should be found.
     *
     * @return neighbors array.
     */
    public ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertex);

    /**
     * parse graph from file.
     *
     * @param fileName string that show path to file.
     */
    public void parse(String fileName);

    /**
     * graph to string like edges: (V1: a, V2: b), \n etc.
     *
     * @return string of graph.
     */
    public String toString();

    /**
     * compare hashcodes and return equal or not.
     * \n@param o object that should be compared.
     *
     * @return bool equal or not.
     */
    public boolean equals(Object o);

    /**
     * make hashcode for graph.
     *
     * @return int of hashcode.
     */
    public int hashCode();
}

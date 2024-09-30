package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * graph with adjacency list.
 *
 * @param <T> type that accept vertex as name.
 */
public class GraphListAdjacency<T> implements Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;

    /**
     * constructor.
     */
    public GraphListAdjacency() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * add vertex to graph.
     *
     * @param vertex that needed to bee added.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
    }

    /**
     * add edge to graph.
     *
     * @param edge that should be added.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

    /**
     * remove vertex from graph.
     *
     * @param vertex that should be removed.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getFrom().equals(vertex) || edges.get(i).getTo().equals(vertex)) {
                removeEdge(edges.get(i));
            }
        }
        vertices.remove(vertex);
    }

    /**
     * remove edge from graph.
     *
     * @param edge that should be removed.
     */
    @Override
    public void removeEdge(Edge<T> edge) {
        edges.remove(edge);
    }

    /**
     * get array of vertices of graph.
     *
     * @return vertices array.
     */
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * get edges array of graph.
     *
     * @return edges array.
     */
    @Override
    public ArrayList<Edge<T>> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * get adjacent vertices with on vertex.
     *
     * @param vertex which neighbors should be found.
     *
     * @return neighbors array.
     */
    @Override
    public ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        ArrayList<Vertex<T>> neighbors = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                neighbors.add(edge.getTo());
            } else if (edge.getTo().equals(vertex)) {
                neighbors.add(edge.getFrom());
            }
        }
        return neighbors;
    }

    /**
     * graph to string like edges: (V1: a, V2: b), \n etc.
     *
     * @return string of graph.
     */
    @Override
    public String toString() {
        String result = "";
        for (Edge<T> edge : edges) {
            result = result + edge.toString() + "\n";
        }
        return result;
    }

    /**
     * compare hashcodes and return equal or not.
     *
     * @param o object that should be compared.
     *
     * @return bool equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        return hashCode() == o.hashCode();
    }

  /**
   * parse graph from file.
   * Example:
   * a > b -> where 'a' vertex from, 'b' vertex to, devided by '>'.
   * b > c
   * c > a
   *
   * @param fileName string that show path to file.
   */
  @Override
  public void parse(String fileName) {
        String[] tokens;
        HashMap<T, Vertex<T>> hashVertices = new HashMap<>();
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);

        while (true) {
            tokens = utils.newLine().split(">");

            if (tokens.length != 2) {
                break;
            }

            from = checkVertex((T) tokens[0], hashVertices);
            to = checkVertex((T) tokens[1], hashVertices);

            addEdge(new Edge<T>(from, to));
        }
    }

    /**
     * check if some vertex with token already exist.
     *
     * @param token name.
     * @param hashVertices hash with names of vertices.
     *
     * @return new or already existing vertex.
     */
    private Vertex<T> checkVertex(T token, HashMap<T, Vertex<T>> hashVertices) {
        Vertex<T> vertex;
        if (!hashVertices.containsKey(token)) {
            vertex = new Vertex<T>(token);
            hashVertices.put(token, vertex);
            addVertex(vertex);
        } else {
            vertex = hashVertices.get(token);
        }

        return vertex;
    }

    /**
     * make hashcode for graph.
     *
     * @return int of hashcode.
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (Edge<T> edge : edges) {
            result += edge.hashCode();
        }
        for (Vertex<T> vertex : vertices) {
            result += vertex.hashCode();
        }
        return result;
    }
}

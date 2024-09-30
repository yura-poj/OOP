package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Objects;

/**
 * graph with matrix adjacency.
 *
 * @param <T> type that accept vertex as name.
 */
public class GraphMatrixAdjacency<T> implements Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private ArrayList<ArrayList<Boolean>> matrixAdjacency;

    /**
     * constructor.
     */
    public GraphMatrixAdjacency() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixAdjacency = new ArrayList<>();
    }

    /**
     * add vertex to graph.
     *
     * @param vertex that needed to bee added.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        ArrayList<Boolean> array = new ArrayList<>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            matrixAdjacency.get(i).add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            array.add(false);
        }
        matrixAdjacency.add(array);
    }

    /**
     * add edge to graph.
     *
     * @param edge that should be added.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
        int idFrom = 0;
        int idTo = 0;
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (vertices.get(i).equals(edge.getFrom())) {
                idFrom = i;
            }
            if (vertices.get(i).equals(edge.getTo())) {
                idTo = i;
            }
        }
        matrixAdjacency.get(idFrom).set(idTo, true);
    }

    /**
     * remove vertex from graph.
     *
     * @param vertex that should be removed.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        vertices.remove(vertex);
        int id = 0;
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (vertices.get(i).equals(vertex)) {
                id = i;
            }
        }
        matrixAdjacency.remove(id);
        for (int i = 0; i < vertices.size() - 1; i++) {
            matrixAdjacency.get(i).remove(id);
        }

        for (int i = 0; i < edges.size() - 1; i++) {
            if (edges.get(i).getFrom().equals(vertex) || edges.get(i).getTo().equals(vertex)) {
                matrixAdjacency.get(i).set(vertices.indexOf(edges.get(i).getTo()), false);
                matrixAdjacency.get(i).set(vertices.indexOf(edges.get(i).getTo()), false);
            }
        }
    }

    /**
     * remove edge from graph.
     *
     * @param edge that should be removed.
     */
    @Override
    public void removeEdge(Edge<T> edge) {
        edges.remove(edge);
        int idFrom = 0;
        int idTo = 0;
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (vertices.get(i).equals(edge.getFrom())) {
                idFrom = i;
            }
            if (vertices.get(i).equals(edge.getTo())) {
                idTo = i;
            }
        }
        matrixAdjacency.get(idFrom).set(idTo, false);
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
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        return hashCode() == o.hashCode();
    }

    /**
     * parse graph from file.
     * Example:
     * Do not use tabs: cause errors.
     * Here a,b,c - verticies, devided by '|'.
     * In that example edges (From, To) = (a,b) (b,c) (c,a).
     * all new vertices and values should be devided by '|'.
     *     a | b | c
     * a | 0 | 1 | 0
     * b | 0 | 0 | 1
     * c | 1 | 0 | 0
     *
     * @param fileName string that show path to file.
     */
    @Override
    public void parse(String fileName) {
        String[] tokens;

        Utilities utils = new Utilities(fileName);
        tokens = utils.newLine().split("[/|]");
        for (String token : tokens) {
            addVertex(new Vertex<T>((T) token));
        }
        for (int i = 0; i < vertices.size(); i++) {
            tokens = utils.newLine().split("[/|]");
            for (int j = 1; j <= vertices.size(); j++) {
                if (Objects.equals(tokens[j], "1")) {
                    addEdge(new Edge<T>(vertices.get(i), vertices.get(j - 1)));
                }
            }
        }
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

package ru.nsu.pozhidaev;

import java.util.ArrayList;

/**
 * graph with matrix incidence.
 *
 * @param <T> type that accept vertex as name.
 */
public class GraphMatrixIncidence<T> implements Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private ArrayList<ArrayList<Boolean>> matrixIncidence;

    /** constructor. */
    public GraphMatrixIncidence() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixIncidence = new ArrayList<>();
    }

    /**
     * add vertex to graph.
     *
     * @param vertex that needed to bee added.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);

        for (int i = 0; i < edges.size(); i++) {
            matrixIncidence.get(i).add(false);
        }
    }

    /**
     * add edge to graph.
     *
     * @param edge that should be added.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
        ArrayList<Boolean> edgeArr = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edgeArr.add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(edge.getTo()) || vertices.get(i).equals(edge.getFrom())) {
                edgeArr.set(i, true);
            }
        }
        matrixIncidence.add(edgeArr);
    }

    /**
     * remove vertex from graph.
     *
     * @param vertex that should be removed.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        int id = -1;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(vertex)) {
                id = i;
                break;
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            matrixIncidence.get(i).remove(id);
        }
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
        int id = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equals(edge)) {
                id = i;
                break;
            }
        }
        matrixIncidence.remove(id);
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
     * parse graph from file. Example: Where a,b,c - vertices devided by '|'. And each next lines is a
     * new edges, where -1 = vertex from, 1 = vertex to. In that example edges (From, To) = (a,b)
     * (b,c) (c,a). a | b | c 1 | -1 | 0 0 | 1 | -1 -1 | 0 | 1
     *
     * @param fileName string that show path to file.
     */
    @Override
    public void parse(String fileName) {
        String[] tokens;
        Vertex<T> from = null;
        Vertex<T> to = null;

        Utilities utils = new Utilities(fileName);

        tokens = utils.newLine().split("[/|]");

        for (String token : tokens) {
            addVertex(new Vertex<T>((T) token));
        }

        for (int i = 0; i < vertices.size(); i++) {
            tokens = utils.newLine().split("[/|]");
            for (int j = 0; j < vertices.size(); j++) {
                switch (tokens[j]) {
                    case "1":
                        from = vertices.get(j);
                        break;
                    case "-1":
                        to = vertices.get(j);
                        break;
                    case "2":
                        from = vertices.get(j);
                        to = vertices.get(j);
                        break;
                }
            }
            addEdge(new Edge<T>(from, to));
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

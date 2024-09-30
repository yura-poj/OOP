package ru.nsu.pozhidaev;

import java.util.ArrayList;

public class GraphMatrixIncidence<T> implements Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private ArrayList<ArrayList<Boolean>> matrixIncidence;

    public GraphMatrixIncidence() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        matrixIncidence = new ArrayList<>();
    }

    /**
     * @param vertex
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);

        for (int i = 0; i < edges.size(); i++) {
            matrixIncidence.get(i).add(false);
        }
    }

    /**
     * @param edge
     */
    @Override
    public void addEdge(Edge<T> edge) {
        edges.add(edge);
        ArrayList<Boolean> edgeArr = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edgeArr.add(false);
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(edge.to) || vertices.get(i).equals(edge.from)) {
                edgeArr.set(i, true);
            }
        }
        matrixIncidence.add(edgeArr);
    }

    /**
     * @param vertex
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
     * @param edge
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
     * @return
     */
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public ArrayList<Edge<T>> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * @param vertex
     * @return
     */
    @Override
    public ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        ArrayList<Vertex<T>> neighbors = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.from.equals(vertex)) {
                neighbors.add(edge.to);
            } else if (edge.to.equals(vertex)) {
                neighbors.add(edge.from);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        String result = "";
        for (Edge<T> edge : edges) {
            result = result + edge.toString() + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        return hashCode() == o.hashCode();
    }

    /**
     * @param fileName
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

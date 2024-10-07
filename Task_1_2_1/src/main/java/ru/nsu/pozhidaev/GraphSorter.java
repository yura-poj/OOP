package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * graph sorter algoritms.
 * You can use it many times after initializing by calling functions.
 *
 * @param <T> type of name of vertex in graph.
 */
public class GraphSorter<T> {
    private ArrayList<Vertex<T>> vertices;
    private HashMap<Vertex<T>, Integer> colors;
    private Graph<T> graph;

    /**
     * constructor.
     */
    public GraphSorter() {
        vertices = new ArrayList<>();
        colors = new HashMap<>();
    }

    /**
     * Sort vertices of graph using topological sort algorithm.
     * can use many times without generating new object.
     *
     * @param graph vertices of which should be sorted.
     *
     * @return array of sorted vertices.
     */
    public ArrayList<Vertex<T>> topologicalSort(Graph<T> graph) {
        prepare(graph);

        for (Vertex<T> vertex : graph.getVertices()) {
            topoSort(vertex);
        }
        ArrayList<Vertex<T>> copy = new ArrayList<Vertex<T>>(vertices);
        finish();
        return new ArrayList<Vertex<T>>(copy);
    }

    /**
     * subfunction that realize Deep First Search.
     *
     * @param vertex from which start DFS.
     */
    private void topoSort(Vertex<T> vertex) {
        if (colors.get(vertex) != 0) {
            return;
        }
        colors.put(vertex, 1);
        for (Vertex<T> next : graph.getAdjacentVertices(vertex)) {
            topoSort(next);
        }
        colors.put(vertex, 2);
        vertices.add(vertex);
    }

    /**
     * prepare hashmap for using in functions.
     *
     * @param graph which should be sorted.
     */
    private void prepare(Graph<T> graph) {
        this.graph = graph;
        for (Vertex<T> vertex : graph.getVertices()) {
            colors.put(vertex, 0);
        }
    }

    /**
     * clear all arrays and hashmaps inside object.
     */
    private void finish() {
        vertices.clear();
        colors.clear();
    }
}

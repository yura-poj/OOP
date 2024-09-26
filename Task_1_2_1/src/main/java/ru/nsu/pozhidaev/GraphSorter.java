package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphSorter<T> {
    ArrayList<Vertex<T>> vertices = new ArrayList<>();
    HashMap<Vertex<T>, Integer> colors = new HashMap<>();
    Graph graph;
    public ArrayList<Vertex<T>> topologicalSort(Graph<T> graph) {
        this.graph = graph;
        for(Vertex<T> vertex : vertices) {
            colors.put(vertex, 0);
        }
        for (Vertex<T> vertex : graph.getVertices()) {
            topoSort(vertex);
        }

        return vertices;
    }

    private void topoSort(Vertex<T> vertex) {
        if (colors.get(vertex) != 0){
            return;
        }
        colors.put(vertex, 1);
        for (Vertex<T> next : graph.getAdjacentVertices(vertex)){
            topoSort(next);
        }
        colors.put(vertex, 2);
        vertices.add(vertex);
    }
}

package ru.nsu.pozhidaev;

import java.util.ArrayList;

public class GraphSorter {
    ArrayList<Vertex<String>> vertices = new ArrayList<>();
    Graph graph;
    public void topologicalSort(Graph graph) {
        this.graph = graph;
        for (Vertex<String> vertex : graph.getVertices()) {
            topoSort(vertex);
        }

        graph.setVertices(vertices);

        setWhiteVertices();
    }

    private void topoSort(Vertex<String> vertex) {
        if (vertex.getColor() != 0){
            return;
        }
        vertex.setColor(1);
        for (Vertex<String> next : graph.getAdjacentVertices(vertex)){
            topoSort(next);
        }
        vertex.setColor(2);
        vertices.add(vertex);
    }

    private void setWhiteVertices() {
        for (Vertex vertex : graph.getVertices()) {
            vertex.setColor(0);
        }
    }
}

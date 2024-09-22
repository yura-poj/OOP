package ru.nsu.pozhidaev;

import java.util.ArrayList;

public class GraphSorter {
    ArrayList<Vertex> vertices = new ArrayList<>();
    Graph graph;
    public void topologicalSort(Graph graph) {
        this.graph = graph;
        for (Vertex vertex : graph.vertices) {
            topoSort(vertex);
        }

        graph.vertices = vertices;

        setWhiteVertices();
    }

    private void topoSort(Vertex vertex) {
        if (vertex.getColor() != 0){
            return;
        }
        vertex.setColor(1);
        for (Vertex next : graph.findVertexNeighbors(vertex)){
            topoSort(next);
        }
        vertex.setColor(2);
        vertices.add(vertex);
    }

    private void setWhiteVertices() {
        for (Vertex vertex : graph.vertices) {
            vertex.setColor(0);
        }
    }
}

package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;

class EdgeTest {
    Edge<String> edge;
    Vertex<String> v1;
    Vertex<String> v2;
    @BeforeEach
    void setUp() {
        v1 = new Vertex<String>("v1");
        v2 = new Vertex<String>("v2");
        edge = new Edge<String>(v1, v2);
      }

    @Test
    void getFrom() {
        assertEquals(v1, edge.getFrom());
      }

    @Test
    void getTo() {
        assertEquals(v2, edge.getTo());
      }

    @Test
    void testToString() {
        assertTimeout(Duration.ofMillis(100), () -> { edge.toString(); });
      }

    @Test
    void testEquals() {
        Edge<String> edge2 = new Edge(v1, v2);
        assertTrue(edge.equals(edge2));
        Vertex<String> v3 = new Vertex("v3");
        Edge<String> edge3 = new Edge(v1, v3);
        assertFalse(edge.equals(edge3));
      }
}
package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VertexTest {

    Vertex vertex;

    @BeforeEach
    void setUp() {
        vertex = new Vertex("1");
    }

    @Test
    void getName() {
        assertEquals("1", vertex.getName());
    }

    @Test
    void setName() {
        vertex.setName("2");
        assertEquals("2", vertex.getName());
    }

    @Test
    void testToString() {
        assertEquals("V: 1", vertex.toString());
    }

    @Test
    void testEquals() {
        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        assertTrue(vertex1.equals(vertex));
        assertFalse(vertex2.equals(vertex));
    }
}

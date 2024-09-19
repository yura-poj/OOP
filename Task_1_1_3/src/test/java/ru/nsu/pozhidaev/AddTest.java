package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTest {
    Add add1;
    Add add2;
    Add add3;

    @BeforeEach
    void setUp() {
        add1 = new Add(new Number(1), new Number(2));
        add2 = new Add(new Number(1), new Variable("x"));
        add3 = new Add(new Variable("x"), new Variable("y"));
    }

    @Test
    void evaluate() {
        assertEquals(3, add1.evaluate(""));

        assertEquals(3, add2.evaluate("x = 2"));

        assertEquals(3, add3.evaluate("x = 1; y = 2"));
    }

    @Test
    void derivative() {
        assertEquals(0, add1.derivative("x").evaluate(""));

        assertEquals(1, add2.derivative("x").evaluate(""));

        assertEquals(1, add3.derivative("x").evaluate(""));

        assertEquals(1, add3.derivative("y").evaluate(""));
    }

    @Test
    void testToString() {
        assertEquals("(1 + 2)", add1.toString());

        assertEquals("(1 + x)", add2.toString());

        assertEquals("(x + y)", add3.toString());
    }
}
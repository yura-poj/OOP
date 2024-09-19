package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubTest {

    Sub sub1;
    Sub sub2;
    Sub sub3;

    @BeforeEach
    void setUp() {
        sub1 = new Sub(new Number(3), new Number(2));
        sub2 = new Sub(new Variable("x"), new Number(2));
        sub3 = new Sub(new Variable("x"), new Variable("y"));
    }

    @Test
    void evaluate() {
        assertEquals(1, sub1.evaluate(""));

        assertEquals(1, sub2.evaluate("x = 3"));

        assertEquals(1, sub3.evaluate("x = 3;y = 2"));
    }

    @Test
    void derivative() {
        assertEquals(0, sub1.derivative("y").evaluate(""));

        assertEquals(1, sub2.derivative("x").evaluate(""));

        assertEquals(1, sub3.derivative("x").evaluate(""));

        assertEquals(-1, sub3.derivative("y").evaluate(""));
    }

    @Test
    void testToString() {
        assertEquals("(3 - 2)", sub1.toString());

        assertEquals("(x - 2)", sub2.toString());

        assertEquals("(x - y)", sub3.toString());
    }
}
package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class MulTest {

    Mul mul1;
    Mul mul2;
    Mul mul3;

    @BeforeEach
    void setUp() {
        mul1 = new Mul(new Number(1), new Number(2));
        mul2 = new Mul(new Number(2), new Variable("x"));
        mul3 = new Mul(new Variable("x"), new Variable("y"));
    }

    @Test
    void evaluate() {
        assertEquals(2, mul1.evaluate(""));

        assertEquals(4, mul2.evaluate("x = 2"));

        assertEquals(6, mul3.evaluate("x = 2; y = 3"));
    }

    @Test
    void derivative() {
        assertEquals(0, mul1.derivative("y").evaluate(""));

        assertEquals(2, mul2.derivative("x").evaluate("x = 2"));

        assertEquals(3, mul3.derivative("x").evaluate("x = 2;y = 3"));

        assertEquals(2, mul3.derivative("y").evaluate("x = 2;y = 3"));
    }

    @Test
    void testToString() {
        assertEquals("(1 * 2)", mul1.toString());

        assertEquals("(2 * x)", mul2.toString());

        assertEquals("(x * y)", mul3.toString());
    }
}
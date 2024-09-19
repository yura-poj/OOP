package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class DivTest {

    Div div1;
    Div div2;
    Div div3;

    @BeforeEach
    void setUp() {
        div1 = new Div(new Number(1), new Number(2));
        div2 = new Div(new Number(2), new Variable("x"));
        div3 = new Div(new Variable("x"), new Variable("y"));
    }

    @Test
    void evaluate() {
        assertEquals(0.5, div1.evaluate(""));

        assertEquals(1, div2.evaluate("x = 2"));

        assertEquals(2, div3.evaluate("x = 6;y = 3"));
    }

    @Test
    void derivative() {
        assertEquals(0, div1.derivative("y").evaluate(""));

        assertEquals(-0.5, div2.derivative("x").evaluate("x = 2"));

        assertEquals(0.25, div3.derivative("x").evaluate("x = 2;y = 4"));

        assertEquals(-0.125, div3.derivative("y").evaluate("x = 2;y = 4"));
    }

    @Test
    void testToString() {
        assertEquals("(1 / 2)", div1.toString());

        assertEquals("(2 / x)", div2.toString());

        assertEquals("(x / y)", div3.toString());
    }
}
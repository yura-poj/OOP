package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableTest {
    Variable variable;

    @BeforeEach
    void setUp() {
        variable = new Variable("x");
    }

    @Test
    void evaluate() {
        assertEquals(10, variable.evaluate("x = 10"));

        assertEquals(10, variable.evaluate("y = 15; x = 10"));
    }

    @Test
    void derivative() {
        assertEquals(1, variable.derivative("x").evaluate(""));

        assertEquals(0, variable.derivative("y").evaluate(""));
    }

    @Test
    void testToString() {
        assertEquals("x", variable.toString());
    }
    
    @Test
    void testEquals() {
        assertTrue(variable.equals(new Variable("x")));
        assertFalse(variable.equals(new Variable("y")));
    }

    @Test
    void testSimplify() {
        assertTrue(variable.equals(variable.simlify()));
    }
}
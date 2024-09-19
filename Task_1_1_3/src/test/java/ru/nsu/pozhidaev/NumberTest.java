package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class NumberTest {
    Number number;

    @BeforeEach
    void setUp() {
        number = new Number(10);
    }

    @Test
    void evaluate() {
        assertEquals(10, number.evaluate(""));
    }

    @Test
    void derivative() {
        assertEquals(0, number.derivative("").evaluate(""));
    }

    @Test
    void testToString() {
        assertEquals("10", number.toString());
    }
}
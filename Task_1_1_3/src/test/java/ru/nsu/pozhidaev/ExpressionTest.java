package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExpressionTest {
    @Test
    void withoutVariables() {
        Expression e = new Mul(
                new Add(new Number(2), new Number(12)),
                new Sub(new Div(new Number(2), new Number(4)), new Number(1)));
        assertEquals(-7.0, e.evaluate(""));

        Expression n = e.derivative("x");
        assertEquals(0, n.evaluate(""));
    }

    @Test
    void withOneVariable() {
        Expression e = new Mul(
                new Add(new Number(2), new Variable("x")),
                new Sub(new Div(new Number(2), new Number(4)), new Number(1)));
        assertEquals(-7.0, e.evaluate("x = 12"));

        Expression n = e.derivative("x");
        assertEquals(-0.5, n.evaluate("x = 12"));
    }

    void withTwoVariables() {
        Expression e = new Mul(
                new Add(new Number(2), new Variable("x")),
                new Sub(new Div(new Number(2), new Number(4)), new Variable("y")));
        assertEquals(-7.0, e.evaluate("x = 12; y = 1"));

        Expression n = e.derivative("x");
        assertEquals(-0.5, n.evaluate("x = 12; y = 1"));

        n = e.derivative("y");
        assertEquals(-1, n.evaluate("x = 12; y = 1"));
    }
}
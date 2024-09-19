package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquationsTest {

    @Test
    void main() {
        Expression e = new Mul(
                new Add(new Number(2), new Variable("x")),
                new Sub(new Div(new Number(2), new Number(4)), new Number(1)));
        assertEquals(-7.0, e.evaluate("x = 12"));

        Expression n = e.derivative("x");
        assertEquals(-0.5, n.evaluate("x = 12"));
    }
}
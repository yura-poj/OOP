package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class ParserTest {

    @Test
    void parse() {
        Parser parser = new Parser();
        String str = "((3 * (82 - 80)) + (2 / x))";
        Expression expression = parser.parse(str);
        assertEquals(7, expression.evaluate("x = 2"));
    }

    @Test
    void parse2() {
        Parser parser = new Parser();
        String str = "1 * 82 - 40 * 2 + x";
        Expression expression = parser.parse(str);
        assertEquals(4, expression.evaluate("x = 2"));
        assertEquals("(((1.0 * 82.0) - (40.0 * 2.0)) + x)", expression.toString());
    }
}
package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



class ParserTest {

    @Test
    void parse() {
        Parser parser = new Parser();
        String str = "((1 * (82 - 80)) + (2 + x))";
        Expression expression = parser.parse(str);
        assertEquals(6, expression.evaluate("x = 2"));
        assertEquals(str, expression.toString());
    }
}
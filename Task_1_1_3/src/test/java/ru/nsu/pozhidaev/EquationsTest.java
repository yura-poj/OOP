package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;



class EquationsTest {
    Equations eq = new Equations();

    @Test
    void equations() {
        String simulatedInput = "((1 * (82 - 80)) + (2 + x))\nx=2\nexit";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Equations.equations();
        assertTrue(outContent.toString().contains("6"));
    }
}
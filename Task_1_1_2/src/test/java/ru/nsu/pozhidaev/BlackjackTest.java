package ru.nsu.pozhidaev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlackjackTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    Blackjack blackjack;

    @BeforeEach
    void setUp() {
        blackjack = new Blackjack();
        outContent = new ByteArrayOutputStream();
    }

    @AfterEach
    void tearDown() {
        InputStream originalIn = System.in;
        System.setIn(originalIn);
    }

    @Test
    void wrongInput() {
        String simulatedInput = "aaa";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertThrows(java.util.NoSuchElementException.class, () -> {
            Blackjack.main(new String[]{});
        });

        assertTrue(outContent.toString().contains("Invalid input. Please enter a number."));
    }

    @Test
    void wrongNumberInput() {
        String simulatedInput = "111";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertThrows(java.util.NoSuchElementException.class, () -> {
            Blackjack.main(new String[]{});
        });

        assertTrue(outContent.toString().contains("Invalid input. Please enter '1', '0', or '-1'."));
    }
}
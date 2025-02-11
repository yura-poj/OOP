package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class BakeryTest {

    Bakery bakery;
    @BeforeEach
    void setUp() {
        bakery = new Bakery(new int[] {2,2}, new int[] {2,2}, 3);
        bakery.start();
    }

    @Test
    void order() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        bakery.order();
        sleep(5000);
        bakery.close();

        assertTrue(outContent.toString().contains("processing"));
        assertTrue(outContent.toString().contains("cooking"));
        assertTrue(outContent.toString().contains("cooked"));
        assertTrue(outContent.toString().contains("delivering"));
        assertTrue(outContent.toString().contains("delivered"));
    }

    @Test
    void ManyOrders() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        int numberOfOrders = 12;
        for(int i = 0; i < numberOfOrders; i++){
            bakery.order();
        }

        sleep(10000);
        bakery.close();

        assertTrue(outContent.toString().contains("Status of order №8 is: delivered"));
        assertTrue(outContent.toString().contains("Status of order №9 is: delivered"));
        assertTrue(outContent.toString().contains("Status of order №10 is: delivered"));
        assertTrue(outContent.toString().contains("Status of order №11 is: delivered"));
        assertTrue(outContent.toString().contains("Status of order №12 is: delivered"));
    }

    @Test
    void close() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        int numberOfOrders = 5;
        for (int i = 0; i < numberOfOrders; i++) {
            bakery.order();
        }
        sleep(2500);
        bakery.close();

        assertTrue(outContent.toString().contains("Status of order №3 is: delivered"));
        assertTrue(outContent.toString().contains("Status of order №4 is: delivered"));
        assertFalse(outContent.toString().contains("Status of order №5 is: delivered"));
    }
}
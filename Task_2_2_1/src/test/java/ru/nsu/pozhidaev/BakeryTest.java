package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BakeryTest {

    Bakery bakery;

    @BeforeEach
    void setUp() {
        bakery = new Bakery("src/test/resources/bakeryConfig.json");
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
    void manyOrders() throws InterruptedException {
        int numberOfOrders = 12;
        Order[] orders = new Order[numberOfOrders];
        for (int i = 0; i < numberOfOrders; i++) {
            orders[i] = bakery.order();
        }

        sleep(11000);
        bakery.close();
        assertEquals(orders[8].getStatus(), Status.DELIVERED);
        assertEquals(orders[9].getStatus(), Status.DELIVERED);
        assertEquals(orders[10].getStatus(), Status.DELIVERED);
        assertEquals(orders[11].getStatus(), Status.DELIVERED);
    }

    @Test
    void close() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        int numberOfOrders = 5;
        Order[] orders = new Order[numberOfOrders];
        for (int i = 0; i < numberOfOrders; i++) {
            orders[i] = bakery.order();
        }
        sleep(2500);
        bakery.close();

        assertEquals(orders[1].getStatus(), Status.DELIVERED);
        assertEquals(orders[2].getStatus(), Status.DELIVERED);
        assertEquals(orders[3].getStatus(), Status.DELIVERED);
        assertNotEquals(orders[4].getStatus(), Status.DELIVERED);
    }
}
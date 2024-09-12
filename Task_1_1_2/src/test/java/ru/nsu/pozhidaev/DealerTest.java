package ru.nsu.pozhidaev;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DealerTest {
    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        dealer = new Dealer(deck);
        dealer.setScore(2);
    }

    @Test
    void getNumberOpenedCards() {
        assertEquals(0, dealer.getNumberOpenedCards());
    }

    @Test
    void action() {
        int points = dealer.getPoints();
        dealer.action();
        int newPoints = dealer.getPoints();
        assertTrue(newPoints >= points);

        while (points <= 17) {
            dealer.action();
            newPoints = dealer.getPoints();
            assertTrue(newPoints >= points);
            points = newPoints;
        }
        dealer.action();
        assertEquals(points, dealer.getPoints());
    }

    @Test
    void testShowCards() {
        assertTimeout(ofMillis(10), () -> dealer.showCards());
    }

    @Test
    void showHiddenCards() {
        assertTimeout(ofMillis(10), () -> dealer.showHiddenCards());
    }

    @Test
    void testNewGame() {
        dealer.newGame();
        assertNull(dealer.getCurrentCard());
        assertEquals(0, dealer.getPoints());
        assertEquals(0, dealer.getNumberOpenedCards());
        assertFalse(dealer.isStop());
    }
}

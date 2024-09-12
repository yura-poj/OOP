package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest extends Player {
    private Dealer dealer;
    private Deck deck;

    /**
     * init.
     *
     * @param deck class deck.
     */
    public DealerTest(Deck deck) {
        super(deck);
    }

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
        int points = 0;
        int newPoints = 0;
        while (points <= 17) {
            dealer.action();
            newPoints = dealer.getPoints();
            assertFalse(newPoints < points);
            points = newPoints;
        }
        newPoints = dealer.getPoints();
        assertEquals(newPoints, points);
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
        assertNotNull(dealer.getCurrentCard());
        assertEquals(0, dealer.getPoints());
        assertEquals(0, dealer.getNumberOpenedCards());
        assertTrue(dealer.isStop());
    }
}
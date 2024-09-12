package ru.nsu.pozhidaev;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class UserTest {

    User user;
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        user = new User(deck);
        user.setScore(2);
    }

    @Test
    void testGetScore() {
        assertEquals(user.getScore(), 2);
    }

    @Test
    void testGetPoints() {
        assertEquals(0, user.getPoints());
    }

    @Test
    void testGetCurrentCard() {
        assertNull(user.getCurrentCard());
        user.getCard();
        assertNotNull(user.getCurrentCard());
    }

    @Test
    void testIsStop() {
        assertFalse(user.isStop());
    }

    @Test
    void testSetScore() {
        user.setScore(4);
        assertEquals(4, user.getScore());
    }

    @Test
    void testSetStop() {
        user.setStop(true);
        assertTrue(user.isStop());
    }

    @Test
    void testGetCard() {
        int pointsBefore = user.getPoints();
        Card beforeCard = user.getCurrentCard();
        user.getCard();

        assertTrue(pointsBefore <= user.getPoints());
        assertNotEquals(beforeCard, user.getCurrentCard());
    }

    @Test
    void testNewGame() {
        user.newGame();
        assertNull(user.getCurrentCard());
        assertEquals(0, user.getPoints());
        assertFalse(user.isStop());
    }

    @Test
    void testShowCards() {
        assertTimeout(ofMillis(10), () -> user.showCards());
    }
}
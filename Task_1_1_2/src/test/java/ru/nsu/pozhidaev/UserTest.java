package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends Player {

    User user;
    Deck deck;
    /**
     * init.
     *
     * @param deck class deck.
     */
    public UserTest(Deck deck) {
        super(deck);
    }

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
        assertTrue(user.isStop());
    }

    @Test
    void testSetScore() {
        user.setScore(4);
        assertEquals(4, user.getScore());
    }

    @Test
    void testSetStop() {
        user.setStop(true);
        assertFalse(user.isStop());
    }

    @Test
    void testGetCard() {
    }

    @Test
    void testNewGame() {
    }

    @Test
    void testShowCards() {
    }
}
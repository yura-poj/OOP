package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class CardTest {
    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card("Queen", "Diamonds");
    }

    @Test
    void getPoints() {
        Assertions.assertEquals(10, card.getPoints());
    }

    @Test
    void testToString() {
        Assertions.assertEquals("Queen of Diamonds(10)", card.toString());
    }
}
package ru.nsu.pozhidaev;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckTest {

    Deck deck;
    Card card;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void give_card() {
        int numberNeededCards = 20;
        HashSet<Object> generatedCards = new HashSet<>();

        for (int i = 0; i < numberNeededCards; i++) {
            card = deck.give_card();
            card.toString();
            Assertions.assertFalse(generatedCards.contains(card.toString()));
            generatedCards.add(card.toString());
        }

        Assertions.assertEquals(numberNeededCards, generatedCards.size());

    }

    @Test
    void newGame() {
        assertTimeout(ofMillis(10), () -> deck.give_card());
    }
}
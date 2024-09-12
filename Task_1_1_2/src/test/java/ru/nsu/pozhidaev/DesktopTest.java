package ru.nsu.pozhidaev;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class DesktopTest {

    Desktop desktop;
    User user;
    Dealer dealer;
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        user = new User(deck);
        dealer = new Dealer(deck);
        desktop = new Desktop(user, dealer, deck);

    }

    @Test
    void userWin() {
        int userScore = user.getScore();
        int points = 0;
        while (points <= 21) {
            dealer.getCard();
            points = dealer.getPoints();
        }

        String simulatedInput = "-1";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        desktop.startGame();
        assertTrue(user.getScore() > userScore);
    }

    @Test
    void dealerWin(){
        int dealerScore = dealer.getScore();
        int points = 0;
        while (points <= 21) {
            user.getCard();
            points = user.getPoints();
        }
        String simulatedInput = "-1";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
        desktop.startGame();
        assertTrue(dealer.getScore() > dealerScore);
    }

    @AfterEach
    void tearDown() {
        InputStream originalIn = System.in;
        System.setIn(originalIn);
    }
}

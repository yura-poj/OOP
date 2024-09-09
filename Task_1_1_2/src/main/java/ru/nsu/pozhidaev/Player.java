package ru.nsu.pozhidaev;

import java.util.*;

public class Player {
    Deck deck;
    int score = 0;
    int points = 0;
    Card current_card;
    List<Card> cards = new ArrayList<Card>();
    boolean stop = false;

    /**
     * @param deck
     */
    public Player(Deck deck) {
        this.deck = deck;
    }

    /**
     *
     */
    public void getCard() {
        current_card = deck.give_card();
        points += current_card.points;
        cards.add(current_card);
    }

    /**
     *
     */
    public void newGame() {
        points = 0;
        stop = false;
        cards.clear();
    }

    /**
     *
     */
    public void showCards() {
        System.out.print("Your cards: [");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(deck.getCardInfo(cards.get(i)));

            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

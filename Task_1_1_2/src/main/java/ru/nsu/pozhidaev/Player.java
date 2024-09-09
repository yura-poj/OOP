package ru.nsu.pozhidaev;

import java.util.*;

public class Player {
    Deck deck;
    int score = 0;
    int points = 0;
    String name;
    Card current_card;
    List<Card> cards = new ArrayList<Card>();

    public Player(Deck deck) {
        this.deck = deck;
    }

    public void getCard() {
        current_card =  deck.give_card();
        points += current_card.points;
        cards.add(current_card);
    }

    public void newGame() {
        points = 0;
    }

    public String showCard(){
        return name + " get " + current_card.getInfo();
    }
}

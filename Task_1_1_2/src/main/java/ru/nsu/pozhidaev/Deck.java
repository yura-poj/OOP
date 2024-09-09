package ru.nsu.pozhidaev;

public class Deck {
    public Card give_card(){
        int points = 0;
        int suit = 0;
        return new Card(points,suit);
    }
}

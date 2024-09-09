package ru.nsu.pozhidaev;

public class Card {
    int id;
    int points;
    int suit;

    /**
     * @param id
     * @param points
     * @param suit
     */
    public Card(int id, int points, int suit) {
        this.id = id;
        this.points = points;
        this.suit = suit;
    }
}

package ru.nsu.pozhidaev;

public class Card {
 
    private String name;
    private int points;
    private String suit;

    /**
     * @param name name of the card.
     * @param points points that will be added with card.
     * @param suit suit of the card.
     */
    public Card(String name, int points, String suit) {
        this.name = name;
        this.points = points;
        this.suit = suit;
    }

    /**
     *
     * @return points of the card.
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @return ready string like: Queen of Diamonds (10).
     */
    public String toString() {
        return name + " of " + suit + "(" + points + ")";
    }
}

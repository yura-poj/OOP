package ru.nsu.pozhidaev;

/**
 * class describing card and giving info about it.
 */
public class Card {

    private final String name;
    private final int points;
    private final String suit;

    /**
     * for generating
     *
     * @param name   name of the card.
     * @param points points that will be added with card.
     * @param suit   suit of the card.
     */
    public Card(String name, int points, String suit) {
        this.name = name;
        this.points = points;
        this.suit = suit;
    }

    /**
     * getter.
     *
     * @return points of the card.
     */
    public int getPoints() {
        return points;
    }

    /**
     * collect info about card in one string.
     *
     * @return ready string like: Queen of Diamonds (10).
     */
    public String toString() {
        return name + " of " + suit + "(" + points + ")";
    }
}

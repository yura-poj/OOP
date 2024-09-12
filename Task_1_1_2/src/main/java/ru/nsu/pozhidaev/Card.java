package ru.nsu.pozhidaev;

/**
 * class describing card and giving info about it.
 */
public class Card {

    private final int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private final String name;
    private final int id;
    private final String suit;

    /**
     * for generating.
     *
     * @param name name of the card.
     * @param id   points that will be added with card.
     * @param suit suit of the card.
     */
    public Card(String name, int id, String suit) {
        this.name = name;
        this.id = id;
        this.suit = suit;
    }

    /**
     * getter.
     *
     * @return points of the card.
     */
    public int getPoints() {
        return points[id];
    }

    /**
     * collect info about card in one string.
     *
     * @return ready string like: Queen of Diamonds (10).
     */
    public String toString() {
        return name + " of " + suit + "(" + getPoints() + ")";
    }
}

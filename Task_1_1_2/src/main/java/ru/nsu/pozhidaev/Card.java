package ru.nsu.pozhidaev;

import java.util.Map;

/**
 * class describing card and giving info about it.
 */
public class Card {

    private final Map<String, Integer> points = Map.ofEntries(
            Map.entry("2", 2),
            Map.entry("3", 3),
            Map.entry("4", 4),
            Map.entry("5", 5),
            Map.entry("6", 6),
            Map.entry("7", 7),
            Map.entry("8", 8),
            Map.entry("9", 9),
            Map.entry("10", 10),
            Map.entry("Jack", 10),
            Map.entry("Queen", 10),
            Map.entry("King", 10),
            Map.entry("Ace", 11)
    );

    private final String name;
    private final String suit;

    /**
     * for generating.
     *
     * @param name name of the card.
     * @param suit suit of the card.
     */
    public Card(String name, String suit) {
        this.name = name;
        this.suit = suit;
    }

    /**
     * getter.
     *
     * @return points of the card.
     */
    public int getPoints() {
        return points.get(name);
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

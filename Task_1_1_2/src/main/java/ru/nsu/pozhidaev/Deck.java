package ru.nsu.pozhidaev;

/**
 * deck of the cards have 52 unique cards from 13 names and 4 suits.
 * can generate card for players.
 */
public class Deck {

    public static final int NAMES_PER_DECK = 13;
    public static final int SUITS_PER_DECK = 4;
    private final String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    private final String[] suits = {"Diamonds", "Hearts", "Clubs", "Spades"};
    private boolean[][] usedCards = new boolean[13][4];

    /**
     * generate randomly card and check was it already used or not.
     * we generate card till it wasn't used.
     *
     * @return Card that was generated.
     */
    public Card give_card() {
        int id = generateKey(12);
        int suit = generateKey(3);

        while (usedCards[id][suit]) {
            id = generateKey(12);
            suit = generateKey(3);
        }

        usedCards[id][suit] = true;

        return new Card(names[id], id, suits[suit]);
    }

    /**
     * clear array of used cards.
     */
    public void newGame() {
        for (int i = 0; i < NAMES_PER_DECK; i++) {
            for (int j = 0; j < SUITS_PER_DECK; j++) {
                usedCards[i][j] = false;
            }
        }
    }

    /**
     * generate keys for new card.
     *
     * @param number max possible number.
     * @return randomly generated number.
     */


    private int generateKey(int number) {
        return (int) (Math.random() * number);
    }
}

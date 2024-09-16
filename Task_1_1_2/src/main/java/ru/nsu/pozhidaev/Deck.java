package ru.nsu.pozhidaev;

/**
 * deck of the cards have 52 unique cards from 13 names and 4 suits.
 * can generate card for players.
 */
public class Deck {

    private static final String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    private static final String[] suits = {"Diamonds", "Hearts", "Clubs", "Spades"};
    private boolean[][] usedCards = new boolean[names.length][suits.length];

    /**
     * generate randomly card and check was it already used or not.
     * we generate card till it wasn't used.
     *
     * @return Card that was generated.
     */
    public Card give_card() {
        int id = generateKey(names.length - 1);
        int suit = generateKey(suits.length - 1);

        while (usedCards[id][suit]) {
            id = generateKey(names.length - 1);
            suit = generateKey(suits.length - 1);
        }

        usedCards[id][suit] = true;

        return new Card(names[id], suits[suit]);
    }

    /**
     * clear array of used cards.
     */
    public void newGame() {
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < suits.length; j++) {
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

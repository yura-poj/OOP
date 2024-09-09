package ru.nsu.pozhidaev;

public class Deck {

    String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    boolean[][] usedCards = new boolean[13][4];
    String[] suits = {"Diamonds", "Hearts", "Clubs", "Spades"};

    /**
     *
     * @return
     */
    public Card give_card() {
        int id = generateKey(12);
        int suit = generateKey(3);

        while (usedCards[id][suit]) {
            id = generateKey(12);
            suit = generateKey(3);
        }

        usedCards[id][suit] = true;

        int score = points[id];
        return new Card(id, score, suit);
    }

    /**
     *
     * @param card
     * @return
     */
    public String getCardInfo(Card card) {
        return names[card.id] + " of " + suits[card.suit] + "(" + points[card.id] + ")";
    }

    /**
     *
     */
    public void newGame() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 3; j++) {
                usedCards[i][j] = false;
            }
        }
    }

    /**
     *
     * @param number
     * @return
     */
    private int generateKey(int number) {
        return (int) (Math.random() * number);
    }
}

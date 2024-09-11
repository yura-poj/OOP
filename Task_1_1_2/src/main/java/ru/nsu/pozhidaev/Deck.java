package ru.nsu.pozhidaev;

public class Deck {

    private String[] names = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King", "Ace"};
    private int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private boolean[][] usedCards = new boolean[13][4];
    private String[] suits = {"Diamonds", "Hearts", "Clubs", "Spades"};

    /**
     * generate randomly card and check was it already used or not.
     * we generate card till it wasn't used.
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

        int score = points[id];
        return new Card(names[id], score, suits[suit]);
    }

    /**
     * clear array of used cards.
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
     * @param number max possible number.
     * @return randomly generated number.
     */
    private int generateKey(int number) {
        return (int) (Math.random() * number);
    }
}

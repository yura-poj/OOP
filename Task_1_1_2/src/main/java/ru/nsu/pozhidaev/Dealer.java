package ru.nsu.pozhidaev;

/**
 * subclass of player, opponent for user.
 */
public class Dealer extends Player {

    private static final int maxPointsNumber = 17;
    private int numberOpenedCards = 0;

    /**
     * getter.
     *
     * @return number of opened cards.
     */
    public int getNumberOpenedCards() {
        return numberOpenedCards;
    }


    /**
     * for generating.
     *
     * @param deck class of deck of cards.
     */
    public Dealer(Deck deck) {
        super(deck);
    }

    /**
     * add card if points less or equal than maxPointsNumber,
     * in other situation touch flag stop.
     */
    public void action() {
        if (points <= maxPointsNumber) {
            getCard();
        } else {
            stop = true;
        }
    }

    /**
     * show visible cards by card.toString
     * and print that possible have hidden cards.
     * we compare number of opened cards - 1 and i, because first card is opened permanently.
     */
    public void showCards() {
        System.out.print("Dealer's cards: [");
        for (int i = 0; i < cards.size(); i++) {
            if (numberOpenedCards - 1 >= i) {
                System.out.print(cards.get(i));
            } else {
                System.out.print("<hidden card>");
            }

            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * open card by card till his cards will be gone.
     * for that call show cards, before that add 1 to numberOpenedCards.
     * in the end make numberOpenedCards the maximum value as possible
     * to avoid hidden cards anymore.
     */
    public void showHiddenCards() {
        stop = true;
        for (int i = 1; i < cards.size(); i++) {
            numberOpenedCards++;
            System.out.println("Dealer open card: " + cards.get(i));
            showCards();
        }
        numberOpenedCards = Integer.MAX_VALUE;
    }

    /**
     * change number of opened card to zero.
     */
    @Override
    public void newGame() {
        super.newGame();
        numberOpenedCards = 0;
    }
}

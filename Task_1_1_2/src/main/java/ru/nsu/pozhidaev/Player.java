package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.List;

/**
 * class that imitate action and state of real player.
 * such action as: get card from deck, stop getting new card,
 * remove old cards and start new game.
 * such states as: score of wins, sum of points of the cards.
 */
public class Player {

    protected final Deck deck;
    protected int score = 0;
    protected int points = 0;
    protected Card currentCard;
    protected List<Card> cards = new ArrayList<Card>();
    protected boolean stop = false;

    /**
     * getter.
     *
     * @return score of wins.
     */
    public int getScore() {
        return score;
    }

    /**
     * getter.
     *
     * @return sum of points of all cards.
     */
    public int getPoints() {
        return points;
    }

    /**
     * getter.
     *
     * @return card that player get recently.
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * getter.
     *
     * @return is user stopped or not.
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * setter.
     *
     * @param score of wins.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * setter.
     *
     * @param stop getting more cards.
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * init.
     *
     * @param deck class deck.
     */
    public Player(Deck deck) {
        this.deck = deck;
    }

    /**
     * get card from the deck and add her points to total points.
     * set card as current and add to player's cards
     */
    public void getCard() {
        currentCard = deck.give_card();
        points += currentCard.getPoints();
        cards.add(currentCard);
    }

    /**
     * set points as zero, stop as false and clear cards for the new game.
     */
    public void newGame() {
        points = 0;
        stop = false;
        cards.clear();
        currentCard = null;
    }

    /**
     * show all cards by toString that player have in list cards.
     */
    public void showCards() {
        System.out.print("Your cards: [");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i));

            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

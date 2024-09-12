package ru.nsu.pozhidaev;

/**
 * The main class.
 */
public class Blackjack {
    static Deck deck;
    static User user;
    static Dealer dealer;
    static Desktop desktop;

    /**
     * function initialize all classes for the game and start it.
     *
     * @param args args of command line.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Blackjack!");

        deck = new Deck();
        user = new User(deck);
        dealer = new Dealer(deck);
        desktop = new Desktop(user, dealer, deck);
        desktop.startGame();
    }
}
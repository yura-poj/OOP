package ru.nsu.pozhidaev;

/**
 * The main class.
 */
public class Blackjack {

    /**
     * function initialize all classes for the game and start it.
     *
     * @param args args of command line.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Blackjack!");

        Deck deck = new Deck();
        User user = new User(deck);
        Dealer dealer = new Dealer(deck);
        Desktop desktop = new Desktop(user, dealer, deck);
        desktop.startGame();
    }
}
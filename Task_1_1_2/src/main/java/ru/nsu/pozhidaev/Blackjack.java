package ru.nsu.pozhidaev;


public class Blackjack {
    /**
     *
     * @param args
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
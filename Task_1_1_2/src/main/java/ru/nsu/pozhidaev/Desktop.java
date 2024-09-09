package ru.nsu.pozhidaev;

import java.util.*;

public class Desktop {
    User user;
    Dealer dealer;
    Deck deck;
    int round = 1;
    boolean newRound = true;
    boolean stop = false;

    /**
     * @param user
     * @param dealer
     * @param deck
     */
    public Desktop(User user, Dealer dealer, Deck deck) {
        this.user = user;
        this.dealer = dealer;
        this.deck = deck;
    }

    /**
     *
     */
    public void startGame() {
        while (!stop) {
            if (newRound) {
                System.out.println("-------------------------------------------");
                System.out.println("Round: " + round);
                for (int i = 0; i < 2; i++) {
                    user.getCard();
                    dealer.getCard();
                }
                statistics();
                newRound = false;
            }
            step();
        }
    }

    /**
     *
     */
    public void step() {
        dealer.action();
        if (!user.stop) {
            userAction();
            if (!dealer.stop) {
                System.out.println("Dealer open card: " + deck.getCardInfo(dealer.current_card));
            }
        }
        if (!dealer.stop) {
            System.out.println("Dealer got hidden card");
        }
        statistics();
        check();
    }

    /**
     *
     */
    public void userAction() {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter '1' to get card, '0' to stop and '-1' to exit");
        while (true) {
            int chose = scanner.nextInt();
            if (chose == 0) {
                userStopped();
                break;
            } else if (chose == 1) {
                user.getCard();
                System.out.println("You open card: " + deck.getCardInfo(user.current_card));
                break;
            } else if (chose == -1) {
                stop = true;
                break;
            }
        }

    }

    /**
     *
     */
    public void check() {
        if (user.points > 21 && dealer.points > 21) {
            System.out.println("Draw");
        } else if (user.points > 21) {
            System.out.println("You lose");
            dealer.score++;
        } else if (dealer.points > 21) {
            System.out.println("You win");
            user.score++;
        } else if (dealer.stop && user.stop) {
            if (user.points == dealer.points) {
                System.out.println("Draw");
            } else if (user.points > dealer.points) {
                System.out.println("You lose");
                dealer.score++;
            } else {
                System.out.println("You win");
                user.score++;
            }
        }
        if (dealer.stop && user.stop || dealer.points > 21 || user.points > 21) {
            finish();
        }
    }

    /**
     *
     */
    public void finish() {
        System.out.println("Score You: " + user.score + " | Dealer: " + dealer.score);
        newRound = true;
        round++;
        dealer.newGame();
        user.newGame();
        deck.newGame();
    }

    /**
     *
     */
    private void userStopped() {
        user.stop = true;
        dealer.showHiddenCards();
    }

    /**
     *
     */
    private void statistics() {
        int numberOpenedCards = 0;
        if (user.stop) {
            numberOpenedCards = dealer.cards.size();
        }
        user.showCards();
        dealer.showCards(numberOpenedCards);
    }

}

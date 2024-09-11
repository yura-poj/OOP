package ru.nsu.pozhidaev;

import java.util.*;

public class Desktop {
    private final User user;
    private final Dealer dealer;
    private final Deck deck;
    private int round = 1;
    private boolean newRound = true;
    private boolean stop = false;

    /**
     * @param user   class user.
     * @param dealer class dealer.
     * @param deck   class deck.
     */
    public Desktop(User user, Dealer dealer, Deck deck) {
        this.user = user;
        this.dealer = dealer;
        this.deck = deck;
    }

    /**
     * while user do not stop the game function do:
     * print round, set up cards for new game, print them
     * and call step.
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
     * firstly dealer make his action
     * and if user did not stop already call his action.
     * print card that get dealer.
     * call statistics and check of step result.
     */
    private void step() {
        dealer.action();
        if (!user.stop) {
            userAction();
            if (!dealer.stop) {
                System.out.println("Dealer open card: " + dealer.getCurrentCard().toString());
            }
        }
        if (!dealer.stop) {
            System.out.println("Dealer got hidden card");
        }
        statistics();
        checkStepResult();
    }

    /**
     * ask user to enter command he would like to do,
     * stop adding cards, get the card or stop the game.
     * if user enter invalid command, ask hin again.
     */
    private void userAction() {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter '1' to get card, '0' to stop and '-1' to exit");
        boolean continues = true;
        while (continues) {
            try {
                int userInput = scanner.nextInt();
                switch (userInput) {
                    case 0:
                        userStopped();
                        continues = false;
                        break;
                    case 1:
                        user.getCard();
                        System.out.println("You open card: " + user.getCurrentCard().toString());
                        continues = false;
                        break;
                    case -1:
                        stop = true;
                        continues = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter '1', '0', or '-1'.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    /**
     * check if players get more than 21 points and if they both stop adding cards.
     * print result.
     * call finish.
     */
    private void checkStepResult() {
        int gameResult = 0;
        if (user.getPoints() > 21 && dealer.getPoints() > 21) {
            gameResult = 0;
        } else if (user.getPoints() > 21) {
            gameResult = -1;
            dealer.setScore(dealer.getScore() + 1);
        } else if (dealer.getPoints() > 21) {
            gameResult = 1;
            user.setScore(user.getScore() + 1);
        } else if (dealer.isStop() && user.isStop()) {
            if (user.getPoints() == dealer.getPoints()) {
                gameResult = 0;
            } else if (user.getPoints() > dealer.getPoints()) {
                gameResult = -1;
                dealer.setScore(dealer.getScore() + 1);
            } else {
                gameResult = 1;
                user.setScore(user.getScore() + 1);
            }
        }
        if (dealer.isStop() && user.isStop() || dealer.getPoints() > 21 || user.getPoints() > 21) {
            finish(gameResult);
        }
    }

    /**
     * if dealer didn't show his card yet, we call showHiddenCards.
     * print result of the round.
     * increase round, set flag of new round.
     * prepare classes for the new game by class.newGame.
     */
    private void finish(int gameResult) {
        if (!dealer.isStop() && dealer.getNumberOpenedCards() != Integer.MAX_VALUE) {
            dealer.showHiddenCards();
        }
        switch (gameResult) {
            case (0):
                System.out.println("Draw");
                break;
            case (1):
                System.out.println("You win");
                break;
            case (-1):
                System.out.println("You lose");
                break;
        }
        System.out.println("Score You: " + user.getScore() + " | Dealer: " + dealer.getScore());
        newRound = true;
        round++;
        dealer.newGame();
        user.newGame();
        deck.newGame();
    }

    /**
     * set flag stop of user = true and call showHiddenCards for dealer.
     */
    private void userStopped() {
        user.setStop(true);
        dealer.showHiddenCards();
    }

    /**
     * print cards of user and dealer.
     */
    private void statistics() {
        user.showCards();
        dealer.showCards();
        System.out.println("<---->");
    }

}

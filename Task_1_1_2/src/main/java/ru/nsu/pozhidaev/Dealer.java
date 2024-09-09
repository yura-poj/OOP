package ru.nsu.pozhidaev;

public class Dealer extends Player {

    public Dealer(Deck deck) {
        super(deck);
    }

    public void action() {
        if (points <= 17) {
            getCard();
        } else {
            stop = true;
        }
    }

    public void showCards(int numberOpenedCards) {
        System.out.print("Dealer's cards: [");
        for (int i = 0; i < cards.size(); i++) {
            if (numberOpenedCards >= i) {
                System.out.print(deck.getCardInfo(cards.get(i)));
            } else {
                System.out.print("<hidden card>");
            }

            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public void showHiddenCards() {
        stop = true;
        for (int i = 1; i < cards.size(); i++) {
            System.out.println("Dealer open card: " + deck.getCardInfo(cards.get(i)));
            showCards(i);
        }
    }
}

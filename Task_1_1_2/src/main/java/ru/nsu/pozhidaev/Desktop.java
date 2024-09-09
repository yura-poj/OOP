package ru.nsu.pozhidaev;

public class Desktop {
    User user;
    Dealer dealer;

    public Desktop(User user, Dealer dealer){
        this.user = user;
        this.dealer = dealer;
    }

    public void step(){
        if(! user.stop){
            userAction();
        }
        dealer.getCard();
    }

    public void userAction() {
        int x = 0;
        if(x == 0){
        } else{
            user.getCard();
            System.out.println("You open card %s (%i)",
                user.current_card.getInfo(), user.current_card.points);
        }
    }

    private void userStopped(){
        user.stop = true;
        dealer.showHiddenCards();
    }
}

package ru.nsu.pozhidaev;

public class User extends Player {
    boolean stop = false;

    public User(Deck deck, String name) {
        super(deck);
        this.name = name;
    }
}

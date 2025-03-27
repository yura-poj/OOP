package ru.nsu.pozhidaev;

import java.util.Collections;

/**
 * A simple implementation of the SnakeBot that moves randomly.
 * This bot makes no attempt to find treats or avoid obstacles beyond
 * the basic collision avoidance inherited from SnakeBot.
 */
public class SnakeBotStupid extends SnakeBot {

    /**
     * Constructs a new stupid snake bot with a reference to the game instance.
     *
     * @param game the game instance this bot belongs to
     */
    public SnakeBotStupid(SnakeGame game) {
        super(game);
    }

    /**
     * Implements the thinking strategy for the stupid bot.
     * Simply shuffles the available directions randomly,
     * making no intelligent decisions about movement.
     */
    @Override
    void think() {
        Collections.shuffle(sortedDirections);
    }
}

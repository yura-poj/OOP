package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SnakeBotStupid extends SnakeBot {

    public SnakeBotStupid(SnakeGame game) {
        super(game);
    }

    /**
     *
     */
    @Override
    void think() {
        Collections.shuffle(sortedDirections);
    }
}

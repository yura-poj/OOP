package ru.nsu.pozhidaev;

import lombok.Setter;

import java.util.ArrayList;

public class SnakeBot extends Snake {
    final static int numberDirections = 4;

    Action[] sortedDirections;
    @Setter
    Treat closestTreat;
    @Setter
    ArrayList<Action> resolvedDirections;

    public SnakeBot() {
        super();
        sortedDirections = new Action[numberDirections];
    }

    @Override
    public void move() {
        think();
        checkForCollision();
        super.move();
    }

    private void checkForCollision() {

    }

    private void think() {
        if( closestTreat.getCoordinateX() > getHead().getCoordinateX()) {
            sortedDirections[0] = Action.LEFT;
            sortedDirections[4] = Action.RIGHT;
        }
        if(closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
            sortedDirections[1] = Action.UP;
            sortedDirections[3] = Action.DOWN;
        }
    }
}

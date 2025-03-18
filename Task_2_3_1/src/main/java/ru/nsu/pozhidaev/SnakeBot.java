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
        sortedDirections[0] = Action.UP;
        sortedDirections[1] = Action.UP;
        sortedDirections[2] = Action.UP;
        sortedDirections[3] = Action.UP;
    }

    @Override
    public void move() {
        think();
        checkForCollision();
        super.move();
    }

    private void checkForCollision() {
        for (Action action : sortedDirections) {
            if (resolvedDirections.contains(action)) {
                setDirection(action);
            }
        }
    }

    private void think() {
        if(closestTreat == null) {
            return;
        }
        if( closestTreat.getCoordinateX() > getHead().getCoordinateX()) {
            sortedDirections[0] = Action.LEFT;
            sortedDirections[3] = Action.RIGHT;
        } else {
            sortedDirections[0] = Action.RIGHT;
            sortedDirections[3] = Action.LEFT;
        }
        if(closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
            sortedDirections[1] = Action.UP;
            sortedDirections[2] = Action.DOWN;
        } else {
            sortedDirections[1] = Action.DOWN;
            sortedDirections[2] = Action.UP;
        }
    }
}

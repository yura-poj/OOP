package ru.nsu.pozhidaev;

import lombok.Setter;

import java.util.ArrayList;

abstract class SnakeBot extends Snake {
    final static int numberDirections = 4;

    ArrayList<Action> sortedDirections;
    @Setter
    Treat closestTreat;
    ArrayList<Action> resolvedDirections;
    SnakeGame game;

    public SnakeBot(SnakeGame game) {
        super(game);
        this.game = game;
        sortedDirections = new ArrayList<>();
        sortedDirections.add(Action.RIGHT);
        sortedDirections.add(Action.LEFT);
        sortedDirections.add(Action.DOWN);
        sortedDirections.add(Action.UP);
    }

    @Override
    public void move() {
        think();
        setUpResolvedDirections();
        checkForCollision();
        super.move();
    }

    private void checkForCollision() {
        for (Action action : sortedDirections) {
            if (resolvedDirections.contains(action)) {
                setDirection(action);
                break;
            }
        }
    }

    private void setUpResolvedDirections() {
        resolvedDirections = new ArrayList<>();

        int x = getHead().getCoordinateX();
        int y = getHead().getCoordinateY();

        if (!game.isCollision(new SnakePart(x + 1, y))) {
            resolvedDirections.add(Action.RIGHT);
        }
        if (!game.isCollision(new SnakePart(x - 1, y))) {
            resolvedDirections.add(Action.LEFT);
        }
        if (!game.isCollision(new SnakePart(x, y + 1))) {
            resolvedDirections.add(Action.DOWN);
        }
        if (!game.isCollision(new SnakePart(x, y - 1))) {
            resolvedDirections.add(Action.UP);
        }
    }

    abstract void think();


}

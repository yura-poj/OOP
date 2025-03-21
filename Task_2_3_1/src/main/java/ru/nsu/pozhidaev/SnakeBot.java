package ru.nsu.pozhidaev;

import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

public class SnakeBot extends Snake {
    final static int numberDirections = 4;

    Action[] sortedDirections;
    @Setter
    Treat closestTreat;
    ArrayList<Action> resolvedDirections;
    SnakeGame game;

    public SnakeBot(SnakeGame game) {
        super(game);
        this.game = game;
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
                break;
            }
        }
    }

    private void think() {
        if(closestTreat==null || !closestTreat.isBooked()) {
            setUpClosestTreat();
        }
        setUpResolvedDirections();

        if (closestTreat!=null) {
            sortDirections();
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

    private void setUpClosestTreat() {
        int closestSum;
            closestSum = Integer.MAX_VALUE;
            for (Treat treat : game.getTreats()) {
                if (treat.isBooked()) {
                    continue;
                }
                int x = treat.coordinateX - getHead().getCoordinateX();
                int y = treat.coordinateY - getHead().getCoordinateY();
                int sum = (int) Math.round(Math.sqrt(x * x + y * y));
                if (sum < closestSum) {
                    closestTreat = treat;
                    closestSum = sum;
                }
            }

            if(closestTreat != null) {
                closestTreat.setBooked(true);
            }
    }

    private void sortDirections() {
        if (closestTreat.getCoordinateX() == getHead().getCoordinateX()) {
            if(closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
                sortedDirections[0] = Action.DOWN;
                sortedDirections[3] = Action.UP;
            } else {
                sortedDirections[0] = Action.UP;
                sortedDirections[3] = Action.DOWN;
            }
            sortedDirections[1] = Action.LEFT;
            sortedDirections[2] = Action.RIGHT;
            return;
        }
        if(closestTreat.getCoordinateY() == getHead().getCoordinateY()) {
            if(closestTreat.getCoordinateX() > getHead().getCoordinateX()) {
                sortedDirections[0] = Action.RIGHT;
                sortedDirections[3] = Action.LEFT;
            } else {
                sortedDirections[0] = Action.LEFT;
                sortedDirections[3] = Action.RIGHT;
            }
            sortedDirections[1] = Action.UP;
            sortedDirections[2] = Action.DOWN;
            return;
        }

        if(closestTreat.getCoordinateX() > getHead().getCoordinateX() ){
            sortedDirections[1] = Action.LEFT;
            sortedDirections[2] = Action.RIGHT;
        } else {
            sortedDirections[1] = Action.RIGHT;
            sortedDirections[2] = Action.LEFT;
        }
        if(closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
            sortedDirections[0] = Action.DOWN;
            sortedDirections[3] = Action.UP;
        } else {
            sortedDirections[0] = Action.UP;
            sortedDirections[3] = Action.DOWN;
        }
    }
}

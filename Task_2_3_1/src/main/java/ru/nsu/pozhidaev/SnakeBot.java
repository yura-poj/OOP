package ru.nsu.pozhidaev;

import lombok.Setter;

import java.util.ArrayList;

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
        if (!(closestTreat != null && !closestTreat.isBooked())) {
            setUpClosestTreat();
        }
        setUpResolvedDirections();

        if (closestTreat!=null) {
            sortDirections();
        }
    }

    private void setUpResolvedDirections() {
        resolvedDirections = new ArrayList<>();
        SnakePart testHead = new SnakePart(0, 0);
        testHead.setCoordinateX(getHead().getCoordinateX() + 1);
        testHead.setCoordinateY(getHead().getCoordinateY());
        if (!game.isCollision(testHead)) {
            resolvedDirections.add(Action.RIGHT);
        }
        testHead.setCoordinateX(getHead().getCoordinateX() - 1);
        testHead.setCoordinateY(getHead().getCoordinateY());
        if (!game.isCollision(testHead)) {
            resolvedDirections.add(Action.LEFT);
        }

        testHead.setCoordinateX(getHead().getCoordinateX());
        testHead.setCoordinateY(getHead().getCoordinateY() + 1);
        if (!game.isCollision(testHead)) {
            resolvedDirections.add(Action.DOWN);
        }
        testHead.setCoordinateX(getHead().getCoordinateX());
        testHead.setCoordinateY(getHead().getCoordinateY() - 1);
        if (!game.isCollision(testHead)) {
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
                int sum = (int) Math.sqrt(Math.pow(treat.getCoordinateX(), 2) + Math.pow(treat.getCoordinateY(), 2));
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
            sortedDirections[1] = Action.LEFT;
            sortedDirections[2] = Action.RIGHT;
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

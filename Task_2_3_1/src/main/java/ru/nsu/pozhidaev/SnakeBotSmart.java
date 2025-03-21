package ru.nsu.pozhidaev;

import java.util.ArrayList;

public class SnakeBotSmart extends SnakeBot {

    public SnakeBotSmart(SnakeGame game) {
        super(game);
    }

    void think() {
        if(closestTreat==null || !closestTreat.isBooked()) {
            setUpClosestTreat();
        }

        if (closestTreat!=null) {
            sortDirections();
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
            if (closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
                sortedDirections.set(0, Action.DOWN);
                sortedDirections.set(3, Action.UP);
            } else {
                sortedDirections.set(0, Action.UP);
                sortedDirections.set(3, Action.DOWN);
            }
            sortedDirections.set(1, Action.LEFT);
            sortedDirections.set(2, Action.RIGHT);
            return;
        }
        if (closestTreat.getCoordinateY() == getHead().getCoordinateY()) {
            if (closestTreat.getCoordinateX() > getHead().getCoordinateX()) {
                sortedDirections.set(0, Action.RIGHT);
                sortedDirections.set(3, Action.LEFT);
            } else {
                sortedDirections.set(0, Action.LEFT);
                sortedDirections.set(3, Action.RIGHT);
            }
            sortedDirections.set(1, Action.UP);
            sortedDirections.set(2, Action.DOWN);
            return;
        }

        if (closestTreat.getCoordinateX() > getHead().getCoordinateX()) {
            sortedDirections.set(1, Action.LEFT);
            sortedDirections.set(2, Action.RIGHT);
        } else {
            sortedDirections.set(1, Action.RIGHT);
            sortedDirections.set(2, Action.LEFT);
        }
        if (closestTreat.getCoordinateY() > getHead().getCoordinateY()) {
            sortedDirections.set(0, Action.DOWN);
            sortedDirections.set(3, Action.UP);
        } else {
            sortedDirections.set(0, Action.UP);
            sortedDirections.set(3, Action.DOWN);
        }
    }
}

package ru.nsu.pozhidaev;

import java.util.ArrayList;

/**
 * Snake that can move, change direction, eat, bump into blocks, and grow.
 */
public class Snake {
    ArrayList<SnakePart> body = new ArrayList<>();
    private int moveOrdinate;
    private int moveAbscissa;
    private SnakePart previous;

    /**
     * Constructs a Snake with a starting position.
     *
     * @param startX the starting x-coordinate
     * @param startY the starting y-coordinate
     */
    public Snake(int startX, int startY) {
        for (int i = 0; i < 3; i++) {
            body.add(new SnakePart(startX + i, startY));
        }
        moveOrdinate = -1;
        moveAbscissa = 0;
        previous = new SnakePart(0, 0);
    }

    /**
     * Sets the direction of the snake based on the given action.
     *
     * @param action the action to change the direction
     */
    public void setDirection(Action action) {
        switch (action) {
            case DOWN:
                if (moveAbscissa==0) {
                    moveAbscissa = 1;
                    moveOrdinate = 0;
                }
                break;
            case UP:
                if (moveAbscissa==0) {
                    moveAbscissa = -1;
                    moveOrdinate = 0;
                }
                break;
            case LEFT:
                if (moveOrdinate==0) {
                    moveOrdinate = -1;
                    moveAbscissa = 0;
                }
                break;
            case RIGHT:
                if (moveOrdinate==0) {
                    moveOrdinate = 1;
                    moveAbscissa = 0;
                }
                break;
        }
    }

    /**
     * Moves the snake in the current direction.
     */
    public void move() {
        previous.setCoordinateX(body.get(body.size() - 1).getCoordinateX());
        previous.setCoordinateY(body.get(body.size() - 1).getCoordinateY());

        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setCoordinateX(body.get(i - 1).getCoordinateX());
            body.get(i).setCoordinateY(body.get(i - 1).getCoordinateY());
        }

        body.get(0).setCoordinateX(body.get(0).getCoordinateX() + moveOrdinate);
        body.get(0).setCoordinateY(body.get(0).getCoordinateY() + moveAbscissa);
    }

    /**
     * Returns the body of the snake.
     *
     * @return a list of snake parts
     */
    public ArrayList<SnakePart> getBody() {
        return new ArrayList<>(body);
    }

    /**
     * Returns the head of the snake.
     *
     * @return the snake's head
     */
    public SnakePart getHead() {
        return body.get(0);
    }

    /**
     * Increases the size of the snake by adding a new part.
     */
    public void lunch() {
        body.add(new SnakePart(previous.getCoordinateX(), previous.getCoordinateY()));
    }
}

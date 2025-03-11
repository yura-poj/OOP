package ru.nsu.pozhidaev;

import java.util.ArrayList;

/**
 * Snake that can move, change direction, eat and bump into the blocks, and grow up.
 */
public class Snake {
    ArrayList<SnakePart> body = new ArrayList<>();
    private int moveOrdinate;
    private int moveAbscissa;
    private SnakePart previous;

    public Snake(int startX, int startY) {
        for(int i =0; i < 3; i++){
            body.add(new SnakePart(startX + i, startY));
        }
        moveOrdinate = -1;
        moveAbscissa = 0;
        previous = new SnakePart(0, 0);
    }

    public void setDirection(Action action) {
        switch (action) {
            case DOWN:
                if (moveAbscissa == 0) {
                    moveAbscissa = 1;
                    moveOrdinate = 0;
                }
                break;
            case UP:
                if (moveAbscissa == 0) {
                    moveAbscissa = -1;
                    moveOrdinate = 0;
                }
                break;
            case LEFT:
                if (moveOrdinate == 0){
                    moveOrdinate = -1;
                    moveAbscissa = 0;
                }
                break;
            case RIGHT:
                if (moveOrdinate == 0){
                    moveOrdinate = 1;
                    moveAbscissa = 0;
                }
                break;
        }
    }

    public void move() {
        previous.setX(body.getLast().getX());
        previous.setY(body.getLast().getY());


        for(int i = body.size()-1; i > 0; i--){
            body.get(i).setX(body.get(i-1).getX());
            body.get(i).setY(body.get(i-1).getY());
        }

        body.get(0).setX(body.get(0).getX() + moveOrdinate);
        body.get(0).setY(body.get(0).getY() + moveAbscissa);

    }

    public ArrayList<SnakePart> getBody() {
        return new ArrayList<>(body);
    }

    public SnakePart getHead() {
        return body.get(0);
    }

    public void lunch() {
        body.add(new SnakePart(previous.getX(), previous.getY()));
    }

}

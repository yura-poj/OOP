package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * main controller of the game.
 */
public class SnakeGame {
    private Snake snake;
    private ArrayList<Wall> walls;
    private ArrayList<Treat> treats;
    private int score;
    private int bestScore;
    private int width = 20;
    private int height = 20;
    private boolean gameOver;




    public SnakeGame() {
        snake = new Snake(10,10);
        score = 0;
        bestScore = 0;
        Treat treat;
        treats = new ArrayList<Treat>();
        walls = new ArrayList<Wall>();
        for(int i = 0; i < 5; i ++){
            treat = new Treat();
            treats.add(treat);
            appearTreat(treat);
        }

    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public ArrayList<SnakePart> getSnakeBody() {
        return snake.getBody();
    }

    public ArrayList<Treat> getTreats() {
        return new ArrayList<>(treats);
    }

    public ArrayList<Wall> getWalls() {
        return new ArrayList<>(walls);
    }

    public void move() {
        snake.move();
        checkCollision();
        checkLunch();
    }

    private void checkCollision() {
        SnakePart head = snake.getHead();
        if(head.getY() > height || head.getY() < 0 || head.getX() > width || head.getX() < 0) {
            gameOver = true;
            return;
        }
        gameOver = Stream.of(
                        walls.stream(),
                        snake.getBody().stream()
                ).flatMap(s -> s)
                .anyMatch(s -> s.getX() == head.getX() && s.getY() == head.getY());
    }

    private void checkLunch() {
        SnakePart head = snake.getHead();
        for(Treat treat : treats) {
            if(head.getX() == treat.getX() && head.getY() == treat.getY()) {
                snake.lunch();
                updateScore();
                appearTreat(treat);
                return;
            }
        }
    }

    private void updateScore() {
        score++;
        if(bestScore < score) {
            bestScore = score;
        }
    }

    private void appearTreat(Treat treat){
        int x = 0;
        int y = 0;
        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (! blockExist(x,y));

        treat.setX(x);
        treat.setY(y);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void receiveAction(Action action) {
        if( action == Action.UP || action == Action.DOWN || action == Action.LEFT || action == Action.RIGHT)  {
            snake.setDirection(action);
        }
    }

    private boolean blockExist(int x, int y) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snake.getBody().stream()
                ).flatMap(s -> s)
                .anyMatch(s -> s.getX() == x && s.getY() == y);
    }
}

package ru.nsu.pozhidaev;

import lombok.Getter;

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
    @Getter
    private int score;
    @Getter
    private int bestScore;
    private boolean gameOver;
    @Getter
    private boolean gameWon;
    GameSettings settings;




    public SnakeGame(GameSettings settings) {
        this.settings = settings;
        treats = new ArrayList<Treat>();
        walls = new ArrayList<Wall>();
        setUpWalls();
        for(int i = 0; i < settings.getNumberFood(); i ++){
            treats.add(new Treat());
        }
        startOver();
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

        if(gameOver){
            return;
        }

        snake.move();
        checkCollision();
        checkLunch();
        checkWin();
    }

    private void checkWin() {
        if(score >= settings.getNumberFoodToWin()){
            gameOver = true;
            gameWon = true;
        }
    }

    public void startOver(){
        List<Integer> snakeCoordinates = settings.getSnakeCoordinates();
        snake = new Snake(snakeCoordinates.get(0),snakeCoordinates.get(1));
        score = 0;
        for( Treat treat : treats){
            appearTreat(treat);
        }
        gameOver = false;
        gameWon = false;

    }

    private void checkCollision() {
        SnakePart head = snake.getHead();
        if(head.getY() > settings.getHeight() || head.getY() < 0 || head.getX() > settings.getWidth() || head.getX() < 0) {
            gameOver = true;
            return;
        }
        gameOver = Stream.concat(
                walls.stream(),
                snake.getBody().stream().skip(1)
        ).anyMatch(s -> s.getX() == head.getX() && s.getY() == head.getY());
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
            x = (int) (Math.random() * settings.getWidth());
            y = (int) (Math.random() * settings.getHeight());
        } while ( blockExist(x,y));

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

    public boolean blockExist(int x, int y) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snake.getBody().stream()
                ).flatMap(s -> s)
                .anyMatch(s -> s.getX() == x && s.getY() == y);
    }

    private void setUpWalls() {
        List<List<Integer>> wallsCoordinates = settings.getWalls();
        for(List<Integer> wallCoordinates : wallsCoordinates) {
            walls.add(new Wall(wallCoordinates.get(0), wallCoordinates.get(1)));
        }
    }
}

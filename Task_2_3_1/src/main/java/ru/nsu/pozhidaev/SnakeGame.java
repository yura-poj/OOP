package ru.nsu.pozhidaev;

/**
 * main controller of the game.
 */
public class SnakeGame {
    private Snake snake;
    private int score;
    private int bestScore;

    public SnakeGame() {
        snake = new Snake();
        score = 0;
        bestScore = 0;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

//    public ArrayList<Block> getSnakeBody() {
//        return snake.getBody();
//    }

    public void setDirection(Action action) {

    }
}

package ru.nsu.pozhidaev;

/**
 * part of body of the snake, it's block that can be obstacle for the snake.
 */
public class SnakePart implements Block{
    private int x;
    private int y;

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    *
     * @return
    */
        @Override
        public int getX() {
            return x;
        }

    /**
    *
     * @return
    */
        @Override
        public int getY() {
            return y;
        }
}

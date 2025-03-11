package ru.nsu.pozhidaev;

/**
 * Block that can will be obstacle for the snake, after bump into that, snake will dye.
 */
public class Wall implements Block{
    private int x;
    private int y;
    public Wall(int x, int y) {
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

/**
*
 * @param x
*/
    @Override
    public void setX(int x) {
        this.x = x;
    }

/**
*
 * @param y
*/
    @Override
    public void setY(int y) {
        this.y = y;
    }
}

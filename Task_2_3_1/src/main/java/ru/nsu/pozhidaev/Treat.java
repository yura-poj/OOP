package ru.nsu.pozhidaev;

/**
 * block of treat on the board that snake can eat and after that object will be destroyed.
 */
public class Treat implements Block{
    int x;
    int y;
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

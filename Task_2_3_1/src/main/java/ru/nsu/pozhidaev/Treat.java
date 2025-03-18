package ru.nsu.pozhidaev;

import lombok.Getter;
import lombok.Setter;

/**
 * Treat represents a block on the board that the snake can eat.
 * Once eaten, the treat is destroyed.
 */
public class Treat extends Block {
    @Getter
    @Setter
    private boolean booked;
    public Treat(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        booked = false;
    }

}

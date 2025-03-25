package ru.nsu.pozhidaev;

/**
 * Action represents the possible directions the snake can move.
 */
public enum Action {
    /**
     * Move the snake downwards.
     */
    DOWN,
    /**
     * Move the snake upwards.
     */
    UP,
    /**
     * Move the snake to the left.
     */
    LEFT,
    /**
     * Move the snake to the right.
     */
    RIGHT;

    public static Action bestDirectionByX(int treat, int head) {
        if (treat < head) {
            return LEFT;
        }

        return RIGHT;
    }

    public static Action bestDirectionByY(int treat, int head) {
        if (treat < head) {
            return UP;
        }

        return DOWN;
    }

    public Action opposite(){
        return opposite(this);
    }

    public static Action opposite(Action action) {
        return switch (action) {
            case DOWN -> UP;
            case UP -> DOWN;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> {
                System.out.println("Unknown action");
                yield null;
            }
        };
    }
}

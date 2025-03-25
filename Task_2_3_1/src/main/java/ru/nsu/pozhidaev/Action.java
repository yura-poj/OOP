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

    /**
     * return best direction by treat.
     *
     * @param treat x coordinate of treat.
     * @param head x coordinate of head.
     *
     * @return best direction.
     */
    public static Action bestDirectionByX(int treat, int head) {
        if (treat < head) {
            return LEFT;
        }

        return RIGHT;
    }

    /**
     * return best direction by treat.
     *
     * @param treat y coordinate of treat.
     * @param head y coordinate of head.
     *
     * @return best direction.
     */
    public static Action bestDirectionByY(int treat, int head) {
        if (treat < head) {
            return UP;
        }

        return DOWN;
    }

    /**
     * find opposite direction from yourself.
     *
     * @return opposite direction.
     */
    public Action opposite() {
        return opposite(this);
    }

    /**
     * return opposite direction by selected direction.
     *
     * @param action from which opposite needed.
     *
     * @return opposite direction.
     */
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

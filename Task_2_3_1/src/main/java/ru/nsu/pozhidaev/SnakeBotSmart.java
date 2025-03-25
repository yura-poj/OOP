package ru.nsu.pozhidaev;

/**
 * A smart implementation of the SnakeBot that uses pathfinding to locate and collect treats.
 * This bot calculates the closest treat and attempts to navigate to it using the most direct
 * available path while avoiding obstacles.
 */
public class SnakeBotSmart extends SnakeBot {

    /**
     * Constructs a new smart snake bot with a reference to the game instance.
     *
     * @param game the game instance this bot belongs to
     */
    public SnakeBotSmart(SnakeGame game) {
        super(game);
    }

    /**
     * Implements the thinking strategy for the smart bot.
     * The bot identifies the closest available treat and sorts its movement
     * directions to prioritize the most direct path to the treat.
     */
    @Override
    void think() {
        if (closestTreat==null || !closestTreat.isBooked()) {
            setUpClosestTreat();
        }

        if (closestTreat!=null) {
            sortDirections();
        }
    }

    /**
     * Identifies and sets up the closest available treat as the bot's target.
     * Calculates distances to all unbooked treats and selects the nearest one.
     * Once a treat is selected, it is marked as booked to prevent other bots
     * from targeting it.
     */
    private void setUpClosestTreat() {
        int closestSum;
        closestSum = Integer.MAX_VALUE;
        for (Treat treat : game.getTreats()) {
            if (treat.isBooked()) {
                continue;
            }
            int x = treat.coordinateX - getHead().getCoordinateX();
            int y = treat.coordinateY - getHead().getCoordinateY();
            int sum = (int) Math.round(Math.sqrt(x * x + y * y));
            if (sum < closestSum) {
                closestTreat = treat;
                closestSum = sum;
            }
        }

        if (closestTreat!=null) {
            closestTreat.setBooked(true);
        }
    }

    /**
     * Sorts the available directions based on the position of the target treat.
     * Prioritizes movements that bring the bot closer to the treat while
     * considering both vertical and horizontal distances.
     */
    private void sortDirections() {
        int treatX = closestTreat.getCoordinateX();
        int treatY = closestTreat.getCoordinateY();
        int headX = getHead().getCoordinateX();
        int headY = getHead().getCoordinateY();
        Action predictX = Action.bestDirectionByX(treatX, headX);
        Action predictY = Action.bestDirectionByY(treatY, headY);

        sortedDirections.set(0, predictX);
        sortedDirections.set(1, predictY);
        sortedDirections.set(2, predictX.opposite());
        sortedDirections.set(3, predictY.opposite());
        
        System.out.println(sortedDirections);
    }
}

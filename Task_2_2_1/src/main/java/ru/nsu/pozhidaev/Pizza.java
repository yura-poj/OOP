package ru.nsu.pozhidaev;

/**
 * Pizza that should be baked.
 */
public class Pizza {
    private Order order;
    private static int idCounter = 0;
    private final int id;

    /**
     * constructor.
     *
     * @param order to which pizza rely on.
     */
    public Pizza(Order order) {
        this.id = ++idCounter;
        this.order = order;
    }

    /**
     * getter.
     *
     * @return order to which pizza rely on.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * getter.
     *
     * @return id of pizza.
     */
    public int getId() {
        return id;
    }
}

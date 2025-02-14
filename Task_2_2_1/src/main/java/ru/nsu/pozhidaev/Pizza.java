package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pizza that should be baked.
 */
public class Pizza {
    private Order order;
    private static AtomicInteger idCounter = new AtomicInteger(0);
    private final int id;

    /**
     * constructor.
     *
     * @param order to which pizza rely on.
     */
    public Pizza(Order order) {
        this.id = idCounter.getAndIncrement();
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

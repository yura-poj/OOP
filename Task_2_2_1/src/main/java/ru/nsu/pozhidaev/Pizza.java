package ru.nsu.pozhidaev;

public class Pizza {
    private Order order;
    private static int idCounter = 0;
    private final int id;

    public Pizza(Order order) {
        this.id = ++idCounter;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }
}

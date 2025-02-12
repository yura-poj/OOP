package ru.nsu.pozhidaev;

/**
 * Order that automatically create bakery.
 * have unique id, pizza that they should bake, and status of order.
 */
public class Order {
    private static int idCounter = 1;
    private final int id;
    private Pizza pizza;
    private Status status;

    /**
     * constructor.
     */
    public Order() {
        this.id = idCounter++;
        this.pizza = new Pizza(this);
        setStatus(Status.PROCESSING);
    }

    /**
     * setter.
     *
     * @param status of order.
     */
    public void setStatus(Status status) {
        this.status = status;
        printStatus();
    }

    /**
     * getter.
     *
     * @return current status of order.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * getter.
     *
     * @return pizza that they should bake.
     */
    public Pizza getPizza() {
        return pizza;
    }

    /**
     * print information about order with status.
     */
    public void printStatus() {
        System.out.println("Status of order №" + id + " is: " + status.getDescription());
    }
}

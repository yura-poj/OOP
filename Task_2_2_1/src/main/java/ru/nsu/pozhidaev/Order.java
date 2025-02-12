package ru.nsu.pozhidaev;

public class Order {
    private static int idCounter = 1;
    private final int id;
    private Pizza pizza;
    private Status status;

    public Order() {
        this.id = idCounter++;
        this.pizza = new Pizza(this);
        setStatus(Status.PROCESSING);
    }

    public void setStatus(Status status) {
        this.status = status;
        printStatus();
    }

    public Status getStatus() {
        return status;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void printStatus() {
        System.out.println("Status of order №" + id + " is: " + status.getDescription());
    }
}

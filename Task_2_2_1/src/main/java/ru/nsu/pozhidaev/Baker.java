package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * baker that bake pizza.
 */
public class Baker implements Runnable {
    private final int speed;
    private Queue<Pizza> storage;
    private Queue<Order> orderQueue;
    private AtomicBoolean isClosed;

    /**
     * constructor.
     *
     * @param speed achieve in seconds, after archive in milliseconds.
     * @param storage is a queue with ready to send pizzas.
     * @param orderQueue is a queue with orders.
     * @param isClosed tells is bakery closed or not.
     */
    public Baker(int speed, Queue<Pizza> storage, Queue<Order> orderQueue, AtomicBoolean isClosed) {
        this.speed = speed * 1000;
        this.storage = storage;
        this.orderQueue = orderQueue;
        this.isClosed = isClosed;
    }

    /**
     * function that run in thread.
     * baker try to get order, and after getting start cooking (sleeping).
     * send information about orders.
     * and at the end send pizza to storage, wait if storage is full.
     */
    @Override
    public void run() {
        ArrayList<Order> orders = null;
        Order order = null;
        while (!isClosed.get()) {
            try {
                orders = orderQueue.pop(1);
                if (orders == null || orders.isEmpty()) {
                    break;
                }
                order = orders.get(0);

                if (order == null) {
                    break;
                }
                order.setStatus(Status.COOKING);
                sleep(speed);
                order.setStatus(Status.COOKED);
                storage.push(order.getPizza());

            } catch (InterruptedException e) {
                System.out.println("Baker is fired");
                return;
            }
        }
    }
}

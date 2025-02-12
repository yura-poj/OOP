package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * baker that bake pizza.
 */
public class Baker implements Runnable {
    private final int speed;
    private Queue storage;
    private Queue orderQueue;
    private AtomicBoolean isClosed;

    /**
     * constructor.
     *
     * @param speed achieve in seconds, after archive in milliseconds.
     * @param storage is a queue with ready to send pizzas.
     * @param orderQueue is a queue with orders.
     * @param isClosed tells is bakery closed or not.
     */
    public Baker(int speed, Queue storage, Queue orderQueue, AtomicBoolean isClosed) {
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
        Pizza[] result;
        Pizza pizza = null;
        while (!isClosed.get()) {
            try {
                result = orderQueue.pop(1);
                if (result.length == 0) {
                    break;
                }
                pizza = result[0];

                if (pizza == null) {
                    break;
                }
                pizza.getOrder().setStatus(Status.COOKING);
                sleep(speed);
                pizza.getOrder().setStatus(Status.COOKED);
                storage.push(pizza);

            } catch (InterruptedException e) {
                System.out.println("Baker is fired");
                return;
            }
        }
    }
}

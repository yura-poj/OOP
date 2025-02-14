package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * courier that deliver pizza to customers.
 */
public class Courier implements Runnable {
    private final int volume;
    private Queue<Pizza> storage;
    private AtomicBoolean isClosed;
    private final int speed;

    /**
     * constructor.
     *
     * @param volume is a number of pizza which can compact in baggage.
     * @param storage is a queue with ready to send pizzas.
     * @param isClosed tells is bakery closed or not.
     */
    public Courier(int volume, int speed, Queue<Pizza> storage, AtomicBoolean isClosed) {
        this.volume = volume;
        this.storage = storage;
        this.isClosed = isClosed;
        this.speed = speed * 1000;
    }

    /**
     * function that run in thread.
     * courier try to get pizzas from the storage, and after getting start delivering(sleeping).
     * update info about orders.
     */
    @Override
    public void run() {
        ArrayList<Pizza> pizzas = null;
        while (!isClosed.get()) {
            try {

                pizzas = storage.pop(volume);

                if (pizzas == null || pizzas.isEmpty()) {
                    break;
                }

                for (Pizza pizza : pizzas) {
                    pizza.getOrder().setStatus(Status.DELIVERING);
                }

                sleep(speed);

                for (Pizza pizza : pizzas) {
                    pizza.getOrder().setStatus(Status.DELIVERED);
                }
            } catch (InterruptedException e) {
                System.out.println("Courier is fired");
                return;
            }
        }
    }
}

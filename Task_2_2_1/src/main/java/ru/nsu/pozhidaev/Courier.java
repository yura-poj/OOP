package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class Courier implements Runnable {
    private static final int speed = 2000;
    private final int volume;
    private Queue storage;
    private AtomicBoolean isClosed;

    /**
     * @param volume   is a number of pizza which can compact in bagage
     * @param storage
     * @param isClosed
     */
    public Courier(int volume, Queue storage, AtomicBoolean isClosed) {
        this.volume = volume;
        this.storage = storage;
        this.isClosed = isClosed;
    }

    /**
     *
     */
    @Override
    public void run() {
        Pizza[] pizzas = new Pizza[0];
        while (!isClosed.get()) {
            try {

                pizzas = storage.pop(volume);

                if (pizzas.length == 0) {
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


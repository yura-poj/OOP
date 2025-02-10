package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Baker implements Runnable,Comparable<Baker> {
    private final int speed;
    private Thread thread;
    AtomicInteger index;
    Storage storage;

    public Baker(int speed, AtomicInteger index, Storage storage) {
        this.speed = speed;
        this.index = index;
        this.storage = storage;
    }


    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Baker o) {
        return this.speed - o.speed;
    }

    /**
     *
     */
    @Override
    public void run() {
        int pizza = index.getAndIncrement();
        Status.COOKING.printStatus(pizza);
        try {
            sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Status.COOKED.printStatus(pizza);
        storage.bringPizza(pizza);
    }
}

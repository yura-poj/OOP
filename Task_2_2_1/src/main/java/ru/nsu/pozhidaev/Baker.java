package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Baker implements Runnable,Comparable<Baker> {
    private final int speed;
    Queue storage;
    Queue orderQueue;

    public Baker(int speed, Queue storage, Queue orderQueue) {
        this.speed = speed;
        this.storage = storage;
        this.orderQueue = orderQueue;
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
        storage.push(pizza);
    }
}

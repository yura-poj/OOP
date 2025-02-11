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
        int pizza = 0;
        while (true) {
            try {
                pizza = orderQueue.pop(1)[0];
            } catch (InterruptedException e) {
                close();
            }
            Status.COOKING.printStatus(pizza);
            try {
                sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Baker is fired");
            }
            Status.COOKED.printStatus(pizza);
            try {
                storage.push(pizza);
            } catch (InterruptedException e) {
                close();
            }
        }
    }

    private void close() {
        //
    }
}

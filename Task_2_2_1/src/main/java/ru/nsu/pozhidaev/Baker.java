package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Baker implements Runnable {
    private final int speed;
    private Queue storage;
    private Queue orderQueue;
    private AtomicBoolean isClosed;

    /**
     *
     * @param speed achieve in seconds, after archive in milliseconds
     * @param storage
     * @param orderQueue
     * @param isClosed
     */
    public Baker(int speed, Queue storage, Queue orderQueue, AtomicBoolean isClosed) {
        this.speed = speed * 1000;
        this.storage = storage;
        this.orderQueue = orderQueue;
        this.isClosed = isClosed;
    }

    /**
     *
     */
    @Override
    public void run() {
        int[] result;
        int pizza = 0;
        while (!isClosed.get()) {
            try {
                result = orderQueue.pop(1);
                if (result.length == 0) {
                    break;
                }
                pizza = result[0];

                if (pizza == 0) {
                    break;
                }

                Status.COOKING.printStatus(pizza);
                sleep(speed);
                Status.COOKED.printStatus(pizza);
                storage.push(pizza);

                } catch (InterruptedException e) {
                System.out.println("Baker is fired");
                return;
            }
        }
    }
}

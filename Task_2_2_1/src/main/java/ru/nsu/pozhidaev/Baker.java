package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Baker implements Runnable,Comparable<Baker> {
    private final int speed;
    Queue storage;
    Queue orderQueue;
    AtomicBoolean isClosed;

    public Baker(int speed, Queue storage, Queue orderQueue, AtomicBoolean isClosed) {
        this.speed = speed * 1000;
        this.storage = storage;
        this.orderQueue = orderQueue;
        this.isClosed = isClosed;
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
        int[] result;
        int pizza = 0;
        while (!isClosed.get()) {
            try {
                result = orderQueue.pop(1);
                if (result.length == 0){
                    break;
                }
                pizza =  result[0];
            } catch (InterruptedException e) {
                System.out.println("Baker is fired");
            }

            if (pizza == 0) {
                break;
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
                System.out.println("Baker is fired");
            }
        }
    }
}

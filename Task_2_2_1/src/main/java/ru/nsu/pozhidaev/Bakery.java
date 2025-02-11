package ru.nsu.pozhidaev;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Bakery {
    private static final int STACK_SIZE = 100000;

    private final Queue storage;
    private final Queue orderQueue;
    private AtomicInteger index;
    private Baker[] bakers;
    private Courier[] couriers;


    public Bakery(int[] bakers, int[] couriers, int storage) {
        index = new AtomicInteger(1);
        this.storage = new Queue(storage);
        this.orderQueue = new Queue(100000);

        this.bakers = new Baker[bakers.length];
        this.couriers = new Courier[couriers.length];

        for( int i = 0; i < bakers.length; i++ ) {
            this.bakers[i] = new Baker(bakers[i], index, this.storage, orderQueue);
        }

        for( int i = 0; i < couriers.length; i++ ) {
            this.couriers[i] = new Courier(bakers[i], this.storage);
        }

        start();
    }

    public void order() {
        Status.PROCESSING.printStatus(index.get());
        orderQueue.push(index.getAndIncrement());
    }

    public void close() {
        //
    }

    private void start() {
        Arrays.sort(bakers);
        Arrays.sort(couriers);

        for(Baker baker : bakers) {
            baker.run();
        }

        for(Courier courier : couriers) {
            courier.run();
        }
    }
}
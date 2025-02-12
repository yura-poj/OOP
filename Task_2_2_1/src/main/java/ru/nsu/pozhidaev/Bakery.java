package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Bakery {
    private static final int STACK_SIZE = 100000;
    private final Queue storage;

    private final Queue orderQueue;
    private AtomicInteger index;
    private Baker[] bakers;
    private Courier[] couriers;
    private Thread[] bakerThreads;
    private Thread[] courierThreads;

    private AtomicBoolean isClosed;


    public Bakery(int[] bakers, int[] couriers, int storage) {
        index = new AtomicInteger(1);
        isClosed = new AtomicBoolean(false);

        this.storage = new Queue(storage, isClosed);
        this.orderQueue = new Queue(100000, isClosed);

        this.bakers = new Baker[bakers.length];
        bakerThreads = new Thread[bakers.length];

        this.couriers = new Courier[couriers.length];
        courierThreads = new Thread[couriers.length];


        for( int i = 0; i < bakers.length; i++ ) {
            this.bakers[i] = new Baker(bakers[i], this.storage, orderQueue, isClosed);
        }

        for( int i = 0; i < couriers.length; i++ ) {
            this.couriers[i] = new Courier(bakers[i], this.storage, isClosed);
        }
        start();
    }

    public void order() {
        Status.PROCESSING.printStatus(index.get());
        try {
            orderQueue.push(index.getAndIncrement());
        } catch (InterruptedException e) {
            close();
        }
    }

    public void close() {
        isClosed.set(true);
        storage.close();
        orderQueue.close();
        try {

            for (Thread courierThread : courierThreads) {
                courierThread.join();
            }
            for (Thread bakerThread : bakerThreads) {
                bakerThread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Bakery is on fire");
        }
    }

    public void start() {

        for (int i = 0; i < bakers.length; i++) {
            bakerThreads[i] = new Thread(bakers[i]);
            bakerThreads[i].start();
        }

        for (int i = 0; i < couriers.length; i++) {
            courierThreads[i] = new Thread(couriers[i]);
            courierThreads[i].start();
        }
    }
}

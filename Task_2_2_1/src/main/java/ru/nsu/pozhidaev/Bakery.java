package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bakery that work with bakers and couriers, has storage with some volume.
 */
public class Bakery {
    private static final int STACK_SIZE = 100000;
    private final Queue<Pizza> storage;

    private final Queue<Order> orderQueue;
    private AtomicInteger index;
    private Baker[] bakers;
    private Courier[] couriers;
    private Thread[] bakerThreads;
    private Thread[] courierThreads;

    private AtomicBoolean isClosed;

    /**
     * constructor.
     */
    public Bakery(String pathToJson) {
        BakeryJsonParser bakeryJsonParser = new BakeryJsonParser(pathToJson);

        index = new AtomicInteger(1);
        isClosed = new AtomicBoolean(false);

        this.storage = new Queue<Pizza>(bakeryJsonParser.getStorageVolume(), isClosed);
        this.orderQueue = new Queue<Order>(bakeryJsonParser.getStackSize(), isClosed);

        int[] bakersSpeeds =  bakeryJsonParser.getBakersSpeeds();
        this.bakers = new Baker[bakersSpeeds.length];
        bakerThreads = new Thread[bakersSpeeds.length];

        int[] courierVolumes = bakeryJsonParser.getCouriersVolumes();
        this.couriers = new Courier[courierVolumes.length];
        courierThreads = new Thread[courierVolumes.length];

        for (int i = 0; i < bakersSpeeds.length; i++) {
            this.bakers[i] = new Baker(bakersSpeeds[i], this.storage, orderQueue, isClosed);
        }

        for (int i = 0; i < courierVolumes.length; i++) {
            this.couriers[i] = new Courier(courierVolumes[i], bakeryJsonParser.getCourierSpeed(),
                    this.storage, isClosed);
        }
        start();
    }

    /**
     * create order.
     * if closed - not receive order.
     *
     * @return created order.
     */
    public Order order() {
        if (isClosed.get()) {
            return null;
        }
        Order order = null;
        try {
            order = new Order();
            orderQueue.push(order);
        } catch (InterruptedException e) {
            close();
        }
        return order;
    }

    /**
     * close bakery.
     * stop receive new orders.
     * change atomic bool isClosed to true.
     * wait to all workers finish their job.
     */
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

    /**
     * tells all bakers and couriers to start working.
     * create new threads with workers.
     */
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

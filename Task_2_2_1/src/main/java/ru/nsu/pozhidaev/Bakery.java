package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

public class Bakery {
    private final CourierManager courierManager;
    private final BakerManager bakerManager;
    private final Storage storage;

    public Bakery(Baker[] bakers, Courier[] couriers, Storage storage) {
        courierManager = new CourierManager(couriers, storage);
        bakerManager = new BakerManager(bakers, storage);
        this.storage = storage;

        courierManager.run();
        bakerManager.run();
    }

    public void order() {
        System.out.println();
        bakerManager.notify();
    }

    public void close() {
        //
    }
}
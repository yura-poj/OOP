package ru.nsu.pozhidaev;

public class Bakery {
    private final CourierManager courierManager;
    private final BakerManager bakerManager;
    private final Storage storage;

    public Bakery(Baker[] bakers, Courier[] couriers, Storage storage) {
        courierManager = new CourierManager(couriers);
        bakerManager = new BakerManager(bakers);
        this.storage = storage;
    }

    public void order() {

    }

    public void cooked() {

    }

    public void ready () {

    }
}
package ru.nsu.pozhidaev;

import java.util.Arrays;

public class CourierManager implements Runnable {
    private Courier[] couriers;
    private Storage storage;

    public CourierManager(Courier[] couriers, Storage storage) {
        this.couriers = couriers;
        Arrays.sort(couriers);
        this.storage = storage;
    }

    /**
     *
     */
    @Override
    public void run() {
        for(Courier courier : couriers){
//            if(courier.isFree){
//                  courier.notify
//            }
        }
    }

    private void close() {

    }
}

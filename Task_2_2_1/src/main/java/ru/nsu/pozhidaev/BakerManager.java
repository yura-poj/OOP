package ru.nsu.pozhidaev;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BakerManager implements Runnable {
    private Baker[] bakers;
    private AtomicInteger index;
    private Storage storage;

    public BakerManager(Baker[] bakers,Storage storage) {
        this.bakers = bakers;
        Arrays.sort(bakers);
        index = new AtomicInteger(1);
        this.storage = storage;
    }


    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                close();
            }
            Status.PROCESSING.printStatus(index.get());
            //choose baker

        }
    }

    private void close() {

    }
}

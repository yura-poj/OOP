package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class Courier implements Runnable {
    private static final int speed = 2000;
    private final int volume;
    private Queue storage;
    private AtomicBoolean isClosed;

    /**
     * @param volume   is a number of pizza which can compact in bagage
     * @param storage
     * @param isClosed
     */
    public Courier(int volume, Queue storage, AtomicBoolean isClosed) {
        this.volume = volume;
        this.storage = storage;
        this.isClosed = isClosed;
    }

    /**
     *
     */
    @Override
    public void run() {
        int[] indexes = new int[0];
        while (!isClosed.get()) {
            try {

                indexes = storage.pop(volume).clone();

                if (indexes.length == 0) {
                    break;
                }

                for (int i = 0; i < indexes.length && i < volume; i++) {
                    Status.DELIVERING.printStatus(indexes[i]);
                }

                sleep(speed);

                for (int i = 0; i < indexes.length && i < volume; i++) {
                    Status.DELIVERED.printStatus(indexes[i]);
                }
            } catch (InterruptedException e) {
                System.out.println("Courier is fired");
                return;
            }

        }
    }
}


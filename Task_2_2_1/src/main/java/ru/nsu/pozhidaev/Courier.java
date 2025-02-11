package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class Courier implements Runnable, Comparable<Courier> {
    private static final int speed = 2000;
    private final int volume;
    private Queue storage;
    AtomicBoolean isClosed;

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
            } catch (InterruptedException e) {
                System.out.println("Courier is fired");
            }

            if (indexes.length == 0) {
                break;
            }

            for (int i = 0; i < indexes.length && i < volume; i++) {
                Status.DELIVERING.printStatus(indexes[i]);
            }

            try {
                sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Courier is fired");
            }

            for (int i = 0; i < indexes.length && i < volume; i++) {
                Status.DELIVERED.printStatus(indexes[i]);
            }
        }
    }

    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Courier o) {
        return this.volume - o.volume;
    }
}


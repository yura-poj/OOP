package ru.nsu.pozhidaev;

import static java.lang.Thread.sleep;

public class Courier implements Runnable, Comparable<Courier> {
    private static final int speed = 10;
    private final int volume;
    private Queue storage;

    public Courier(int volume, Queue storage) {
        this.volume = volume;
        this.storage = storage;
    }

    public void deliver(int[] indexes) {

    }

    /**
     *
     */
    @Override
    public void run() {
        int[] indexes = storage.getPizzas(volume);
        for (int i = 0; i < indexes.length && i < volume; i++) {
            Status.DELIVERING.printStatus(indexes[i]);
        }

        try {
            sleep(speed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < indexes.length && i < volume; i++) {
            Status.DELIVERING.printStatus(indexes[i]);
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

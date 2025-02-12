package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

public class Queue {
    private final int volume;
    private final Pizza[] pizzas;
    private int size;
    private AtomicBoolean isClosed;

    private int removeQueue;
    private int currentPosition;
    private final Object lock = new Object(); // Объект для синхронизации

    public Queue(int volume, AtomicBoolean isClosed) {
        this.volume = volume;
        this.pizzas = new Pizza[volume];
        this.size = 0;
        this.removeQueue = 0;
        this.currentPosition = 0;
        this.isClosed = isClosed;
    }

    public void push(Pizza index) throws InterruptedException {
        synchronized (lock) {
            while (size >= volume) {
                lock.wait();
                if (isClosed.get()) {
                    return;
                }
            }
            pizzas[currentPosition] = index;
            size++;
            currentPosition = (currentPosition + 1) % volume;
            lock.notify();
        }
    }

    public Pizza[] pop(int amount) throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
                if (isClosed.get()) {
                    return new Pizza[0];
                }
            }
            int nums = Math.min(size, amount);
            Pizza[] baggage = new Pizza[nums];
            for (int i = 0; i < nums; i++) {
                baggage[i] = pizzas[removeQueue];
                removeQueue = (removeQueue + 1) % volume;
                size--;

                lock.notify();
            }
            return baggage;
        }
    }

    public void close() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

}

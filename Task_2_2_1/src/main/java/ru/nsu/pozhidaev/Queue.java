package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Queue is similar to conveyor.
 * You can push one pizza and pop amount of pizzas.
 */
public class Queue {
    private final int volume;
    private final Pizza[] pizzas;
    private int size;
    private AtomicBoolean isClosed;

    private int removeQueue;
    private int currentPosition;
    private final Object lock = new Object(); // Объект для синхронизации

    /**
     * constructor.
     *
     * @param volume of queue.
     * @param isClosed bool closed bakery or not.
     */
    public Queue(int volume, AtomicBoolean isClosed) {
        this.volume = volume;
        this.pizzas = new Pizza[volume];
        this.size = 0;
        this.removeQueue = 0;
        this.currentPosition = 0;
        this.isClosed = isClosed;
    }

    /**
     * push pizza to the queue.
     * wait if queue is full.
     * if bakery is closed right after wait, receive notify and exit from function.
     * move queue and notify pop() that queue have new pizza.
     *
     * @param pizza that will be added to the queue.
     *
     * @throws InterruptedException if was interrupted during wait.
     */
    public void push(Pizza pizza) throws InterruptedException {
        synchronized (lock) {
            while (size >= volume) {
                lock.wait();
                if (isClosed.get()) {
                    return;
                }
            }
            pizzas[currentPosition] = pizza;
            size++;
            currentPosition = (currentPosition + 1) % volume;
            lock.notify();
        }
    }

    /**
     * pop amount of pizzas from the queue.
     * wait if the queue is empty.
     * if bakery is closed right after wait, receive notify and exit from function.
     * move queue and notify push() that queue have space,
     * notify so much times as many pizza we remove.
     *
     * @param amount of pizza.
     * @return pizzas from queue.
     *
     * @throws InterruptedException if was interrupted during wait.
     */
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

    /**
     * notify all waiters that we already closed and they should not wait anymore.
     */
    public void close() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

}

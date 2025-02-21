package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Queue is similar to conveyor.
 * You can push one item and pop amount of pizzas.
 */
public class Queue<T> {
    private final int volume;
    private ArrayList<T> items;
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
        this.items = new ArrayList<T>(Collections.nCopies(volume, null));
        this.items.add(null);
        this.size = 0;
        this.removeQueue = 0;
        this.currentPosition = 0;
        this.isClosed = isClosed;
    }

    /**
     * push item to the queue.
     * wait if queue is full.
     * if bakery is closed right after wait, receive notify and exit from function.
     * move queue and notify pop() that queue have new item.
     *
     * @param item that will be added to the queue.
     *
     * @throws InterruptedException if was interrupted during wait.
     */
    public void push(T item) throws InterruptedException {
        synchronized (lock) {
            while (size >= volume) {
                lock.wait();
                if (isClosed.get()) {
                    return;
                }
            }
            items.set(currentPosition, item);
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
     * notify so much times as many item we remove.
     *
     * @param amount of item.
     * @return pizzas from queue.
     *
     * @throws InterruptedException if was interrupted during wait.
     */
    public ArrayList<T> pop(int amount) throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
                if (isClosed.get()) {
                    return null;
                }
            }
            int nums = Math.min(size, amount);
            ArrayList<T> baggage = new ArrayList<T>(Collections.nCopies(nums, null));
            for (int i = 0; i < nums; i++) {
                baggage.set(i, items.get(removeQueue));
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

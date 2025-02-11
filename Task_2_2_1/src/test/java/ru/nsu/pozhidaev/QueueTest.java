package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueTest {
    Queue queue;
    @BeforeEach
    void setUp() throws InterruptedException {
        queue = new Queue(3,new AtomicBoolean(false));
        queue.push(1);
        queue.push(2);
    }

    @Test
    void push() throws InterruptedException {
        queue.push(3);
        queue.pop(2);
        assertEquals(3, queue.pop(1)[0]);
    }

    @Test
    void pushWithLock() throws InterruptedException {
        queue.push(3);
        Thread thread2 = new Thread(() -> {
            try {
                queue.push(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                queue.pop(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2.start();
        thread3.start();
        thread2.join();
        assertEquals(4, queue.pop(1)[0]);
    }

    @Test
    void pop() throws InterruptedException {
        assertEquals(1, queue.pop(1)[0]);
    }

    @Test
    void popWithLock() throws InterruptedException {
        queue.push(2);
        Thread thread = new Thread(() -> {
            try {
                queue.pop(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                queue.push(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        thread2.start();
        thread.join();
        assertEquals(5, queue.pop(1)[0]);
    }
}
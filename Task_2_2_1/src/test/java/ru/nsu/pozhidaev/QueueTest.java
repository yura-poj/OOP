package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueueTest {
    Queue queue;

    @BeforeEach
    void setUp() throws InterruptedException {
        queue = new Queue(3, new AtomicBoolean(false));
        queue.push(new Pizza(new Order()));
        queue.push(new Pizza(new Order()));
    }

    @Test
    void push() throws InterruptedException {
        Pizza pizza = new Pizza(new Order());
        queue.push(pizza);
        queue.pop(2);
        assertEquals(pizza.getId(), queue.pop(1)[0].getId());
    }

    @Test
    void pushWithLock() throws InterruptedException {
        Pizza pizza = new Pizza(new Order());
        queue.push(new Pizza(new Order()));
        Thread thread2 = new Thread(() -> {
            try {
                queue.push(pizza);
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
        assertEquals(pizza.getId(), queue.pop(1)[0].getId());
    }

    @Test
    void popWithLock() throws InterruptedException {
        Pizza pizza = new Pizza(new Order());
        queue.push(new Pizza(new Order()));
        Thread thread = new Thread(() -> {
            try {
                queue.pop(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                queue.push(pizza);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        thread2.start();
        thread.join();
        assertEquals(pizza.getId(), queue.pop(1)[0].getId());
    }
}
package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void HeapSortTest() {
        long start;
        long finish;
        long timeElapsed;
        start = System.nanoTime();
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, Main.heapsort(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{129, 543}, Main.heapsort(new int[]{543, 129}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{123, 234, 456, 789}, Main.heapsort(new int[]{234, 789, 456, 123}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{123, 234, 345, 456, 567, 678, 789, 876}, Main.heapsort(new int[]{876, 345, 789, 234, 123, 567, 678, 456}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{112, 123, 223, 234, 234, 334, 345, 445, 456, 567, 678, 789, 876, 890, 901, 987}, Main.heapsort(new int[]{987, 345, 234, 567, 876, 123, 456, 678, 234, 789, 890, 901, 112, 223, 334, 445}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);


    }
}
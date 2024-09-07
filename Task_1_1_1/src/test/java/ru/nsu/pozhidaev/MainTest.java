package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void heapSortTest() {
        long start = System.nanoTime();
        assertArrayEquals(new int[]{129, 543}, Main.heapsort(new int[]{543, 129}));
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{123, 234, 456, 789},
                Main.heapsort(new int[]{234, 789, 456, 123}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{123, 234, 345, 456, 567, 678, 789, 876},
                Main.heapsort(new int[]{876, 345, 789, 234, 123, 567, 678, 456}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.nanoTime();
        assertArrayEquals(new int[]{112, 123, 223, 234, 234, 334, 345, 445,
                        456, 567, 678, 789, 876, 890, 901, 987},
                Main.heapsort(new int[]{987, 345, 234, 567, 876, 123, 456, 678, 234,
                        789, 890, 901, 112, 223, 334, 445}));
        finish = System.nanoTime();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        assertArrayEquals(new int[]{2147483640, 2147483643, 2147483645, 2147483647},
                Main.heapsort(new int[]{2147483647, 2147483643, 2147483645, 2147483640}));

        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                Main.heapsort(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));

        assertArrayEquals(new int[]{123, 234, 345, 456, 567, 678, 789, 876},
                Main.heapsort(new int[]{123, 234, 345, 456, 567, 678, 789, 876}));

        assertArrayEquals(new int[]{123, 234, 456, 789},
                Main.heapsort(new int[]{789, 456, 234, 123}));

        assertArrayEquals(new int[]{-789, -456, -234, -123},
                Main.heapsort(new int[]{-789, -234, -456, -123}));
    }
}
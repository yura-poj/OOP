package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class HeapsortTest {

    void countTime(int[] arr) {
        long start = System.nanoTime();
        Heapsort.heapsort(arr);
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);

    }

    @Test
    void speedTest() {
        countTime(new int[]{543, 129});
        countTime(new int[]{234, 789, 456, 123});
        countTime(new int[]{876, 345, 789, 234, 123, 567, 678, 456});
        countTime(new int[]{987, 345, 234, 567, 876, 123, 456, 678, 234,
            789, 890, 901, 112, 223, 334, 445});
    }

    @Test
    void heapSortTest() {
        assertArrayEquals(new int[]{129, 543}, Heapsort.heapsort(new int[]{543, 129}));

        assertArrayEquals(new int[]{123, 234, 456, 789},
                Heapsort.heapsort(new int[]{234, 789, 456, 123}));

        assertArrayEquals(new int[]{123, 234, 345, 456, 567, 678, 789, 876},
                Heapsort.heapsort(new int[]{876, 345, 789, 234, 123, 567, 678, 456}));

        assertArrayEquals(
                new int[]{112, 123, 223, 234, 234, 334, 345, 445,
                    456, 567, 678, 789, 876, 890, 901, 987
                }, Heapsort.heapsort(
                        new int[]{987, 345, 234, 567, 876, 123, 456, 678, 234,
                            789, 890, 901, 112, 223, 334, 445}));
    }

    @Test
    void testWithSimilarNumbers() {
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                Heapsort.heapsort(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
    }

    @Test
    void testForSortedArray() {
        assertArrayEquals(new int[]{123, 234, 345, 456, 567, 678, 789, 876},
                Heapsort.heapsort(new int[]{123, 234, 345, 456, 567, 678, 789, 876}));
    }

    @Test
    void testForReversedArray() {
        assertArrayEquals(new int[]{123, 234, 456, 789},
                Heapsort.heapsort(new int[]{789, 456, 234, 123}));
    }

    @Test
    void testForArrayWithNegativeNumbers() {
        assertArrayEquals(new int[]{-789, -456, -234, -123},
                Heapsort.heapsort(new int[]{-789, -234, -456, -123}));
    }

    @Test
    void testForArrayWithLargeNumbers() {
        assertArrayEquals(new int[]{2147483640, 2147483643, 2147483645, 2147483647},
                Heapsort.heapsort(new int[]{2147483647, 2147483643, 2147483645, 2147483640}));
    }

    @Test
    void testForOneElement() {
        assertArrayEquals(new int[]{129}, Heapsort.heapsort(new int[]{129}));
    }

    @Test
    void testForEmptyArray() {
        assertArrayEquals(new int[]{}, Heapsort.heapsort(new int[]{}));
    }
}
package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void HeapSortTest() {
        int[] array = {5, 8, 3, 9, 1, 6};
        Main.heapsort(array);
        assertArrayEquals(new int[]{1, 3, 5, 6, 8, 9}, array);
    }
}
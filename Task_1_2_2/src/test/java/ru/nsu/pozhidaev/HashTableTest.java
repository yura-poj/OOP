package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    HashTable<String, Integer> hashTable;
    Iterator<Structure<String, Integer>> iterator;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<String, Integer>();
        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("C", 3);
        iterator = hashTable.iterator();
    }

    @Test
    void getKeySize() {
        assertEquals(8, hashTable.getKeySize());
    }

    @Test
    void put() {
        assertFalse(hashTable.contains("D"));
        hashTable.put("D", 1);
        assertTrue(hashTable.contains("D"));
    }

    @Test
    void get() {
        assertEquals(hashTable.get("A"), 1);
    }

    @Test
    void removeByKey() {
        assertTrue(hashTable.contains("A"));
        hashTable.removeByKey("A");
        assertFalse(hashTable.contains("A"));
    }

    @Test
    void update() {
        hashTable.update("A", 5);
        assertEquals(5, hashTable.get("A"));
    }

    @Test
    void contains() {
        assertTrue(hashTable.contains("A"));
        assertFalse(hashTable.contains("D"));
    }

    @Test
    void testToString() {
        assertEquals("A: 1\nB: 2\nC: 3\n", hashTable.toString());
    }

    @Test
    void print() {
        assertTimeout(
                Duration.ofMillis(100),
                () -> {
                    hashTable.print();
                });
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> newHashTable = new HashTable<>();
        newHashTable.put("A", 1);
        newHashTable.put("B", 2);

        assertFalse(hashTable.equals(newHashTable));

        newHashTable.put("C", 3);

        assertTrue(hashTable.equals(newHashTable));
    }

    @Test
    void hasNext() {
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void next() {
        assertEquals(1, iterator.next().data);
    }

    @Test
    void remove() {
        int before = hashTable.getKeyNumber();
        iterator.next();
        iterator.remove();
        assertEquals(before, hashTable.getKeyNumber() + 1);
    }

    @Test
    void iterator() {
        int result = 0;
        for (Structure<String, Integer> structure : hashTable) {
            result++;
        }
        assertEquals(result, hashTable.getKeyNumber());
    }

    @Test
    void checkException() {
        int result = 0;
        assertThrowsExactly(
                ConcurrentModificationException.class,
                () -> {
                    for (Structure<String, Integer> structure : hashTable) {
                        hashTable.removeByKey("A");
                    }
                }
        );
    }
}

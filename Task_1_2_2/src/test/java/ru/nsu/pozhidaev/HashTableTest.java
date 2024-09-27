package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<String, Integer>();
        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("C", 3);
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
        assertTimeout(Duration.ofMillis(100), () -> {
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

        assertTrue(newHashTable.equals(newHashTable));
    }

    @Test
    void hasNext() {
        assertTrue(hashTable.hasNext());
        hashTable.next();
        assertTrue(hashTable.hasNext());
        hashTable.next();
        assertTrue(hashTable.hasNext());
        hashTable.next();
        assertFalse(hashTable.hasNext());
    }

    @Test
    void next() {
        assertEquals(1, hashTable.get("A"));
    }

    @Test
    void remove() {
        int before = hashTable.getKeyNumber();
        hashTable.next();
        hashTable.remove();
        assertEquals(before, hashTable.getKeyNumber() + 1);
    }

    @Test
    void forEachRemaining() {
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> vals = new ArrayList<>();
        vals.add(1);
        vals.add(2);
        vals.add(3);
        hashTable.forEachRemaining((value) -> values.add(value.data));
        assertEquals(values, vals);
    }
}
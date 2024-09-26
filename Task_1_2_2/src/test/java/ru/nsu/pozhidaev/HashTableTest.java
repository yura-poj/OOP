package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
    void getKeys() {
        Set<String> keys = new HashSet<>();
        keys.add("A");
        keys.add("B");
        keys.add("C");
        assertEquals(keys, hashTable.getKeys());
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
    void remove() {
        assertTrue(hashTable.contains("A"));
        hashTable.remove("A");
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
}
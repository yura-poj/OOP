package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HashTable<T, D> {
    private final static int SUB_ARRAY_MAX_SIZE = 4;

    private int keySize;
    Set<T> keys;
    ArrayList<ArrayList<Structure<T, D>>> values;

    public HashTable() {
        values = new ArrayList<>();
        keySize = 8;
        keys = new HashSet<>();
        for (int i = 0; i < keySize; i++) {
            values.add(new ArrayList<>());
        }
    }

    public int getKeySize() {
        return keySize;
    }

    public Set<T> getKeys() {
        return keys;
    }

    public void put(T key, D value) {
        if (keys.contains(key)) {
            return;
        }
        keys.add(key);
        int id = jenkinsHash(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        if (list.size() > SUB_ARRAY_MAX_SIZE) {
            resize();
            id = jenkinsHash(key, keySize);
        }
        list.add(new Structure<>(key, value));
    }

    public D get(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            return structure.data;
        }
        return null;
    }

    public void remove(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            keys.remove(key);
            structure = null;
        }
    }

    public void update(T key, D value) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            structure.data = value;
        }
    }

    public boolean contains(T key) {
        return keys.contains(key);
    }

    @Override
    public String toString() {
        String result = "";
        Structure<T, D> structure = null;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure == null) {
                    continue;
                }
                result += structure.key.toString() + ": " + structure.data.toString() + "\n";
            }
        }
        return result;
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object o) {
        Structure<T, D> structure = null;
        if (o == null || getClass() != o.getClass()) return false;
        HashTable<T, D> hashTable = (HashTable<T, D>) o;
        if (!keys.equals(hashTable.getKeys())) {
            return false;
        }
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure == null) {
                    continue;
                }
                if (hashTable.get(structure.key) != structure.data) {
                    return false;
                }
            }
        }
        return true;
    }

    private Structure<T, D> getStructure(T key) {
        int id = jenkinsHash(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        for (Structure<T, D> structure : list) {
            if (structure.key.equals(key)) {
                return structure;
            }
        }
        return null;
    }

    private int jenkinsHash(T key, int size) {
        String keyString = key.toString();
        int hash = 0;
        for (char c : keyString.toCharArray()) {
            hash += (int) c;
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }

        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);

        return Math.abs(hash) % size;
    }

    private void resize() {
        ArrayList<ArrayList<Structure<T, D>>> newValues = new ArrayList<>();
        for (int i = 0; i < keySize * 2; i++) {
            newValues.add(new ArrayList<>());
        }
        rehash(newValues);
        keySize = keySize * 2;
        values = newValues;
    }

    private void rehash(ArrayList<ArrayList<Structure<T, D>>> newValues) {
        int id = 0;
        Structure structure = null;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure == null) {
                    continue;
                }
                id = jenkinsHash((T) structure.key, keySize);
                newValues.get(id).add(structure);
            }
        }
    }

    private static class Structure<T, D> {
        T key;
        D data;

        Structure(T key, D data) {
            this.key = key;
            this.data = data;
        }
    }
}

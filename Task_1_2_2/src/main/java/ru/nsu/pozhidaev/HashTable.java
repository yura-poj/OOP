package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;

public class HashTable<T, D> implements Iterable<Structure<T, D>> {
    private final static int SUB_ARRAY_MAX_SIZE = 4;


    private int keySize;
    private int keyNumber;
    private ArrayList<ArrayList<Structure<T, D>>> values;

    public HashTable() {
        values = new ArrayList<>();
        keySize = 8;
        keyNumber = 0;
        for (int i = 0; i < keySize; i++) {
            values.add(new ArrayList<>());
        }
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public int getKeySize() {
        return keySize;
    }

    public void put(T key, D value) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            return;
        }

        keyNumber++;
        int id = hashFunstion(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        if (list.size() > SUB_ARRAY_MAX_SIZE) {
            resize();
            id = hashFunstion(key, keySize);
        }
        list.add(new Structure<T, D>(key, value));
    }

    public D get(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            return structure.data;
        }
        return null;
    }

    public void removeByKey(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            keyNumber--;
            structure.key = null;
            structure.data = null;
        }
    }

    public void update(T key, D value) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            structure.data = value;
        }
    }

    public boolean contains(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Structure<T, D> structure = null;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure == null) {
                    continue;
                }
                sb.append(structure.key.toString());
                sb.append(": ");
                sb.append(structure.data.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object o) {
        Structure<T, D> structure = null;
        if (o == null || getClass() != o.getClass()) return false;
        HashTable<T, D> hashTable = (HashTable<T, D>) o;

        return (hashCode() == hashTable.hashCode());
    }

    private Structure<T, D> getStructure(T key) {
        int id = hashFunstion(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        for (Structure<T, D> structure : list) {
            if (structure.key != null && structure.key.equals(key)) {
                return structure;
            }
        }
        return null;
    }

    private int hashFunstion(T key, int size) {
        return Math.abs(key.hashCode()) % size;
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
        Structure<T, D> structure = null;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure.key == null) {
                    continue;
                }
                id = hashFunstion((T) structure.key, keySize);
                newValues.get(id).add(structure);
            }
        }
    }

    @Override
    public Iterator<Structure<T, D>> iterator() {
        return new HashTableIterator();
    }

    @Override
    public int hashCode() {
        Structure<T, D> structure = null;
        int hash = 0;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure.key == null) {
                    continue;
                }
                hash += structure.key.hashCode();
                hash += structure.data.hashCode();
                hash %= 1000;
            }
        }
        hash += keyNumber ;
        return hash;
    }

    private class HashTableIterator implements Iterator<Structure<T, D>> {
        private Structure<T, D> current;
        private int indexI;
        private int indexJ;

        public HashTableIterator() {
            indexI = 0;
            indexJ = -1;
            current = null;
        }

        private Structure<T, D> findNext(boolean change) {
            int newIndexI = indexI;
            int newIndexJ = indexJ;
            Structure<T, D> structure = null;
            while (newIndexI < keySize) {
                newIndexJ++;
                if (newIndexJ > values.get(newIndexI).size() - 1) {
                    newIndexJ = -1;
                    newIndexI++;
                    continue;
                }
                structure = values.get(newIndexI).get(newIndexJ);

                if (structure.key != null) {
                    break;
                }
            }
            if (change) {
                this.indexI = newIndexI;
                this.indexJ = newIndexJ;
            }

            return structure;
        }

        @Override
        public boolean hasNext() {
            return findNext(false) != null;
        }

        @Override
        public Structure<T, D> next() {
            current = findNext(true);
            return current;
        }

        @Override
        public void remove() {
            if (current != null) {
                removeByKey(current.key);
            }
        }

        public void forEach(Consumer<? super Structure<T, D>> action) {
            int oldIndexI = indexI;
            int oldIndexJ = indexJ;
            indexI = 0;
            indexJ = -1;
            try {
                while (hasNext()) {
                    next();
                    if (current != null && current.key != null) {
                        action.accept(current);
                    }
                }
            } catch (ConcurrentModificationException e) {
                System.out.println("You can not modify collection during iterating");
            }
            indexI = oldIndexI;
            indexJ = oldIndexJ;
        }
    }
}
package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class HashTable<T, D> implements Iterator<Structure<T,D>> {
    private final static int SUB_ARRAY_MAX_SIZE = 4;

    private int indexI;
    private int indexJ;
    private int keySize;
    private int keyNumber;
    private ArrayList<ArrayList<Structure<T, D>>> values;
    private Structure<T, D> current;

    public HashTable() {
        values = new ArrayList<>();
        keySize = 8;
        indexI = 0;
        indexJ = -1;
        keyNumber = 0;
        current = null;
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
        list.add(new Structure<T,D>(key, value));
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
        if (keyNumber != hashTable.getKeyNumber()) {
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
        Structure<T,D> structure = null;
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

    private Structure<T,D> findNext(boolean change){
        int newIndexI = indexI;
        int newIndexJ = indexJ;
        Structure<T, D> structure = null;
        while (newIndexI < keySize) {
            newIndexJ++;
            if(newIndexJ > values.get(newIndexI).size() - 1) {
                newIndexJ = -1;
                newIndexI++;
                continue;
            }
            structure = values.get(newIndexI).get(newIndexJ);

            if(structure.key != null) {
                break;
            }
        }
        if(change){
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
        if(current != null) {
            removeByKey(current.key);
        }
    }

    @Override
    public void forEachRemaining(Consumer<? super Structure<T, D>> action) {
        while (hasNext()) {
            next();
            if(current != null && current.key != null) {
                action.accept(current);
            }
        }
    }
}

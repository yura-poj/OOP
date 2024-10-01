package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * hashtable with method of chains (linked arrays),
 * and resizing table with hash function.
 * contain data by their keys, like ("A": 1).
 *
 * @param <T> type of key.
 * @param <D> type of data.
 */
public class HashTable<T, D> implements Iterable<Structure<T, D>> {
    private static final int SUB_ARRAY_MAX_SIZE = 4;

    private int iterationNumber;
    private int keySize;
    private int keyNumber;
    private ArrayList<ArrayList<Structure<T, D>>> values;

    /**
     * constructor.
     */
    public HashTable() {
        values = new ArrayList<>();
        keySize = 8;
        keyNumber = 0;
        iterationNumber = 0;
        for (int i = 0; i < keySize; i++) {
            values.add(new ArrayList<>());
        }
    }

    /**
     * getter.
     *
     * @return iteration number, iterations are put, update, remove.
     */
    public int getIterationNumber() {
        return iterationNumber;
    }

    /**
     * getter.
     *
     * @return key number is number of keys in hashtable
     */
    public int getKeyNumber() {
        return keyNumber;
    }

    /**
     * getter.
     *
     * @return  number of subarrays in array = size of values array.
     */
    public int getKeySize() {
        return keySize;
    }

    /**
     * put a new value with key.
     * if key already exist - code won't put anything.
     *
     * @param key of structure.
     * @param value of structure.
     */
    public void put(T key, D value) {
        iterationNumber++;
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            return;
        }

        keyNumber++;
        int id = hashFunction(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        if (list.size() > SUB_ARRAY_MAX_SIZE) {
            resize();
            id = hashFunction(key, keySize);
        }
        list.add(new Structure<T, D>(key, value));
    }

    /**
     * get value by key from hashtable.
     * if key isn't available - code return null.
     *
     * @param key by which will be research.
     *
     * @return value stored by key.
     */
    public D get(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            return structure.data;
        }
        return null;
    }

    /**
     * remove structure by key.
     * if structure isn't available code won't do anything.
     *
     * @param key that will be removed with structure.
     */
    public void removeByKey(T key) {
        iterationNumber++;
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            keyNumber--;
            structure.key = null;
            structure.data = null;
        }
    }

    /**
     * update value by existing key.
     *
     * @param key that exist already.
     * @param value that should be instead of previous value.
     */
    public void update(T key, D value) {
        iterationNumber++;
        Structure<T, D> structure = getStructure(key);
        if (structure != null) {
            structure.data = value;
        }
    }

    /**
     * check exist key in hashtable or not yet.
     *
     * @param key which should be searched.
     *
     * @return bool contains key or not.
     */
    public boolean contains(T key) {
        Structure<T, D> structure = getStructure(key);
        if (structure == null) {
            return false;
        }
        return true;
    }

    /**
     * convert hashtable to string like (key: data \n).
     *
     * @return string of ready hashtable.
     */
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

    /**
     * print hashtable to system out.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * check equals object and hashtable or not.
     *
     * @param o object which should b compared.
     *
     * @return bool equal or not.
     */
    @Override
    public boolean equals(Object o) {
        Structure<T, D> structure = null;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTable<T, D> hashTable = (HashTable<T, D>) o;

        return (hashCode() == hashTable.hashCode());
    }

    /**
     * search structure in hashtable by key.
     *
     * @param key by which code will search structure.
     *
     * @return structure with the same key.
     */
    private Structure<T, D> getStructure(T key) {
        int id = hashFunction(key, keySize);
        ArrayList<Structure<T, D>> list = values.get(id);
        for (Structure<T, D> structure : list) {
            if (structure.key != null && structure.key.equals(key)) {
                return structure;
            }
        }
        return null;
    }

    /**
     * hash key with their hashfunctions and devide that on size of hashtable.
     *
     * @param key that will be hashed.
     * @param size by which will be devided hash.
     *
     * @return hashcode.
     */
    private int hashFunction(T key, int size) {
        return Math.abs(key.hashCode()) % size;
    }

    /**
     * if some sub array is not fits in subArrayMaxSize that function called.
     * it doubles size of array.
     */
    private void resize() {
        ArrayList<ArrayList<Structure<T, D>>> newValues = new ArrayList<>();
        for (int i = 0; i < keySize * 2; i++) {
            newValues.add(new ArrayList<>());
        }
        rehash(newValues);
        keySize = keySize * 2;
        values = newValues;
    }

    /**
     * when hashtable is resized old values should be on the new places,
     * so we should make new hash for all keys in hashtable,
     * and set up for them new place.
     *
     * @param newValues new array in which will be stored old values.
     */
    private void rehash(ArrayList<ArrayList<Structure<T, D>>> newValues) {
        int id = 0;
        Structure<T, D> structure = null;
        for (int i = 0; i < keySize; i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                structure = values.get(i).get(j);
                if (structure.key == null) {
                    continue;
                }
                id = hashFunction((T) structure.key, keySize);
                newValues.get(id).add(structure);
            }
        }
    }

    /**
     * Iterator of hashtable.
     * Usage: you can make Iterator it = hashtable.iterator,
     * or just call iterator in function:
     * for(Structure struct: hashtable)
     *
     * @return pointer to new class iterator of hashtable.
     */
    @Override
    public Iterator<Structure<T, D>> iterator() {
        return new HashTableIterator();
    }

    /**
     * hashcode of hashtable to compare them.
     * it collects in one code hashcodes of all values and keys,
     * and after that add this own size.
     *
     * @return ready hashcode.
     */
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
        hash += keyNumber;
        return hash;
    }

    /**
     * iterator of hashtable.
     */
    private class HashTableIterator implements Iterator<Structure<T, D>> {
        private Structure<T, D> current;
        private int indexI;
        private int indexJ;
        private int iterationNumber;

        /**
         * constructor.
         */
        public HashTableIterator() {
            indexI = 0;
            indexJ = -1;
            current = null;
            iterationNumber = getIterationNumber();
        }

        /**
         * find next structure in the hashtable.
         *
         * @param change should we change idnexes or not (in case of function hasNext).
         *
         * @return Structure.
         */
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

        /**
         * check whether is more structures in hashtable or not.
         *
         * @return bool = remain structure or not.
         */
        @Override
        public boolean hasNext() {
            checkIteration();
            return findNext(false) != null;
        }

        /**
         * find and return next structure in hashtable.
         *
         * @return structure.
         */
        @Override
        public Structure<T, D> next() {
            checkIteration();
            current = findNext(true);
            return current;
        }

        /**
         * remove structure that was called by next,
         * if it haven't such one, code does nothing.
         */
        @Override
        public void remove() {
            checkIteration();
            if (current != null) {
                removeByKey(current.key);
            }
        }

        /**
         * check if hashtable was changed outside of iteration, in time of iteration.
         * if there was such case throw exception.
         */
        private void checkIteration() {
            if (iterationNumber != getIterationNumber()) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
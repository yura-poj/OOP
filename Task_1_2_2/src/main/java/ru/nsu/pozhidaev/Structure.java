package ru.nsu.pozhidaev;

/**
 * class for uses in hashtable that means structure (key: value).
 *
 * @param <T> type of key.
 * @param <D> type of data.
 */
public class Structure<T, D> {
    T key;
    D data;

    /**
     * constructor.
     *
     * @param key with type T used as key for hashtable.
     * @param data with type D used as data for structure.
     */
    Structure(T key, D data) {
        this.key = key;
        this.data = data;
    }
}

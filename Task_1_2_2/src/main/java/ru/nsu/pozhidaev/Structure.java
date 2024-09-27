package ru.nsu.pozhidaev;

public class Structure<T,D> {
    T key;
    D data;

    Structure(T key, D data) {
        this.key = key;
        this.data = data;
    }
}

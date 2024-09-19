package ru.nsu.pozhidaev;

import java.util.HashMap;

public class Number extends Expression {
    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public double eval(HashMap<String, Double> dict) {
        return number;
    }

    @Override
    public Expression derivative(String str) {
        return new Number(0);
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}

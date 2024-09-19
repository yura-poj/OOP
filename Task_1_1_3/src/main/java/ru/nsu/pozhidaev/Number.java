package ru.nsu.pozhidaev;

public class Number extends Expression {
    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public double evaluate(String str) {
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

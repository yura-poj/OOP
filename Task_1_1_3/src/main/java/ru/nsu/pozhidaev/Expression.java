package ru.nsu.pozhidaev;

public abstract class Expression {

    public abstract double evaluate(String str);

    public abstract Expression derivative(String str);

    public abstract String toString();

    public void print() {
        System.out.println(toString());
    }

    ;

}

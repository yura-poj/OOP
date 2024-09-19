package ru.nsu.pozhidaev;

import java.util.HashMap;

public class Add extends Expression {
    private Expression left;
    private Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) + right.eval(dict);
    }

    @Override
    public Expression derivative(String str) {
        return new Add(left.derivative(str), right.derivative(str));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }
}

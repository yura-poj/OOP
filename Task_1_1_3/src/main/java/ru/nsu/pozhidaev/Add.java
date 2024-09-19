package ru.nsu.pozhidaev;

public class Add extends Expression {
    Expression left;
    Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(String str) {
        return left.evaluate(str) + right.evaluate(str);
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

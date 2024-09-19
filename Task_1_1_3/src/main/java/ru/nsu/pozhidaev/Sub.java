package ru.nsu.pozhidaev;

import java.util.HashMap;

public class Sub extends Expression {
    private Expression left;
    private Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) - right.eval(dict);
    }

    /**
     * @return
     */
    @Override
    public Expression derivative(String str) {
        return new Sub(left.derivative(str), right.derivative(str));
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}

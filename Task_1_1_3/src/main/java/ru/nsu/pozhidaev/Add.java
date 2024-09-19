package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * calculates sum of expressions.
 */
public class Add extends Expression {
    private Expression left;
    private Expression right;

    /**
     * init.
     *
     * @param left  expression.
     * @param right expression.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * evaluate sum of expressions.
     *
     * @param dict of variables.
     * @return result of sum.
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) + right.eval(dict);
    }

    /**
     * derivative sum of expressions.
     *
     * @param str variable of derivation.
     * @return ready expression with derivative elements.
     */
    @Override
    public Expression derivative(String str) {
        return new Add(left.derivative(str), right.derivative(str));
    }

    /**
     * Make a string from sum like (1 + 1).
     *
     * @return string of sum.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }
}

package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * calculates multiplication of expressions.
 */
public class Mul extends Expression {
    private Expression left;
    private Expression right;

    /**
     * init.
     *
     * @param left  expression.
     * @param right expression.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * evaluate multiplication of expressions.
     *
     * @param dict of variables.
     * @return result of multiplication.
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) * right.eval(dict);
    }

    /**
     * derivative multiplication of expressions.
     *
     * @param str variable of derivation.
     * @return ready expression with derivatived elements.
     */
    @Override
    public Expression derivative(String str) {
        return new Add(new Mul(left.derivative(str), right), new Mul(left, right.derivative(str)));
    }

    /**
     * Make a string from multiplication like (1 + 1).
     *
     * @return string of multiplication.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}

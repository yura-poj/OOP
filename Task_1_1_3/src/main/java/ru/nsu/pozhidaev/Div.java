package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * calculates division of expressions.
 */
public class Div extends Expression {
    private Expression left;
    private Expression right;

    /**
     * init.
     *
     * @param left  expression.
     * @param right expression.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * evaluate division of expressions.
     *
     * @param dict of variables.
     * @return result of division.
     */

    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) / right.eval(dict);
    }

    /**
     * derivative division of expressions.
     *
     * @param str variable of derivation.
     * @return ready expression with derivatived elements.
     */

    @Override
    public Expression derivative(String str) {
        return new Div(
                new Sub(new Mul(left.derivative(str), right),
                        new Mul(left, right.derivative(str))),
                new Mul(right, right));
    }

    /**
     * Make a string from division like (1 + 1).
     *
     * @return string of division.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " / " + right.toString() + ")";
    }
}

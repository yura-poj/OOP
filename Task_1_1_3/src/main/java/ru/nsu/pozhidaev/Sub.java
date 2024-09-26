package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * calculates subtraction of expressions.
 */
public class Sub extends Expression {
    private Expression left;
    private Expression right;

    /**
     * init.
     *
     * @param left  expression.
     * @param right expression.
     */

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * evaluate sum of expressions.
     *
     * @param dict of variables.
     * @return result of subtraction.
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return left.eval(dict) - right.eval(dict);
    }

    /**
     * derivative subtraction of expressions.
     *
     * @param str variable of derivation.
     * @return ready expression with derivatived elements.
     */
    @Override
    public Expression derivative(String str) {
        return new Sub(left.derivative(str), right.derivative(str));
    }

    /**
     * Make a string from subtraction like (1 - 1).
     *
     * @return string of subtraction.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}

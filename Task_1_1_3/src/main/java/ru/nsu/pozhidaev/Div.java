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

    /**
     * simplify the expression.
     *
     * @return simplified expression.
     */
    @Override
    public Expression simlify() {
        if (left.equals(right)) {
            return new Number(1);
        }
        if (right.equals(new Number(1))) {
            return left.simlify();
        }
        Expression leftSimple = left.simlify();
        Expression rightSimple = right.simlify();

        if (leftSimple.getClass() == Number.class && rightSimple.getClass() == Number.class) {
            return new Number(leftSimple.eval(new HashMap<>()) / rightSimple.eval(new HashMap<>()));
        }
        return new Div(left.simlify(), right.simlify());
    }

    /**
     * equals or not.
     *
     * @param other object.
     * @return true or false, equals or not.
     */
    @Override
    public boolean equals(Expression other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        return other.hashCode() == this.hashCode();
    }

    /**
     * hashcode.
     *
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return "div".hashCode() + left.hashCode() + right.hashCode();
    }
}

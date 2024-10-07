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

    /**
     * simplify the expression.
     *
     * @return simplified expression.
     */
    @Override
    public Expression simlify() {
        if (left.equals(new Number(0))) {
            return right.simlify();
        }
        if (right.equals(new Number(0))) {
            return left.simlify();
        }
        Expression leftSimple = left.simlify();
        Expression rightSimple = right.simlify();

        if (leftSimple.getClass() == Number.class && rightSimple.getClass() == Number.class) {
            return new Number(leftSimple.eval(new HashMap<>()) + rightSimple.eval(new HashMap<>()));
        }
        return new Add(left.simlify(), right.simlify());
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
        return "add".hashCode() + left.hashCode() + right.hashCode();
    }
}

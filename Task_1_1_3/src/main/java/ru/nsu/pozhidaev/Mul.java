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

    /**
     * simplify the expression.
     *
     * @return simplified expression.
     */
    @Override
    public Expression simlify() {
        if (left.equals(new Number(1))) {
            return right.simlify();
        }
        if (right.equals(new Number(1))) {
            return left.simlify();
        }
        if (left.equals(new Number(0)) || right.equals(new Number(0))) {
            return new Number(0);
        }
        Expression leftSimple = left.simlify();
        Expression rightSimple = right.simlify();

        if (leftSimple.getClass() == Number.class && rightSimple.getClass() == Number.class) {
            return new Number(leftSimple.eval(new HashMap<>()) * rightSimple.eval(new HashMap<>()));
        }
        return new Mul(left.simlify(), right.simlify());
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
        return "mul".hashCode() + left.hashCode() + right.hashCode();
    }
}

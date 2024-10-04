package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * object of number.
 */
public class Number extends Expression {
    private double number;

    /**
     * init.
     *
     * @param number double.
     */
    public Number(double number) {
        this.number = number;
    }

    /**
     * evaluate value of expressions.
     *
     * @param dict of variables.
     * @return value.
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return number;
    }

    /**
     * derivative value of expression.
     *
     * @param str variable of derivation.
     * @return derivative element.
     */
    @Override
    public Expression derivative(String str) {
        return new Number(0);
    }

    /**
     * Make a string from number like 1.
     *
     * @return string of number.
     */
    @Override
    public String toString() {
        return Double.toString(number);
    }

    /**
     * simplify the expression.
     *
     * @return simplified expression.
     */
    @Override
    public Expression simlify() {
        return this;
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
        return Double.hashCode(number);
    }
}

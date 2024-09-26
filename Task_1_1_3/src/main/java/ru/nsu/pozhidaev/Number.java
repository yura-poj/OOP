package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * object of number.
 */
public class Number extends Expression {
    private int number;

    /**
     * init.
     *
     * @param number double.
     */
    public Number(int number) {
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
        return Integer.toString(number);
    }
}

package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * calculates sum of expressions.
 */
public class Variable extends Expression {
    private String name;

    /**
     * init.
     *
     * @param name of variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * evaluate value of expression.
     *
     * @param dict of variables.
     * @return value of variable.
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return dict.get(name);
    }

    /**
     * derivative sum of expressions.
     *
     * @param str variable of derivation.
     * @return ready expression with derivative elements.
     */
    @Override
    public Expression derivative(String str) {
        if (name.equals(str)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Make a string from variable.
     *
     * @return name.
     */
    @Override
    public String toString() {
        return name;
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
        return name.hashCode();
    }
}

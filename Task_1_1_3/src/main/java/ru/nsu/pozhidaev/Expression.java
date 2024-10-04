package ru.nsu.pozhidaev;

import java.util.HashMap;

/**
 * abstract class for expressions.
 */
public abstract class Expression {

    /**
     * evaluate result of expression to double.
     *
     * @param dict of variables.
     * @return double - result of expression.
     */
    public abstract double eval(HashMap<String, Double> dict);

    /**
     * derivative expression to another expression.
     *
     * @param str variable of derivative.
     * @return new derivatived expression.
     */
    public abstract Expression derivative(String str);

    /**
     * transform expression to string includes all dependencies .
     *
     * @return string of expression like.
     */
    public abstract String toString();

    /**
     * simplify the expression.
     *
     * @return simplified expression.
     */
    public abstract Expression simlify();

    /**
     * equals or not.
     *
     * @param other object.
     * @return true or false, equals or not.
     */
    public abstract boolean equals(Expression other);

    /**
     * hashcode.
     *
     * @return hashcode.
     */
    public abstract int hashCode();

    /**

     * recall method evaluate with hashmap.
     *
     * @param str variables and its values.
     * @return result of evaluation in double.
     */
    public double evaluate(String str) {
        return eval(stringToMap(str));
    }

    /**
     * convert string with variables and its values to hashmap.
     *
     * @param str string of variables and values.
     * @return ready hashmap.
     */
    private HashMap<String, Double> stringToMap(String str) {
        HashMap<String, Double> map = new HashMap<>();
        String[] values = str.replace(" ", "").split(";");
        for (String value : values) {
            String[] results = value.split("=");
            if (results.length == 2) {
                map.put(results[0], Double.parseDouble(results[1]));
            }
        }
        return map;
    }

    /**
     * print the expression into standart output.
     */
    public void print() {
        System.out.println(this);
    }
}

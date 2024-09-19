package ru.nsu.pozhidaev;

import java.util.HashMap;

public class Variable extends Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    /**
     * @param dict
     * @return
     */
    @Override
    public double eval(HashMap<String, Double> dict) {
        return dict.get(name);
    }

    @Override
    public Expression derivative(String str) {
        if (name.equals(str)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}

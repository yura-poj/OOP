package ru.nsu.pozhidaev;

import java.util.HashMap;

public abstract class Expression {

    public abstract double eval(HashMap <String, Double> dict );

    public abstract Expression derivative(String str);

    public abstract String toString();


    public double evaluate(String str){
        return eval(stringToMap(str));
    }

    private HashMap<String, Double> stringToMap(String str) {
        HashMap<String, Double> map = new HashMap<>();
        String[] values = str.replace(" ", "").split(";");
        for (String value : values) {
            String[] results = value.split("=");
            if (results.length == 2) {
                map.put(results[0], Double.parseDouble(results[1]));
            } else {
                System.out.println("Invalid variable value");
            }
        }
        return map;
    }


    public void print() {
        System.out.println(this);
    }

    ;

}

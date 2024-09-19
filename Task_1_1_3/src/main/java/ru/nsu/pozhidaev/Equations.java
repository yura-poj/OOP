package ru.nsu.pozhidaev;

public class Equations {
    public static void equations() {
        Expression e = new Mul(
                new Add(new Number(2), new Variable("x")),
                new Sub(new Div(new Number(2), new Number(4)), new Number(1)));
        e.print();
        System.out.println( e.evaluate("x = 12"));
        Expression n =  e.derivative("x");
        n.print();
        System.out.println(n.evaluate("x = 12"));
    }
}
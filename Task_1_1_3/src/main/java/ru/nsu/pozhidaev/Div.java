package ru.nsu.pozhidaev;

public class Div extends Expression {
    Expression left;
    Expression right;
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return
     */
    @Override
    public double evaluate(String str) {
        return left.evaluate(str) / right.evaluate(str);
    }

    /**
     * @return
     */
    @Override
    public Expression derivative(String str) {
        return new Div(
                new Sub( new Mul(left.derivative(str), right),
                        new Mul(left, right.derivative(str))),
                new Mul(right, right));
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " / " + right.toString() + ")";
    }
}

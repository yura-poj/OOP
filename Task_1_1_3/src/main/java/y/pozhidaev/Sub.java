package y.pozhidaev;

public class Sub extends Expression {
    Expression left;
    Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return
     */
    @Override
    public double evaluate(String str) {
        return right.evaluate(str) - left.evaluate(str);
    }

    /**
     * @return
     */
    @Override
    public Expression derivative(String str) {
        return new Sub(left.derivative(str), right.derivative(str));
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}

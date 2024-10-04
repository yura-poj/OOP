package ru.nsu.pozhidaev;

import java.util.Stack;

/**
 * Parse string into Expression.
 */
public class Parser {

    private static final String actions = "+-*/";

    /**
     * Remove extra chars and return parsed string.
     *
     * @param input string to parse.
     * @return parsed expression.
     */
    public Expression parse(String input) {
        input = input.replace(" ", "");
        return parseExpression(input);
    }

    /**
     * Parse ready string to expression.
     *
     * @param input string to parse.
     * @return parsed expression.
     */
    private Expression parseExpression(String input) {
        Stack<Expression> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int pos = 0;

        while (pos < input.length()) {
            char current = input.charAt(pos);

            if (Character.isDigit(current) || Character.isLetter(current)) {
                int start = pos;
                while (pos < input.length() && (Character.isDigit(input.charAt(pos))
                        || Character.isLetter(input.charAt(pos)))) {
                    pos++;
                }
                String value = input.substring(start, pos);
                values.push(parseValue(value));
                continue;
            }

            if (current == '(') {
                ops.push(current);
            } else if (current == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    values.push(createAction(ops.pop(), values.pop(), values.pop()));
                }
                if (!ops.isEmpty() && ops.peek() == '(') {
                    ops.pop();
                }
            } else if (actions.indexOf(current) != -1) {
                while (!ops.isEmpty() && ops.peek() != '('
                        && precedence(current) <= precedence(ops.peek())) {
                    values.push(createAction(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(current);
            }
            pos++;
        }

        while (!ops.isEmpty()) {
            values.push(createAction(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    /**
     * Detect variable or number.
     *
     * @param input string of number or variable.
     * @return expression.
     */
    private Expression parseValue(String input) {
        try {
            int number = Integer.parseInt(input);
            return new Number(number);
        } catch (NumberFormatException ex) {
            return new Variable(input);
        }
    }

    /**
     * Create action based on char operation.
     *
     * @param action char of operation.
     * @param right  expression.
     * @param left   expression.
     * @return combine expression with action.
     */
    private Expression createAction(char action, Expression right, Expression left) {
        switch (action) {
            case '+':
                return new Add(left, right);
            case '-':
                return new Sub(left, right);
            case '*':
                return new Mul(left, right);
            case '/':
                return new Div(left, right);
            default:
                throw new IllegalArgumentException("Unknown operation: " + action);
        }
    }

    /**
     * Detect precedence of actions.
     *
     * @param op operation to detect.
     * @return number of precedence.
     */
    private int precedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        }
        return 0;
    }
}

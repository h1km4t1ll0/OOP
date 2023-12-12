package ru.nsu.dolgov.calculator;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Class for expression implementation.
 * Its purpose is to store the deque and provide an API to access it
 */
public class Expression {
    private final Deque<Double> stack;
    private final String[] rawTokens;

    public Expression(String[] parsedExpression) {
        this.stack = new LinkedList<>();
        this.rawTokens = parsedExpression;
    }

    /**
     * Method to check whether token is number or not.
     *
     * @param token string token.
     * @return true if number false otherwise.
     */
    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method to check whether token is operand or not.
     *
     * @param token string token.
     * @return true if operand false otherwise.
     */
    public static boolean isOperand(String token) {
        try {
            Operations.getOperation(token);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Getter for the tokens collected from user.
     *
     * @return String[] tokens.
     */
    public String[] getRawTokens() {
        return this.rawTokens;
    }

    /**
     * Method to push the data to an internal deque.
     *
     * @param expression expression to be pushed.
     */
    public void push(Double expression) {
        this.stack.push(expression);
    }


    /**
     * Method to get the data from the internal deque.
     *
     * @return Double value.
     */
    public Double pop() {
        return this.stack.pop();
    }
}

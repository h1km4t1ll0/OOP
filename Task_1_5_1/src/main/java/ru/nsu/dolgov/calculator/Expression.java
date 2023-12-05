package ru.nsu.dolgov.calculator;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Class for expression implementation.
 */
public class Expression {
    public final Deque<Double> stack;
    public String[] rawTokens;

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
}

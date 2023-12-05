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

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

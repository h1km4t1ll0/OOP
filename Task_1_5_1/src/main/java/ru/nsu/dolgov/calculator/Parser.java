package ru.nsu.dolgov.calculator;

import java.util.Arrays;
import java.util.List;

/**
 * Class for parser implementation.
 */
public class Parser {
    public final List<String> availableTokens = List.of(
            new String[]{
                    "*", "+", "-", "/",
                    "sin", "cos", "log",
                    "pow", "sqrt"
            }
    );
    private final String stringExpression;

    public Parser(String stringExpression) {
        this.stringExpression = stringExpression;
    }

    /**
     * Parses string to Expression.
     *
     * @return Expression.
     */
    public Expression parse() {
        String[] tokenizedStringExpression = this.stringExpression.split("\\s+");
        return new Expression(tokenizedStringExpression);
    }

    /**
     * Checks if input could be parsed or not.
     *
     * @return true if could else otherwise.
     */
    public boolean check() {
        return Arrays.stream(this.stringExpression.split("\\s+")).allMatch(
                availableTokens::contains
        );
    }
}

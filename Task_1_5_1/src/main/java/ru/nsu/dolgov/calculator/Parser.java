package ru.nsu.dolgov.calculator;

/**
 * Class for parser implementation.
 */
public class Parser {
    private final String stringExpression;

    public Parser(String stringExpression) {
        this.stringExpression = stringExpression;
    }

    public Expression parse() {
        String[] tokenizedStringExpression = this.stringExpression.split("\\s+");
        return new Expression(tokenizedStringExpression);
    }
}

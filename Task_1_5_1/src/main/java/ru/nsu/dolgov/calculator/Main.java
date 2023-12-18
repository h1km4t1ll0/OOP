package ru.nsu.dolgov.calculator;

import java.util.Scanner;

/**
 * An entrypoint for a calculator.
 */
public class Main {
    /**
     * An entrypoint.
     */
    public static void main() {
        try {
            System.out.println("Enter the expression:");
            System.out.println("Output: " + calculate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method for calculating the result.
     *
     * @return double value.
     * @throws Exception while parsing or handling the expression.
     */
    public static double calculate() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        scanner.close();
        Parser parser = new Parser(inputString);

        if (parser.check()) {
            throw new Exception("Invalid argument!");
        }

        Expression expression = parser.parse();
        Calculator calculator = new Calculator(expression);
        return calculator.calculate();
    }

    /**
     * Method for calculating the result.
     *
     * @param expression expression to be parsed.
     * @return double value.
     * @throws Exception when parsing is not completed.
     */
    public static double calculate(String expression) throws Exception {
        Parser parser = new Parser(expression);

        if (parser.check()) {
            throw new Exception("Invalid argument!");
        }

        Expression parsedExpression = parser.parse();
        Calculator calculator = new Calculator(parsedExpression);
        return calculator.calculate();
    }
}

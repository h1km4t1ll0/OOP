package ru.nsu.dolgov.calculator;

import java.util.Scanner;

/**
 * An entrypoint for a calculator.
 */
public class Main {
    /**
     * An entrypoint.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
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
        Expression expression = parseInput();
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

        Expression tokens = parser.parse();
        Calculator calculator = new Calculator(tokens);
        return calculator.calculate();
    }


    /**
     * Method for parsing input.
     *
     * @return Expression.
     * @throws Exception when parsing wasn't done.
     */
    public static Expression parseInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        scanner.close();
        Parser parser = new Parser(inputString);

        if (parser.check()) {
            throw new Exception("Invalid argument!");
        }

        return parser.parse();
    }
}

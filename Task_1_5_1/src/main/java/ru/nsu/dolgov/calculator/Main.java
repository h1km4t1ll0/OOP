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
     * Class for calculating the result.
     *
     * @return double value.
     * @throws Exception while parsing or handling the expression.
     */
    private static double calculate() throws Exception {
        Expression expression = parseInput();
        Calculator calculator = new Calculator(expression);
        return calculator.calculate();
    }

    /**
     * Method for parsing input.
     *
     * @return Expression.
     * @throws Exception when parsing wasn't done.
     */
    private static Expression parseInput() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        scanner.close();
        Parser parser = new Parser(inputString);

        if (!parser.check()) {
            throw new Exception("Invalid argument!");
        }

        return parser.parse();
    }
}

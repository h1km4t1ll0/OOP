package ru.nsu.dolgov.calculator;

import java.util.Scanner;

/**
 * An entrypoint for a calculator.
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Enter the expression:");
            System.out.println("Output: " + calculate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static double calculate() throws Exception {
        Expression expression = parseInput();
        Calculator calculator = new Calculator(expression);
        return calculator.calculate();
    }

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

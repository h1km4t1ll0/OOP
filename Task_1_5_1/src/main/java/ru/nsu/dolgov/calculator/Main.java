package ru.nsu.dolgov.calculator;

import java.util.Scanner;

/**
 * An entrypoint for a calculator.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the expression:");
        System.out.println("Output: " + calculate());
    }

    private static double calculate() {
        Expression expression = parseInput();
        Calculator calculator = new Calculator(expression);
        return calculator.calculate();
    }

    private static Expression parseInput() {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        scanner.close();
        Parser parser = new Parser(inputString);

        return parser.parse();
    }
}

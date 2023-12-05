package ru.nsu.dolgov.calculator;

/**
 * Class for calculator implementation.
 */
public class Calculator {
    private final Expression expression;

    public Calculator(Expression expression) {
        this.expression = expression;
    }

    private void calculateAlgebraicExpression(String operand) {
        switch (operand) {
            case "*":
                this.expression.stack.push(
                        this.expression.stack.pop() * this.expression.stack.pop()
                );
            case "+":
                this.expression.stack.push(
                        this.expression.stack.pop() + this.expression.stack.pop()
                );
            case "-":
                this.expression.stack.push(
                        this.expression.stack.pop() - this.expression.stack.pop()
                );
            case "/": {
                double firstExpression = this.expression.stack.pop();
                double secondExpression = this.expression.stack.pop();
                if (secondExpression == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                this.expression.stack.push(firstExpression / secondExpression);
            }
        }
    }

    private void calculateTrigonometricalExpression(String operand) {
        switch (operand) {
            case "sin" -> this.expression.stack.push(
                    Math.sin(this.expression.stack.pop())
            );
            case "cos" -> this.expression.stack.push(
                    Math.cos(this.expression.stack.pop())
            );
        }
    }

    private void calculateOtherExpression(String operand) {
        switch (operand) {
            case "pow" -> this.expression.stack.push(
                    Math.pow(this.expression.stack.pop(),
                            this.expression.stack.pop())
            );
            case "sqrt" -> {
                double expression = this.expression.stack.pop();
                if (expression < 0) {
                    throw new ArithmeticException("Expression is lower than zero");
                }
                this.expression.stack.push(
                        Math.sqrt(expression)
                );
            }
            case "log" -> {
                double base = this.expression.stack.pop();
                double expression = this.expression.stack.pop();
                if (expression <= 0 || base <= 0 || base == 1) {
                    throw new ArithmeticException("Invalid arguments");
                }
                this.expression.stack.push(
                        Math.log(expression) / Math.log(base)
                );
            }
        }
    }

    private void calculateSingleExpression(String operand) throws ArithmeticException {
        if (
                operand.equals("*") ||
                        operand.equals("+") ||
                        operand.equals("/") ||
                        operand.equals("-")
        ) {
            this.calculateAlgebraicExpression(operand);
        } else if (
                operand.equals("cos") ||
                        operand.equals("sin")
        ) {
            this.calculateTrigonometricalExpression(operand);
        } else {
            this.calculateOtherExpression(operand);
        }
    }

    public double calculate() {
        for (int i = this.expression.rawTokens.length - 1; i >= 0; i--) {
            String token = this.expression.rawTokens[i];
            if (Expression.isNumber(token)) {
                this.expression.stack.push(Double.parseDouble(token));
            } else {
                this.calculateSingleExpression(token);
            }
        }

        return this.expression.stack.pop();
    }
}

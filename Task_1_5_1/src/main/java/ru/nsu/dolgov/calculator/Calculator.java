package ru.nsu.dolgov.calculator;

/**
 * Class for calculator implementation.
 */
public class Calculator {
    private final Expression expression;

    public Calculator(Expression expression) {
        this.expression = expression;
    }

    /**
     * Method for calculating algebraic expressions.
     *
     * @param operand string operand.
     */
    private void calculateAlgebraicExpression(Operations operand) {
        switch (operand) {
            case MUL: {
                this.expression.push(
                        this.expression.pop() * this.expression.pop()
                );
                break;
            }
            case ADD: {
                this.expression.push(
                        this.expression.pop() + this.expression.pop()
                );
                break;
            }
            case SUB: {
                this.expression.push(
                        this.expression.pop() - this.expression.pop()
                );
                break;
            }
            case DIV: {
                double firstExpression = this.expression.pop();
                double secondExpression = this.expression.pop();
                if (secondExpression == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                this.expression.push(firstExpression / secondExpression);
                break;
            }
            default: {

            }
        }
    }

    /**
     * Method for calculating trigonometrical expressions.
     *
     * @param operand string operand.
     */
    private void calculateTrigonometricalExpression(Operations operand) {
        switch (operand) {
            case SIN: {
                this.expression.push(
                        Math.sin(this.expression.pop())
                );
                break;
            }
            case COS: {
                this.expression.push(
                        Math.cos(this.expression.pop())
                );
                break;
            }
            default: {

            }
        }
    }

    /**
     * Method for calculating other expressions.
     *
     * @param operand string operand.
     */
    private void calculateOtherExpression(Operations operand) {
        switch (operand) {
            case POW: {
                this.expression.push(
                        Math.pow(this.expression.pop(),
                                this.expression.pop())
                );
                break;
            }
            case SQRT: {
                double expression = this.expression.pop();
                if (expression < 0) {
                    throw new ArithmeticException("Expression is lower than zero");
                }
                this.expression.push(
                        Math.sqrt(expression)
                );
                break;
            }
            case LOG: {
                double base = this.expression.pop();
                double expression = this.expression.pop();
                if (expression <= 0 || base <= 0 || base == 1) {
                    throw new ArithmeticException("Invalid arguments");
                }
                this.expression.push(
                        Math.log(expression) / Math.log(base)
                );
                break;
            }
            default: {

            }
        }
    }

    /**
     * Method for calculating single expression.
     *
     * @param operand string operand.
     * @throws ArithmeticException if execution wasn't successfully ended.
     */
    private void calculateSingleExpression(Operations operand) throws ArithmeticException {
        if (
                operand.equals(Operations.MUL)
                        || operand.equals(Operations.ADD)
                        || operand.equals(Operations.DIV)
                        || operand.equals(Operations.SUB)
        ) {
            this.calculateAlgebraicExpression(operand);
        } else if (
                operand.equals(Operations.COS)
                        || operand.equals(Operations.SIN)
        ) {
            this.calculateTrigonometricalExpression(operand);
        } else {
            this.calculateOtherExpression(operand);
        }
    }

    /**
     * Method for calculating the result.
     *
     * @return double value.
     */
    public double calculate() {
        String[] tokens = this.expression.getRawTokens();
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (Expression.isNumber(token)) {
                this.expression.push(Double.parseDouble(token));
            } else if (Expression.isOperand(token)) {
                this.calculateSingleExpression(Operations.getOperation(token));
            }
        }

        return this.expression.pop();
    }
}

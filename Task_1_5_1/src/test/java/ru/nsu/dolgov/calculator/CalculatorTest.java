package ru.nsu.dolgov.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Class for Calculator class test.
 */
public class CalculatorTest {

    @Test
    void testMain() {
        Main.main();
        assertTrue(true);
    }

    @Test
    void testCalculate() {
        assertThrows(NoSuchElementException.class, Main::calculate);
    }

    @Test
    void testBasicExpression() throws Exception {
        String expression = "sin + - 1 2 1";
        double result = Main.calculate(expression);
        assertEquals(0, result, 0.0001);
    }

    @Test
    void testLog() throws Exception {
        String expression = "log 2 8";
        double result = Main.calculate(expression);
        assertEquals(3, result, 0.0001);
    }

    @Test
    void testAllOperations() throws Exception {
        String expression = "+ * sin - cos 0 1 / cos 1 4 log 2 8";
        double result = Main.calculate(expression);
        assertEquals(3, result, 0.0001);
    }

    @Test
    void testExpression() throws Exception {
        String expression = "/ * + - + log 2 8 1 6 1 9 3";
        double result = Main.calculate(expression);
        assertEquals(-3, result, 0.0001);
    }

    @Test
    void testAddition() throws Exception {
        String expression = "+ 5 3";
        double result = Main.calculate(expression);
        assertEquals(8, result, 0.0001);
    }

    @Test
    void testSubtraction() throws Exception {
        String expression = "- 5 3";
        double result = Main.calculate(expression);
        assertEquals(2, result, 0.0001);
    }

    @Test
    void testMultiplication() throws Exception {
        String expression = "* 5 3";
        double result = Main.calculate(expression);
        assertEquals(15, result, 0.0001);
    }

    @Test
    void testDivision() throws Exception {
        String expression = "/ 10 2";
        double result = Main.calculate(expression);
        assertEquals(5, result, 0.0001);
    }

    @Test
    void testSineFunction() throws Exception {
        String expression = "sin 0";
        double result = Main.calculate(expression);
        assertEquals(0, result, 0.0001);
    }

    @Test
    void testAdditionDouble() throws Exception {
        String expression = "+ 5.4 3.78";
        double result = Main.calculate(expression);
        assertEquals(9.18, result, 0.0001);
    }

    @Test
    void testSubtractionDouble() throws Exception {
        String expression = "- 5.2 3.89";
        double result = Main.calculate(expression);
        assertEquals(1.31, result, 0.0001);
    }

    @Test
    void testMultiplicationDouble() throws Exception {
        String expression = "* 5.4 3.897";
        double result = Main.calculate(expression);
        assertEquals(21.0438, result, 0.0001);
    }

    @Test
    void testDivisionDouble() throws Exception {
        String expression = "/ 10.25 2.5";
        double result = Main.calculate(expression);
        assertEquals(4.1, result, 0.0001);
    }

    @Test
    void testLogDouble() throws Exception {
        String expression = "log 1.5 2.25";
        double result = Main.calculate(expression);
        assertEquals(2, result, 0.0001);
    }

    @Test
    void testPowDouble() throws Exception {
        String expression = "pow 1.5 2";
        double result = Main.calculate(expression);
        assertEquals(2.25, result, 0.0001);
    }

    @Test
    void testSqrtDouble() throws Exception {
        String expression = "sqrt 2.25";
        double result = Main.calculate(expression);
        assertEquals(1.5, result, 0.0001);
    }

    @Test
    void testCosineFunction() throws Exception {
        String expression = "cos 0";
        double result = Main.calculate(expression);
        assertEquals(1, result, 0.0001);
    }

    @Test
    void testDivisionByZero() {
        String expression = "/ 5 0";
        assertThrows(ArithmeticException.class, () -> Main.calculate(expression));
    }

    @Test
    void testEmptyStackException() {
        String expression = "log 10";
        assertThrows(NoSuchElementException.class, () -> Main.calculate(expression));
    }

    @Test
    void testLogInvalidBaseException() {
        String expression = "log -2 10";
        assertThrows(ArithmeticException.class, () -> Main.calculate(expression));
    }

    @Test
    void testLogInvalidBase2Exception() {
        String expression = "log 1 10";
        assertThrows(ArithmeticException.class, () -> Main.calculate(expression));
    }

    @Test
    void testLogInvalidNumberException() {
        String expression = "log 2 -10";
        assertThrows(ArithmeticException.class, () -> Main.calculate(expression));
    }

    @Test
    void e2e() throws Exception {
        String expression = "pow - + + * sin - cos 0 1 / cos 1 4 "
                + "log 2 8 / * + - + log 2 8 1 6 1 9 3 sqrt 4 2";
        double result = Main.calculate(expression);
        assertEquals(4, result, 0.0001);
    }
}

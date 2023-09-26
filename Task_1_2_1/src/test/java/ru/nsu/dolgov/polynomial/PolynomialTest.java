package ru.nsu.dolgov.polynomial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void checkMain() {
        String[] testArguments = new String[]{"tetsArg"};
        Polynomial.main(testArguments);
        assert (true);
    }
}
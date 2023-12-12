package ru.nsu.dolgov.calculator;

import java.util.Arrays;

/**
 * Enum to handle operations.
 */
public enum Operations {
    COS("cos"),
    SIN("sin"),
    POW("pow"),
    SQRT("sqrt"),
    LOG("log"),
    MUL("*"),
    DIV("/"),
    ADD("+"),
    SUB("-");

    final String alias;

    Operations(String alias) {
        this.alias = alias;
    }

    /**
     * Method to get an operation by an alias from enum.
     *
     * @param alias alias to search for in enum.
     * @return value associated with provided alias.
     * @throws IndexOutOfBoundsException when nothing found by an alias.
     */
    public static Operations getOperation(String alias) throws IndexOutOfBoundsException {
        return Arrays.stream(Operations.values())
                .filter(each -> each.alias.equals(alias))
                .toList()
                .get(0);
    }
}
package ru.nsu.dolgov.calculator;

import java.util.Arrays;

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

    public static Operations getOperation(String alias) throws IndexOutOfBoundsException {
        return Arrays.stream(Operations.values())
                .filter(each -> each.alias.equals(alias))
                .toList()
                .get(0);
    }
}
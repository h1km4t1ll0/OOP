package ru.nsu.dolgov.polynomial;

import jdk.jshell.spi.ExecutionControl;

public class Polynomial {

    private final int[] coefficients;

    public Polynomial(int[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public Polynomial subtract(Polynomial other) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public Polynomial multiply(Polynomial other) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public Polynomial devide(Polynomial other) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public static void main(String[] args) {}
}
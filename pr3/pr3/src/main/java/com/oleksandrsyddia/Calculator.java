package com.oleksandrsyddia;

public class Calculator {

    public double plus(double a, double b) {
        return a + b;
    }    

    public double minus(double a, double b) {
        return a - b;
    }

    public double muliply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Error: number cannot be devided by zero");
        }
        return a / b;
    }

    public double root(double a) throws InvalidInputException {
        if (a < 0) {
            throw new InvalidInputException("Error: root cannot be taken of negative number");
        }
        return Math.sqrt(a);
    }
}

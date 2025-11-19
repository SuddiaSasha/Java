 package com.oleksandrsyddia;


import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scannerNum = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Enter first number: ");
            double firstNum = scannerNum.nextDouble(); 
            scannerNum.nextLine();

            System.out.print("Enter operation *type only +, -, *, /, root*: ");
            String operation = scannerNum.nextLine();

            double result = 0;

            if (operation.equals("root")) {
                result = calculator.root(firstNum);
                System.out.println("Result: " + result);
                return;
            }

            System.out.print("Enter second number: ");
            double secondNum = scannerNum.nextDouble();

            switch (operation) {
                case "+":
                    result = calculator.plus(firstNum, secondNum);
                    break;
                case "-": 
                    result = calculator.minus(firstNum, secondNum);
                    break;
                case "*":
                    result = calculator.muliply(firstNum, secondNum);
                    break;
                case "/":
                    result = calculator.divide(firstNum, secondNum);
                    break;
                default:
                    System.out.println("Error: unknown operation");
                    return;
            }
        System.out.print("Result: " + result);

        }catch (InputMismatchException e) {
            System.err.println("Error: you have to enter only numbers");
        } catch (ArithmeticException e) {
            System.err.println("Arithmetic error: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            System.out.println("\nEnd of program");
            scannerNum.close();
        }
    }
}  
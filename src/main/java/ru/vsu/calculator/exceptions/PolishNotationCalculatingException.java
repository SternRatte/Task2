package ru.vsu.calculator.exceptions;

public class PolishNotationCalculatingException extends Exception {
    public PolishNotationCalculatingException() {
    }

    public PolishNotationCalculatingException(String message) {
        super(message);
    }
}

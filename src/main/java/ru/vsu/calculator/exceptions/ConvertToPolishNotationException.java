package ru.vsu.calculator.exceptions;

public class ConvertToPolishNotationException extends PolishNotationCalculatingException {
    public ConvertToPolishNotationException() {
        }

    public ConvertToPolishNotationException(String message) {
            super(message);
        }
}

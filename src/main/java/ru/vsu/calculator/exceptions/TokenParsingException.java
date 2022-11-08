package ru.vsu.calculator.exceptions;

public class TokenParsingException extends Exception {
    public TokenParsingException() {
    }

    public TokenParsingException(String message) {
        super(message);
    }
}

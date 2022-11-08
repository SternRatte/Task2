package ru.vsu.calculator.exceptions;

public class NotEnoughArgumentsException extends PolishNotationCalculatingException{
    public NotEnoughArgumentsException() {
    }

    public NotEnoughArgumentsException(String message) {
        super(message);
    }
}

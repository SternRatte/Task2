package ru.vsu.calculator.parsing;

public interface TokenBuilderFactory {
    TokenBuilder createBuilder(int startPosition, String expression);
}

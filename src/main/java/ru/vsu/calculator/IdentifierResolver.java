package ru.vsu.calculator;

import java.util.Stack;
import ru.vsu.calculator.tokens.ValueToken;
import ru.vsu.calculator.exceptions.PolishNotationCalculatingException;

public interface IdentifierResolver {
    double resolveVariable(ValueToken<String> token);

    double resolveFunction(ValueToken<String> token, Stack<Double> allOperands) throws PolishNotationCalculatingException;
}

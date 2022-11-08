package calculator;

import java.util.Stack;
import calculator.tokens.ValueToken;
import calculator.exceptions.*;

public interface IdentifierResolver {
    double resolveVariable(ValueToken<String> token);

    double resolveFunction(ValueToken<String> token, Stack<Double> allOperands) throws PolishNotationCalculatingException;
}

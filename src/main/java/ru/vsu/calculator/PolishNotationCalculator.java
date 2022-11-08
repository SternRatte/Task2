package ru.vsu.calculator;

import java.util.*;
import ru.vsu.calculator.exceptions.ConvertToPolishNotationException;
import ru.vsu.calculator.exceptions.NotEnoughArgumentsException;
import ru.vsu.calculator.exceptions.NotRecognizedTokenException;
import ru.vsu.calculator.exceptions.PolishNotationCalculatingException;
import ru.vsu.calculator.tokens.OperatorToken;
import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;
import ru.vsu.calculator.tokens.ValueToken;

public class PolishNotationCalculator {
    private final IdentifierResolver _identifierResolver;

    public PolishNotationCalculator(IdentifierResolver identifierResolver) {
        if (identifierResolver == null)
            throw new IllegalArgumentException("IdentifierResolver can't be null.");

        _identifierResolver = identifierResolver;
    }

    @SuppressWarnings("unchecked")
    public double calculate(Iterable<Token> tokens) throws PolishNotationCalculatingException {
        if (tokens == null)
            throw new IllegalArgumentException("Tokens can't be null.");

        Stack<Double> operands = new Stack<Double>();
        for (Token token : tokens) {
            switch (token.getTokenType()) {
                case CONSTANT:
                case VARIABLE:
                    try {
                        double value = token.getTokenType() == TokenType.VARIABLE
                                ? _identifierResolver.resolveVariable((ValueToken<String>) token)
                                : ((ValueToken<Double>) token).getValue();

                        operands.push(value);
                    } catch (ClassCastException ex) {
                        throw new PolishNotationCalculatingException("Failed to cast token to ValueToken.");
                    }
                    break;
                case OPERATOR:
                    if (!(token instanceof OperatorToken))
                        throw new ConvertToPolishNotationException("Failed to cast token of type OPERATOR to OperatorToken.");

                    OperatorToken operator = (OperatorToken) token;

                    if (operands.size() < operator.getParamsCount())
                        throw new NotEnoughArgumentsException(String.format("Operator requires %d operands but was %d.",
                                operator.getParamsCount(), operands.size()));

                    List<Double> params = new ArrayList<Double>();

                    for (int i = 0; i < operator.getParamsCount(); ++i)
                        params.add(operands.pop());

                    operands.push(operator.apply(params));
                    break;
                case FUNCTION:
                    try {
                        operands.push(_identifierResolver.resolveFunction((ValueToken<String>) token, operands));
                    } catch (ClassCastException ex) {
                        throw new PolishNotationCalculatingException("Failed to cast token to ValueToken.");
                    }
                    break;
                default:
                    throw new NotRecognizedTokenException(token);
            }
        }

        if (operands.size() != 1)
            throw new PolishNotationCalculatingException("Operators stack has more than one operator.");

        return operands.pop();
    }
}

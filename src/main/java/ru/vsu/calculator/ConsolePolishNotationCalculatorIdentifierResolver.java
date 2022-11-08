package calculator;

import calculator.parsing.TokenBuilderFactory;
import calculator.tokens.*;
import java.util.*;
import calculator.exceptions.*;

public class ConsolePolishNotationCalculatorIdentifierResolver implements IdentifierResolver {
    private final Map<String, Double> _localVariables;
    private final List<TokenBuilderFactory> _tokenBuilderFactories;

    public ConsolePolishNotationCalculatorIdentifierResolver(Map<String, Double> localVariables, List<TokenBuilderFactory> tokenBuilderFactories) {
        if (tokenBuilderFactories == null)
            throw new IllegalArgumentException("Calculator can't be null.");

        _localVariables = localVariables == null ? new Hashtable<String, Double>() : localVariables;
        _tokenBuilderFactories = tokenBuilderFactories;
    }

    public ConsolePolishNotationCalculatorIdentifierResolver(List<TokenBuilderFactory> tokenBuilderFactories) {
        this(null, tokenBuilderFactories);
    }

    @Override
    public double resolveVariable(ValueToken<String> token) {
        String variableName = token.getValue();

        if (_localVariables.containsKey(variableName))
            return _localVariables.get(variableName);

        Double value = readDouble(String.format("Input variable %s: ", variableName));

        _localVariables.put(variableName, value);

        return value;
    }

    @SuppressWarnings("resource")
    private Double readDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                String line = scanner.nextLine();
                return Double.parseDouble(line);
            } catch (Exception ex) {
                System.out.println("Unable to read Double from input.");
            }
        }
    }

    @SuppressWarnings("resource")
    private Integer readInteger(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (Exception ex) {
                System.out.println("Unable to read Integer from input.");
            }
        }
    }

    @SuppressWarnings("resource")
    private Iterable<Token> readTokens(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                String line = scanner.nextLine();
                return new TokenIterable(line, _tokenBuilderFactories);
            } catch (Exception ex) {
                System.out.println("Unable to read Tokens from input.");
            }
        }
    }

    @Override
    public double resolveFunction(ValueToken<String> token, Stack<Double> allOperands) throws PolishNotationCalculatingException {
        String functionName = token.getValue();
        int paramsCount = readInteger(String.format("Input params count for function %s: ", functionName));

        if (paramsCount > allOperands.size())
            throw new NotEnoughArgumentsException(String.format("Function requires %d operands but was %d.", paramsCount, allOperands.size()));

        Map<String, Double> paramsVariables = new Hashtable<String, Double>();
        for (int i = 0; i < allOperands.size(); ++i) {
            String paramName = String.format("a%d", i);
            paramsVariables.put(paramName, allOperands.pop());
        }

        Iterable<Token> tokens = ToPolishNotationConverter.convert(readTokens("Input expression: "));
        PolishNotationCalculator calculator = new PolishNotationCalculator(new ConsolePolishNotationCalculatorIdentifierResolver(paramsVariables, _tokenBuilderFactories));

        return calculator.calculate(tokens);
    }
}

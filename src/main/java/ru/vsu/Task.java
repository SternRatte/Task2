package ru.vsu;

import ru.vsu.calculator.parsing.*;
import ru.vsu.calculator.ConsolePolishNotationCalculatorIdentifierResolver;
import ru.vsu.calculator.PolishNotationCalculator;
import ru.vsu.calculator.ToPolishNotationConverter;
import ru.vsu.calculator.TokenIterable;
import ru.vsu.calculator.exceptions.PolishNotationCalculatingException;
import ru.vsu.calculator.tokens.Token;

import java.util.*;

public class Task {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws PolishNotationCalculatingException {
        try {
            List<TokenBuilderFactory> arr = new ArrayList<TokenBuilderFactory>();
            arr.add(new NumberTokenBuilderFactory('.'));
            arr.add(new OperatorTokenBuilderFactory());
            arr.add(new IdentifierTokenBuilderFactory());
            arr.add(new OpenBracketTokenBuilderFactory());
            arr.add(new CloseBracketTokenBuilderFactory());
            arr.add(new FunctionParamsSeparatorTokenBuilderFactory());

            System.out.print("Read expression: ");
            Scanner scanner = new Scanner(System.in);
            TokenIterable it = new TokenIterable(scanner.nextLine(), arr);
            Iterable<Token> tokens = ToPolishNotationConverter.convert(it);

            PolishNotationCalculator calculator = new PolishNotationCalculator(new ConsolePolishNotationCalculatorIdentifierResolver(arr));

            System.out.println(calculator.calculate(tokens));
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

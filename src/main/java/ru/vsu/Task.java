import calculator.exceptions.*;
import calculator.parsing.*;
import calculator.tokens.*;
import calculator.*;

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

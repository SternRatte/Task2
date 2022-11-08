package calculator.parsing;

public interface TokenBuilderFactory {
    TokenBuilder createBuilder(int startPosition, String expression);
}

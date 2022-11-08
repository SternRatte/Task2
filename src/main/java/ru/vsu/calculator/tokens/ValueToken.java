package calculator.tokens;

public class ValueToken <T> extends Token {
    private final T _value;

    public ValueToken(T value, TokenType tokenType) {
        super(tokenType);
        _value = value;
    }

    public T getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return _value != null ? _value.toString() : super.toString();
    }
}

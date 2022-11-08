package calculator.tokens;

public class Token {
    private final TokenType _tokenType;

    public Token(TokenType tokenType) {
        _tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return _tokenType;
    }
}

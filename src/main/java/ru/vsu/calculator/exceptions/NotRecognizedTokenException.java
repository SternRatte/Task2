package calculator.exceptions;

import calculator.tokens.Token;

public class NotRecognizedTokenException extends ConvertToPolishNotationException {
    private final Token _token;

    public NotRecognizedTokenException(Token token) {
        super("Unable to recognize token.");

        if (token == null)
            throw new IllegalArgumentException("Token can't be null.");

        _token = token;
    }

    public Token getToken() {
        return _token;
    }
}

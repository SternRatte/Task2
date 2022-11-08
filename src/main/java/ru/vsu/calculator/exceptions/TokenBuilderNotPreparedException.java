package calculator.exceptions;

public class TokenBuilderNotPreparedException extends TokenParsingException {
    public TokenBuilderNotPreparedException() {
    }

    public TokenBuilderNotPreparedException(String message) {
        super(message);
    }
}

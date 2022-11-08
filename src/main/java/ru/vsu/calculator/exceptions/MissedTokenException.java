package calculator.exceptions;

public class MissedTokenException extends ConvertToPolishNotationException {
    public MissedTokenException() {
    }

    public MissedTokenException(String message) {
        super(message);
    }
}

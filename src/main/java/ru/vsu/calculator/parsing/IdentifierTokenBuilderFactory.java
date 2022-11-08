package ru.vsu.calculator.parsing;

import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;
import ru.vsu.calculator.tokens.ValueToken;

public class IdentifierTokenBuilderFactory implements TokenBuilderFactory {
    @Override
    public TokenBuilder createBuilder(int startPosition, String expression) {
        return new IdentifierTokenBuilder(startPosition, expression);
    }

    private class IdentifierTokenBuilder extends TokenBuilder {
        public IdentifierTokenBuilder(int startPosition, String expression) {
            super(startPosition, expression);
        }

        @Override
        protected void internalPrepare() {
            if (_endPosition >= _expression.length())
                return;

            char firstChar = _expression.charAt(_endPosition);

            if (!Character.isAlphabetic(firstChar))
                return;

            ++_endPosition;

            while (true)
            {
                if (_endPosition >= _expression.length())
                    return;

                char currentChar = _expression.charAt(_endPosition);
                if (!Character.isAlphabetic(currentChar) && !Character.isDigit(currentChar))
                    return;

                ++_endPosition;
            }
        }

        @Override
        protected Token internalBuild() {
            boolean isFunction = _endPosition < _expression.length() && _expression.charAt(_endPosition) == '(';
            return new ValueToken<String>(getRawToken(), isFunction ? TokenType.FUNCTION : TokenType.VARIABLE);
        }
    }
}

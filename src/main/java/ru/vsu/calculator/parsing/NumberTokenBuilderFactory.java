package calculator.parsing;

import calculator.tokens.*;

public class NumberTokenBuilderFactory implements TokenBuilderFactory {
    private final char _decimalSeparator;

    public NumberTokenBuilderFactory(char decimalSeparator) {
        _decimalSeparator = decimalSeparator;
    }

    @Override
    public TokenBuilder createBuilder(int startPosition, String expression) {
        return new NumberTokenBuilder(_decimalSeparator, startPosition, expression);
    }

    private class NumberTokenBuilder extends TokenBuilder {
        private final char _decimalSeparator;

        public NumberTokenBuilder(char decimalSeparator, int startPosition, String expression) {
            super(startPosition, expression);
            _decimalSeparator = decimalSeparator;
        }

        @Override
        protected void internalPrepare() {
            if (_endPosition >= _expression.length())
                return;

            char firstChar = _expression.charAt(_endPosition);

            if (!Character.isDigit(firstChar))
                return;

            ++_endPosition;

            int dotsCount = 0;
            while (true)
            {
                if (_endPosition >= _expression.length())
                    return;

                char currentChar = _expression.charAt(_endPosition);
                boolean isDigit = Character.isDigit(currentChar);
                boolean isSeperator = _decimalSeparator == currentChar;

                if (isSeperator)
                    ++dotsCount;

                if ((!isDigit && !isSeperator) || dotsCount > 1)
                    return;

                ++_endPosition;
            }
        }

        @Override
        protected Token internalBuild() {
            String rawToken = getRawToken().replace(_decimalSeparator, '.');
            try
            {
                return new ValueToken<Double>(Double.parseDouble(rawToken), TokenType.CONSTANT);
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
}

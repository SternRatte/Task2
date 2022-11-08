package calculator.parsing;

import calculator.tokens.*;

public class CloseBracketTokenBuilderFactory implements TokenBuilderFactory {
    @Override
    public TokenBuilder createBuilder(int startPosition, String expression) {
        return new CloseBracketTokenBuilder(startPosition, expression);
    }

    private class CloseBracketTokenBuilder extends OneSymbolBuilder {
        public CloseBracketTokenBuilder(int startPosition, String expression) {
            super(')', startPosition, expression);
        }

        @Override
        protected Token internalBuild() {
            return new Token(TokenType.CLOSE_BRACKET);
        }
    }
}

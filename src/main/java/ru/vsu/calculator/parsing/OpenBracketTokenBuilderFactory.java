package calculator.parsing;

import calculator.tokens.*;

public class OpenBracketTokenBuilderFactory implements TokenBuilderFactory {
    @Override
    public TokenBuilder createBuilder(int startPosition, String expression) {
        return new OpenBracketTokenBuilder(startPosition, expression);
    }

    private class OpenBracketTokenBuilder extends OneSymbolBuilder {
        public OpenBracketTokenBuilder(int startPosition, String expression) {
            super('(', startPosition, expression);
        }

        @Override
        protected Token internalBuild() {
            return new Token(TokenType.OPEN_BRACKET);
        }
    }
}

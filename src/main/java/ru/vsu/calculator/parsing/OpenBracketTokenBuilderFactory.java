package ru.vsu.calculator.parsing;

import ru.vsu.calculator.tokens.*;
import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;

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

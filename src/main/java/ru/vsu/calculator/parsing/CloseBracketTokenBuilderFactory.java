package ru.vsu.calculator.parsing;

import ru.vsu.calculator.tokens.*;
import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;

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

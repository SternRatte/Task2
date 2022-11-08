package ru.vsu.calculator.parsing;

import ru.vsu.calculator.tokens.*;
import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;

public class FunctionParamsSeparatorTokenBuilderFactory implements TokenBuilderFactory {
    @Override
    public TokenBuilder createBuilder(int startPosition, String expression) {
        return new FunctionParamsSeparatorTokenBuilder(startPosition, expression);
    }

    private class FunctionParamsSeparatorTokenBuilder extends OneSymbolBuilder {
        public FunctionParamsSeparatorTokenBuilder(int startPosition, String expression) {
            super(',', startPosition, expression);
        }

        @Override
        protected Token internalBuild() {
            return new Token(TokenType.FUNCTION_PARAMS_SEPARATOR);
        }
    }
}

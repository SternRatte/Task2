package ru.vsu.calculator.parsing;

public abstract class OneSymbolBuilder extends TokenBuilder {
    private final char _symbol;

    public OneSymbolBuilder(char symbol, int startPosition, String expression) {
        super(startPosition, expression);
        _symbol = symbol;
    }

    @Override
    protected void internalPrepare() {
        if (_endPosition >= _expression.length())
            return;

        _endPosition += _expression.charAt(_endPosition) == _symbol ? 1 : 0;
    }
}

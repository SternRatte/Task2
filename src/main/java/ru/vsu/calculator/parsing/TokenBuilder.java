package ru.vsu.calculator.parsing;

import ru.vsu.calculator.exceptions.TokenBuilderNotPreparedException;
import ru.vsu.calculator.tokens.Token;


public abstract class TokenBuilder implements Comparable<TokenBuilder> {
    protected final String _expression;

    protected int _endPosition;

    protected final int _startPosition;

    private boolean _prepared;

    public TokenBuilder(int startPosition, String expression) {
        if (expression == null)
            throw new IllegalArgumentException("Expression can't be null.");

        if (startPosition < 0 || startPosition >= expression.length())
            throw new IllegalArgumentException("Start Position must be greater or equals than zero and less than length.");

        _startPosition = _endPosition = startPosition;
        _expression = expression;
        _prepared = false;
    }

    public int getNextTokenPosition() {
        return _endPosition;
    }

    public final void prepare() {
        if (_prepared)
            return;

        internalPrepare();

        _prepared = true;
    }

    public final Token build() throws TokenBuilderNotPreparedException
    {
        if (!_prepared)
            throw new TokenBuilderNotPreparedException();

        return internalBuild();
    }

    @Override
    public int compareTo(TokenBuilder object) {
        if (object == null)
            throw new IllegalArgumentException("Object can't be null.");

        return Integer.valueOf(_endPosition).compareTo(object._endPosition);
    }

    protected final String getRawToken() {
        return _expression.substring(_startPosition, _endPosition);
    }

    protected abstract void internalPrepare();

    protected abstract Token internalBuild();
}

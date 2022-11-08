package ru.vsu.calculator;

import ru.vsu.calculator.exceptions.TokenBuilderNotPreparedException;
import ru.vsu.calculator.tokens.Token;
import java.util.*;
import ru.vsu.calculator.parsing.TokenBuilder;
import ru.vsu.calculator.parsing.TokenBuilderFactory;

public class TokenIterable implements Iterable<Token> {
    private final String _expression;

    private final Iterable<TokenBuilderFactory> _tokenBuilderFactoryies;

    public TokenIterable(String expression, Iterable<TokenBuilderFactory> tokenBuilderFactories) {
        if (expression == null)
            throw new IllegalArgumentException("Expression can't be null.");

        if (tokenBuilderFactories == null)
            throw new IllegalArgumentException("TokenBuilderFactories can't be null.");

        _expression = expression.trim();
        _tokenBuilderFactoryies = tokenBuilderFactories;
    }

    @Override
    public Iterator<Token> iterator() {
        return new TokenIterator(_expression, _tokenBuilderFactoryies);
    }

    private final class TokenIterator implements Iterator<Token> {
        private final String _expression;

        private int _currentPosition;

        private final Iterable<TokenBuilderFactory> _tokenBuilderFactories;

        public TokenIterator(String expression, Iterable<TokenBuilderFactory> tokenBuilderFactories) {
            _expression = expression;
            _tokenBuilderFactories = tokenBuilderFactories;
            _currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return _currentPosition < _expression.length();
        }

        @Override
        public Token next() {
            if (!hasNext())
                throw new NoSuchElementException();

            while (_expression.charAt(_currentPosition) == ' ')
                ++_currentPosition;

            List<TokenBuilder> builders = createBuilders(_currentPosition, _expression);

            TokenBuilder bestBuilder = Collections.max(builders);
            int nextPostition = bestBuilder.getNextTokenPosition();

            if (_currentPosition == nextPostition)
                throw new NoSuchElementException(String.format("Unrecognized token in position %d", _currentPosition));

            Token token = null;
            try {
                token = bestBuilder.build();
            } catch (TokenBuilderNotPreparedException e) {
            }

            _currentPosition = nextPostition;

            return token;
        }

        private List<TokenBuilder> createBuilders(int currentPosition, String expression) {
            List<TokenBuilder> builders = new ArrayList<TokenBuilder>();

            for (TokenBuilderFactory factory : _tokenBuilderFactories) {
                TokenBuilder builder = factory.createBuilder(_currentPosition, _expression);
                builder.prepare();
                builders.add(builder);
            }

            return builders;
        }
    }
}

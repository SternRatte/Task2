package ru.vsu;

import org.junit.Test;
import ru.vsu.calculator.tokens.ValueToken;
import ru.vsu.calculator.exceptions.TokenBuilderNotPreparedException;
import ru.vsu.calculator.parsing.*;
import static org.junit.Assert.*;

public class NumberTokenBuilderFactoryTests {
    @Test
    @SuppressWarnings("unchecked")
    public void successfulNumberBuildTest() throws TokenBuilderNotPreparedException {
        String expression = "12.034";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('.');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(expression.length(), builder.getNextTokenPosition());
        assertEquals(12.034, token.getValue(), 0.001);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void numberBuildWithOperatorAtEndTest() throws TokenBuilderNotPreparedException {
        String expression = "12.034+7.031";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('.');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(expression.indexOf('+'), builder.getNextTokenPosition());
        assertEquals(12.034, token.getValue(), 0.001);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void numberBuildWithTwoSeparatorsTest() throws TokenBuilderNotPreparedException {
        String expression = "12.03.4";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('.');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(expression.lastIndexOf('.'), builder.getNextTokenPosition());
        assertEquals(12.03, token.getValue(), 0.001);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void numberBuildVeryLargeNumberTest() throws TokenBuilderNotPreparedException {
        String expression = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('.');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(expression.length(), builder.getNextTokenPosition());
        assertEquals(1.1111111111111112E91, token.getValue(), 1E-91);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void nullNumberTokenBuildTest() throws TokenBuilderNotPreparedException {
        String expression = "function(5, 4)";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('.');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(0, builder.getNextTokenPosition());
        assertNull(token);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void customSeparatorNumberTest() throws TokenBuilderNotPreparedException {
        String expression = "12@034";
        NumberTokenBuilderFactory factory = new NumberTokenBuilderFactory('@');
        TokenBuilder builder = factory.createBuilder(0, expression);
        builder.prepare();

        ValueToken<Double> token = (ValueToken<Double>)builder.build();

        assertEquals(expression.length(), builder.getNextTokenPosition());
        assertEquals(12.034, token.getValue(), 0.001);
    }
}

package calculator.tokens;

import java.util.List;
import java.util.function.Function;

public class OperatorToken extends Token {
    private final Function<List<Double>, Double> _function;

    private final OperatorAssociativity _operatorAssociativity;

    private final int _priority;

    private final int _paramsCount;

    public OperatorToken(Function<List<Double>, Double> function, OperatorAssociativity operatorAssociativity, int priority, int paramsCount) {
        super(TokenType.OPERATOR);

        if (function == null)
            throw new IllegalArgumentException("Function can't be null.");

        if (priority < 0)
            throw new IllegalArgumentException("Priority can't be less than zero.");

        if (paramsCount < 1)
            throw new IllegalArgumentException("ParamsCount can't be less than one.");

        _function = function;
        _operatorAssociativity = operatorAssociativity;
        _priority = priority;
        _paramsCount = paramsCount;
    }

    public OperatorAssociativity getOperatorAssociativity() {
        return _operatorAssociativity;
    }

    public int getPriority() {
        return _priority;
    }

    public int getParamsCount() {
        return _paramsCount;
    }

    public double apply(List<Double> params) {
        if (params.size() != _paramsCount)
            throw new IllegalArgumentException();

        return _function.apply(params);
    }
}

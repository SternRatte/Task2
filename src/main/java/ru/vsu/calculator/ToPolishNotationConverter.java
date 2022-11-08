package ru.vsu.calculator;

import java.util.*;
import ru.vsu.calculator.exceptions.*;
import ru.vsu.calculator.tokens.*;
import ru.vsu.calculator.exceptions.ConvertToPolishNotationException;
import ru.vsu.calculator.exceptions.MissedTokenException;
import ru.vsu.calculator.exceptions.NotRecognizedTokenException;
import ru.vsu.calculator.tokens.OperatorAssociativity;
import ru.vsu.calculator.tokens.OperatorToken;
import ru.vsu.calculator.tokens.Token;
import ru.vsu.calculator.tokens.TokenType;

public final class ToPolishNotationConverter {
    private ToPolishNotationConverter() {
    }

    public static Iterable<Token> convert(Iterable<Token> infixTokenNotation) throws ConvertToPolishNotationException {
        if (infixTokenNotation == null)
            throw new IllegalArgumentException("InfixTokenNotation can't be null.");

        Queue<Token> outputQueue = new ArrayDeque<Token>();
        Stack<Token> opStack = new Stack<Token>();

        for (Token token : infixTokenNotation) {
            switch (token.getTokenType())
            {
                case CONSTANT:
                case VARIABLE:
                    outputQueue.add(token);
                    break;
                case FUNCTION:
                    opStack.push(token);
                    break;
                case FUNCTION_PARAMS_SEPARATOR:
                    if (!popStackToQueueWhileNotOpenBracket(outputQueue, opStack))
                        throw new MissedTokenException("The function argument separator (comma) is missing in the expression, or the opening bracket is missing.");
                    break;
                case OPERATOR:
                    if (token instanceof OperatorToken)
                    {
                        OperatorToken op1 = (OperatorToken)token;
                        OperatorToken op2 = tryPeekOperatorToken(opStack);

                        while (op2 != null && (op2.getPriority() == op1.getPriority()
                                ? op1.getOperatorAssociativity() == OperatorAssociativity.LEFT
                                : op2.getPriority() > op1.getPriority())) {
                            outputQueue.add(opStack.pop());
                            op2 = tryPeekOperatorToken(opStack);
                        }

                        opStack.push(op1);
                    }
                    else
                        throw new ConvertToPolishNotationException("Failed to cast token of type OPERATOR to OperatorToken.");
                    break;
                case OPEN_BRACKET:
                    opStack.push(token);
                    break;
                case CLOSE_BRACKET:
                    if (!popStackToQueueWhileNotOpenBracket(outputQueue, opStack))
                        throw new MissedTokenException("The expression is missing an opening bracket.");

                    opStack.pop();

                    if(!opStack.empty() && opStack.peek().getTokenType() == TokenType.FUNCTION)
                        outputQueue.add(opStack.pop());

                    break;
                default:
                    throw new NotRecognizedTokenException(token);
            }
        }

        while (opStack.size() > 0)
        {
            Token token = opStack.pop();

            if (token.getTokenType() == TokenType.OPEN_BRACKET)
                throw new MissedTokenException("The expression is missing an closing bracket.");

            outputQueue.add(token);
        }

        return outputQueue;
    }

    private static final OperatorToken tryPeekOperatorToken(Stack<Token> opStack) throws ConvertToPolishNotationException {
        if (opStack.empty())
            return null;

        Token token = opStack.peek();

        if (token.getTokenType() != TokenType.OPERATOR)
            return null;

        if (token instanceof OperatorToken)
            return (OperatorToken)token;

        throw new ConvertToPolishNotationException("Failed to cast token of type OPERATOR to OperatorToken.");
    }

    private static final boolean popStackToQueueWhileNotOpenBracket(Queue<Token> outputQueue, Stack<Token> opStack) {
        if (opStack.empty())
            return false;

        while (opStack.peek().getTokenType() != TokenType.OPEN_BRACKET)
        {
            outputQueue.add(opStack.pop());

            if (opStack.empty())
                return false;
        }

        return true;
    }
}

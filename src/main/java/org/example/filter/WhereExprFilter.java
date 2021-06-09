package org.example.filter;

import org.example.tree.*;

import java.util.Map;
import java.util.Optional;

public class WhereExprFilter  extends AstVisitor<Boolean, Map<String,Long>> implements RowFilter {

    private Expression expression;

    public WhereExprFilter(Expression expression){
        this.expression = expression;
    }

    public boolean filter(Map<String,Long> row){

        if(expression instanceof ComparisonExpression){
          return visitComparisonExpression((ComparisonExpression) expression, row);
        }

        if(expression instanceof LogicalBinaryExpression){
            return visitLogicalBinaryExpression((LogicalBinaryExpression) expression,row);
        }else{
            throw new UnsupportedOperationException("not implemented");
        }
    }

    @Override
    protected Boolean visitLogicalBinaryExpression(LogicalBinaryExpression node, Map<String, Long> row) {
        Expression left = node.getLeft();
        Expression right = node.getRight();
        boolean leftRet,rightRet;
        if(left instanceof LogicalBinaryExpression){
            leftRet = visitLogicalBinaryExpression((LogicalBinaryExpression) left,row);
        }else if(left instanceof ComparisonExpression){
            leftRet = visitComparisonExpression((ComparisonExpression) left,row);
        }else{
            throw new UnsupportedOperationException("not implemented");
        }

        if(right instanceof LogicalBinaryExpression){
            rightRet = visitLogicalBinaryExpression((LogicalBinaryExpression) right,row);
        }else if(right instanceof ComparisonExpression){
            rightRet = visitComparisonExpression((ComparisonExpression) right,row);
        }else{
            throw new UnsupportedOperationException("not implemented");
        }

        LogicalBinaryExpression.Operator op = node.getOperator();
        switch (op){
            case AND:
                return leftRet && rightRet;
            case OR:
                return leftRet || rightRet;
            default:
                throw new UnsupportedOperationException("not implemented");
        }

    }

    @Override
    protected Boolean visitComparisonExpression(ComparisonExpression node, Map<String, Long> context) {
        Expression left = node.getLeft();
        Expression right = node.getRight();
        if(left instanceof Identifier && right instanceof LongLiteral){
            ComparisonExpression.Operator op = node.getOperator();
            Long leftVal = context.get(((Identifier) left).getValue());
            if(leftVal == null){
                return false;
            }
            Long rightVal = ((LongLiteral) right).getValue();

            switch (op){
                case EQUAL:
                    return leftVal.equals(rightVal);
                case LESS_THAN:
                    return leftVal<rightVal;
                case NOT_EQUAL:
                    return !leftVal.equals(rightVal);
                case GREATER_THAN:
                    return leftVal>rightVal;
                case LESS_THAN_OR_EQUAL:
                    return leftVal<=rightVal;
                case GREATER_THAN_OR_EQUAL:
                    return leftVal>=rightVal;
                default:
                    throw new UnsupportedOperationException("not implemented");
            }

        }else{
            throw new UnsupportedOperationException("not implemented");
        }
    }
}

package org.example.tree;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public final class ExpressionFormatter
{

    public static String formatExpression(Expression expression, Optional<List<Expression>> parameters){
        return new Formatter(parameters).process(expression, null);

    }
    public static class Formatter
            extends AstVisitor<String, Void> {
        private final Optional<List<Expression>> parameters;

        public Formatter(Optional<List<Expression>> parameters) {
            this.parameters = parameters;
        }

        @Override
        protected String visitNode(Node node, Void context) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected String visitExpression(Expression node, Void context)
        {
            throw new UnsupportedOperationException(format("not yet implemented: %s.visit%s", getClass().getName(), node.getClass().getSimpleName()));
        }

        @Override
        protected String visitLongLiteral(LongLiteral node, Void context)
        {
            return Long.toString(node.getValue());
        }

        @Override
        protected String visitComparisonExpression(ComparisonExpression node, Void context)
        {
            return formatBinaryExpression(node.getOperator().getValue(), node.getLeft(), node.getRight());
        }

        private String formatBinaryExpression(String operator, Expression left, Expression right)
        {
            return '(' + process(left, null) + ' ' + operator + ' ' + process(right, null) + ')';
        }

        @Override
        protected String visitIdentifier(Identifier node, Void context)
        {
            if (!node.isDelimited()) {
                return node.getValue();
            }
            else {
                return '"' + node.getValue().replace("\"", "\"\"") + '"';
            }
        }

        @Override
        protected String visitLogicalBinaryExpression(LogicalBinaryExpression node, Void context)
        {
            return formatBinaryExpression(node.getOperator().toString(), node.getLeft(), node.getRight());
        }
    }

}
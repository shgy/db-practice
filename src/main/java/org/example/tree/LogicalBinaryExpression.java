package org.example.tree;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class LogicalBinaryExpression
        extends Expression
{
    public enum Operator
    {
        AND, OR;

        public Operator flip()
        {
            switch (this) {
                case AND:
                    return Operator.OR;
                case OR:
                    return Operator.AND;
                default:
                    throw new IllegalArgumentException("Unsupported logical expression type: " + this);
            }
        }
    }

    private final Operator operator;
    private final Expression left;
    private final Expression right;

    public LogicalBinaryExpression(Operator operator, Expression left, Expression right)
    {
        this(Optional.empty(), operator, left, right);
    }

    public LogicalBinaryExpression(NodeLocation location, Operator operator, Expression left, Expression right)
    {
        this(Optional.of(location), operator, left, right);
    }

    private LogicalBinaryExpression(Optional<NodeLocation> location, Operator operator, Expression left, Expression right)
    {
        super(location);
        requireNonNull(operator, "operator is null");
        requireNonNull(left, "left is null");
        requireNonNull(right, "right is null");

        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public Expression getLeft()
    {
        return left;
    }

    public Expression getRight()
    {
        return right;
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context)
    {
        return visitor.visitLogicalBinaryExpression(this, context);
    }
    @Override
    public List<Node> getChildren()
    {
        return ImmutableList.of(left, right);
    }

    public static LogicalBinaryExpression and(Expression left, Expression right)
    {
        return new LogicalBinaryExpression(Optional.empty(), Operator.AND, left, right);
    }

    public static LogicalBinaryExpression or(Expression left, Expression right)
    {
        return new LogicalBinaryExpression(Optional.empty(), Operator.OR, left, right);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogicalBinaryExpression that = (LogicalBinaryExpression) o;
        return operator == that.operator &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(operator, left, right);
    }
}

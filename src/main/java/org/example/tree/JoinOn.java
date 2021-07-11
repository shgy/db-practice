package org.example.tree;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class JoinOn
        extends JoinCriteria
{
    private final Expression expression;

    public JoinOn(Expression expression)
    {
        this.expression = requireNonNull(expression, "expression is null");
    }

    public Expression getExpression()
    {
        return expression;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        JoinOn o = (JoinOn) obj;
        return Objects.equals(expression, o.expression);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(expression);
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .addValue(expression)
                .toString();
    }

    @Override
    public List<Node> getNodes()
    {
        return ImmutableList.of(expression);
    }
}

package org.example.tree;


import java.util.Optional;

public abstract class Expression
        extends Node
{
    protected Expression(Optional<NodeLocation> location)
    {
        super(location);
    }

    @Override
    public final String toString()
    {
        return ExpressionFormatter.formatExpression(this, Optional.empty()); // This will not replace parameters, but we don't have access to them here
    }
}

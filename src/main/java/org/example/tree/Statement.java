package org.example.tree;

import java.util.Optional;

public abstract class Statement
        extends Node
{
    protected Statement(Optional<NodeLocation> location)
    {
        super(location);
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context)
    {
        return visitor.visitStatement(this, context);
    }

}
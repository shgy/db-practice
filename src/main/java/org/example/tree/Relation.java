package org.example.tree;

import java.util.Optional;

public abstract class Relation
        extends Node
{
    protected Relation(Optional<NodeLocation> location)
    {
        super(location);
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context)
    {
        return visitor.visitRelation(this, context);
    }


}
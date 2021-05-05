package org.example.tree;

import java.util.Optional;

public abstract class QueryBody
        extends Relation
{
    protected QueryBody(Optional<NodeLocation> location)
    {
        super(location);
    }
}
package org.example.tree;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

public abstract class Literal
        extends Expression
{
    protected Literal(Optional<NodeLocation> location)
    {
        super(location);
    }

    @Override
    public List<Node> getChildren()
    {
        return ImmutableList.of();
    }
}

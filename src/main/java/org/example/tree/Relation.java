package org.example.tree;

import java.util.Optional;

public abstract class Relation
        extends Node
{
    protected Relation(Optional<NodeLocation> location)
    {
        super(location);
    }


}
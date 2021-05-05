package org.example.tree;

import java.util.Optional;

public abstract class Statement
        extends Node
{
    protected Statement(Optional<NodeLocation> location)
    {
        super(location);
    }
}
package org.example.tree;

import java.util.Optional;

public abstract class SelectItem
        extends Node
{
    protected SelectItem(Optional<NodeLocation> location)
    {
        super(location);
    }
}
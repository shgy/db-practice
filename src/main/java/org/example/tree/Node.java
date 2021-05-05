package org.example.tree;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public abstract class Node
{
    private final Optional<NodeLocation> location;

    protected Node(Optional<NodeLocation> location)
    {
        this.location = requireNonNull(location, "location is null");
    }

    public Optional<NodeLocation> getLocation()
    {
        return location;
    }

    public abstract List<? extends Node> getChildren();

    // Force subclasses to have a proper equals and hashcode implementation
    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();
}

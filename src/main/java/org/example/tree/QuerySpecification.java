package org.example.tree;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class QuerySpecification
        extends QueryBody
{
    private final Select select;
    private final Optional<Relation> from;

    public QuerySpecification(
            Select select,
            Optional<Relation> from)
    {
        this(Optional.empty(), select, from);
    }

    public QuerySpecification(
            NodeLocation location,
            Select select,
            Optional<Relation> from)
    {
        this(Optional.of(location), select, from);
    }

    private QuerySpecification(
            Optional<NodeLocation> location,
            Select select,
            Optional<Relation> from)
    {
        super(location);
        requireNonNull(select, "select is null");
        requireNonNull(from, "from is null");


        this.select = select;
        this.from = from;

    }

    public Select getSelect()
    {
        return select;
    }

    public Optional<Relation> getFrom()
    {
        return from;
    }


    @Override
    public List<Node> getChildren()
    {
        ImmutableList.Builder<Node> nodes = ImmutableList.builder();
        nodes.add(select);
        from.ifPresent(nodes::add);

        return nodes.build();
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("select", select)
                .add("from", from)
                .toString();
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
        QuerySpecification o = (QuerySpecification) obj;
        return Objects.equals(select, o.select) &&
                Objects.equals(from, o.from) ;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(select, from);
    }
}

package org.example.tree;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class Query
        extends Statement
{
    private final QueryBody queryBody;


    public Query(
            QueryBody queryBody)
    {
        this(Optional.empty(), queryBody);
    }

    public Query(
            NodeLocation location,
            QueryBody queryBody)
    {
        this(Optional.of(location), queryBody);
    }

    private Query(
            Optional<NodeLocation> location,
            QueryBody queryBody)
    {
        super(location);
        requireNonNull(queryBody, "queryBody is null");

        this.queryBody = queryBody;
    }


    public QueryBody getQueryBody()
    {
        return queryBody;
    }




    @Override
    public List<Node> getChildren()
    {
        ImmutableList.Builder<Node> nodes = ImmutableList.builder();
        nodes.add(queryBody);
        return nodes.build();
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("queryBody", queryBody)
                .omitNullValues()
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
        Query o = (Query) obj;
        return  Objects.equals(queryBody, o.queryBody);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( queryBody);
    }
}

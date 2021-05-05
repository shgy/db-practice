package org.example.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.example.antlr.SqlBaseBaseVisitor;
import org.example.antlr.SqlBaseParser;
import org.example.tree.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class AstBuilder
        extends SqlBaseBaseVisitor<Node>
{
    private int parameterPosition;
    private final ParsingOptions parsingOptions;

    AstBuilder(ParsingOptions parsingOptions)
    {
        this.parsingOptions = requireNonNull(parsingOptions, "parsingOptions is null");
    }


    @Override
    public Node visitSingleStatement(SqlBaseParser.SingleStatementContext context)
    {
        return visit(context.statement());
    }

    @Override
    public Node visitQuery(SqlBaseParser.QueryContext context)
    {
        Query body = (Query) visit(context.queryNoWith());

        return new Query(
                getLocation(context),
                body.getQueryBody());
    }


    @Override
    public Node visitQueryNoWith(SqlBaseParser.QueryNoWithContext context)
    {
        QueryBody term = (QueryBody) visit(context.queryTerm());

        if (term instanceof QuerySpecification) {
            // When we have a simple query specification
            // followed by order by limit, fold the order by and limit
            // clauses into the query specification (analyzer/planner
            // expects this structure to resolve references with respect
            // to columns defined in the query specification)
            QuerySpecification query = (QuerySpecification) term;

            return new Query(
                    getLocation(context),
                    new QuerySpecification(
                            getLocation(context),
                            query.getSelect(),
                            query.getFrom()
                            ));
        }

        return new Query(
                getLocation(context),
                term);
    }

    @Override
    public Node visitQuerySpecification(SqlBaseParser.QuerySpecificationContext context)
    {
        Optional<Relation> from = Optional.empty();
        List<SelectItem> selectItems = visit(context.selectItem(), SelectItem.class);

        List<Relation> relations = visit(context.relation(), Relation.class);
        if (!relations.isEmpty()) {
            // synthesize implicit join nodes
            Iterator<Relation> iterator = relations.iterator();
            Relation relation = iterator.next();

            from = Optional.of(relation);
        }

        return new QuerySpecification(
                getLocation(context),
                new Select(getLocation(context.SELECT()), false, selectItems),
                from);
    }

    @Override
    public Node visitSelectSingle(SqlBaseParser.SelectSingleContext context)
    {
        return new SingleColumn(
                getLocation(context),
                (Expression) visit(context.expression()),
                Optional.empty());
    }
    @Override
    public Node visitColumnReference(SqlBaseParser.ColumnReferenceContext context)
    {
        return visit(context.identifier());
    }
    @Override
    public Node visitTableName(SqlBaseParser.TableNameContext context)
    {
        return new Table(getLocation(context), getQualifiedName(context.qualifiedName()));
    }
    @Override
    public Node visitUnquotedIdentifier(SqlBaseParser.UnquotedIdentifierContext context)
    {
        return new Identifier(getLocation(context), context.getText(), false);
    }

    private QualifiedName getQualifiedName(SqlBaseParser.QualifiedNameContext context)
    {
        List<String> parts = visit(context.identifier(), Identifier.class).stream()
                .map(Identifier::getValue) // TODO: preserve quotedness
                .collect(Collectors.toList());

        return QualifiedName.of(parts);
    }
    private <T> Optional<T> visitIfPresent(ParserRuleContext context, Class<T> clazz)
    {
        return Optional.ofNullable(context)
                .map(this::visit)
                .map(clazz::cast);
    }
    private <T> List<T> visit(List<? extends ParserRuleContext> contexts, Class<T> clazz)
    {
        return contexts.stream()
                .map(this::visit)
                .map(clazz::cast)
                .collect(toList());
    }
    public static NodeLocation getLocation(ParserRuleContext parserRuleContext)
    {
        requireNonNull(parserRuleContext, "parserRuleContext is null");
        return getLocation(parserRuleContext.getStart());
    }

    public static NodeLocation getLocation(Token token)
    {
        requireNonNull(token, "token is null");
        return new NodeLocation(token.getLine(), token.getCharPositionInLine());
    }

    public static NodeLocation getLocation(TerminalNode terminalNode)
    {
        requireNonNull(terminalNode, "terminalNode is null");
        return getLocation(terminalNode.getSymbol());
    }
}

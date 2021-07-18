package org.example.tree;

import javax.annotation.Nullable;

public abstract class AstVisitor<R, C> {
    public R process(Node node) {
        return process(node, null);
    }

    public R process(Node node, @Nullable C context) {
        return node.accept(this, context);
    }


    protected R visitQuerySpecification(QuerySpecification node, C context)
    {
        return visitQueryBody(node, context);
    }

    protected R visitJoin(Join node, C context)
    {
        return visitRelation(node, context);
    }
    protected R visitRelation(Relation node, C context)
    {
        return visitNode(node, context);
    }

    protected R visitQueryBody(QueryBody node, C context)
    {
        return visitRelation(node, context);
    }

    protected R visitNode(Node node, C context) {
        return null;
    }

    protected R visitExpression(Expression node, C context) {
        return visitNode(node, context);
    }

    protected R visitLiteral(Literal node, C context)
    {
        return visitExpression(node, context);
    }

    protected R visitIdentifier(Identifier node, C context)
    {
        return visitExpression(node, context);
    }


    protected R visitLongLiteral(LongLiteral node, C context)
    {
        return visitLiteral(node, context);
    }

    protected R visitLogicalBinaryExpression(LogicalBinaryExpression node, C context)
    {
        return visitExpression(node, context);
    }
    protected R visitComparisonExpression(ComparisonExpression node, C context)
    {
        return visitExpression(node, context);
    }

    protected R visitStatement(Statement node, C context)
    {
        return visitNode(node, context);
    }


    protected R visitQuery(Query node, C context)
    {
        return visitStatement(node, context);
    }

    protected R visitAliasedRelation(AliasedRelation node, C context)
    {
        return visitRelation(node, context);
    }

    protected R visitTable(Table node, C context)
    {
        return visitQueryBody(node, context);
    }

    protected R visitDereferenceExpression(DereferenceExpression node, C context)
    {
        return visitExpression(node, context);
    }
}
package org.example.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.example.tree.*;

import java.util.List;
import java.util.Set;

public class FilterFieldVisitor extends AstVisitor<Void,Set<String>> {

    public Set<String> extractFields(Expression expression){
        Set<String> fields = Sets.newHashSet();
        process(expression,fields);
        return fields;
    }


    @Override
    protected Void visitLogicalBinaryExpression(LogicalBinaryExpression node, Set<String> context) {
        process(node.getLeft(),context);
        process(node.getRight(),context);
        return null;
    }

    @Override
    protected Void visitComparisonExpression(ComparisonExpression node, Set<String> context) {
        Expression left = node.getLeft();
        if(left instanceof Identifier){
            context.add(((Identifier) left).getValue());
        }
        return null;
    }
}

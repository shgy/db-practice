package org.example.planner;

import com.facebook.presto.spi.type.Type;
import com.google.common.collect.Lists;
import org.example.meta.Field;
import org.example.operator.JoinOperator;
import org.example.operator.Operator;
import org.example.operator.OutputOperator;
import org.example.operator.TableScanOperator;
import org.example.sql.analyzer.Analysis;
import org.example.tree.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RelationPlanner extends AstVisitor<Operator,Void> {
    private final Analysis analysis;

    private List<Operator> result = Lists.newArrayList();
    public RelationPlanner(Analysis analysis){
        this.analysis = analysis;
    }


    public List<Operator> getResult(){
        return result;
    }


    @Override
    protected Operator visitQuery(Query node, Void context) {
        return process(node.getQueryBody());
    }

    @Override
    protected Operator visitQuerySpecification(QuerySpecification node, Void context){


        Optional<Relation> relation = node.getFrom();
        if(relation.isPresent()){
            Operator operator = process(relation.get());
            result.add(operator);
        }

        List<Field> fields = analysis.getOutFields();
        List<Type> fieldTypes = fields.stream().map(s->s.getType()).collect(Collectors.toList());
        List<String> fieldNames = fields.stream().map(s->s.getName()).collect(Collectors.toList());


        OutputOperator operator = new OutputOperator(fieldTypes, fieldNames,Optional.empty());
        result.add(operator);
        return null;
    }

    @Override
    protected Operator visitJoin(Join node, Void context){

        TableScanOperator probeScanOp = (TableScanOperator) process(node.getLeft());

        result.add(probeScanOp);

        TableScanOperator buildScanOp = (TableScanOperator) process(node.getRight());

        JoinOperator joinOperator = new JoinOperator(buildScanOp);


        return joinOperator;
    }


    @Override
    protected Operator visitAliasedRelation(AliasedRelation node, Void context) {
        Table table = (Table) node.getRelation();
        return process(table);
    }

    @Override
    public Operator visitTable(Table node, Void context){
        String tableName = node.getName().toString();
        List<Field> fields = analysis.getTableFields(tableName);
        List<Type> fieldTypes = fields.stream().map(s->s.getType()).collect(Collectors.toList());
        List<String> fieldNames = fields.stream().map(s->s.getName()).collect(Collectors.toList());
        TableScanOperator operator = new TableScanOperator(tableName, fieldTypes,fieldNames);
        return operator;
    }

}

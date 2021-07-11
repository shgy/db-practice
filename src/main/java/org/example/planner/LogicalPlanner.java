package org.example.planner;

import com.facebook.presto.spi.PrestoException;
import com.google.common.collect.Lists;
import org.example.operator.Operator;
import org.example.sql.analyzer.Analysis;
import org.example.tree.*;

import java.util.List;
import java.util.Optional;

import static com.facebook.presto.spi.StandardErrorCode.NOT_SUPPORTED;

public class LogicalPlanner {

    public List<Operator> plan(Statement statement){
        if (statement instanceof Query) {
            return createRelationPlan((Query) statement);
        }else{
            throw new PrestoException(NOT_SUPPORTED, "Unsupported statement type " + statement.getClass().getSimpleName());
        }
    }

    private List<Operator> createRelationPlan(Query query){
        Visitor visitor=  new Visitor();
        visitor.process(query);
        return visitor.getResult();
    }

    class Visitor extends AstVisitor<Operator,Void>{
        private List<Operator> result = Lists.newArrayList();

        public List<Operator> getResult(){
            return result;
        }

        @Override
        protected Operator visitJoin(Join node, Void context){

            Table probeTable = (Table) node.getLeft();
            visitTable(probeTable,context);

            Table buildTable = (Table) node.getRight();
            visitTable(buildTable, context);

            return null;
        }

        @Override
        public Operator visitTable(Table node, Void context){
            return null;
        }

        @Override
        protected Operator visitQuerySpecification(QuerySpecification node, Void context){

            /**
             * select * from relation
             */
            Optional<Relation> relation = node.getFrom();
            if(relation.isPresent()){

            }

            return null;
        }
    }
}

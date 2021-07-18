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

    public List<Operator> plan(Analysis analysis){

        Statement statement = analysis.getStatement();

        if (statement instanceof Query) {
            return createRelationPlan(analysis, (Query) statement);
        }else{
            throw new PrestoException(NOT_SUPPORTED, "Unsupported statement type " + statement.getClass().getSimpleName());
        }
    }

    private List<Operator> createRelationPlan(Analysis analysis, Query query){
        RelationPlanner planner = new RelationPlanner(analysis);
        planner.process(query);
        return planner.getResult();
    }

}

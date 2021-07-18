package org.example;

import org.example.meta.Metadata;
import org.example.operator.Operator;
import org.example.parser.SqlParser;
import org.example.planner.LogicalPlanner;
import org.example.sql.analyzer.Analysis;
import org.example.sql.analyzer.Analyzer;
import org.example.tree.Statement;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LogicalPlanTest {


    @Test
    public void testPlan(){
        SqlParser sqlParser= new SqlParser();
//        Statement statement = sqlParser.createStatement("select id, name from users");
        Statement statement = sqlParser.createStatement("select t1.id, t1.name from users t1 join orders t2 on t1.id = t2.uid ");

        Analyzer analyzer = new Analyzer(new Metadata());
        Analysis analysis = analyzer.analyze(statement);

        LogicalPlanner planner = new LogicalPlanner();
        List<Operator> operators = planner.plan(analysis);

        Assert.assertTrue(operators.size()>0);
    }
}

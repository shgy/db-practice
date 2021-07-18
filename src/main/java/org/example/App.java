package org.example;

import com.facebook.presto.spi.Page;
import com.google.common.collect.Lists;
import org.example.codegen.WhereExprFilterCompiler;
import org.example.execute.Row;
import org.example.filter.FilterFieldVisitor;
import org.example.filter.RowFilter;
import org.example.meta.Metadata;
import org.example.operator.Operator;
import org.example.operator.OperatorContext;
import org.example.operator.OutputOperator_d;
import org.example.operator.TableScanOperator_d;
import org.example.parser.SqlParser;
import org.example.planner.LogicalPlanner;
import org.example.sql.analyzer.Analysis;
import org.example.sql.analyzer.Analyzer;
import org.example.tree.*;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException {

        System.out.print(">> ");
        InputStream is = System.in;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sql = reader.readLine();
//        String sql = "select City, City from cities";
//        String sql = "select id, name from users";
//        String sql = "select t1.id, t1.name,t2.name from users t1 join orders t2 ";

        SqlParser sqlParser = new SqlParser();

        Statement statement = sqlParser.createStatement(sql);

        if(statement instanceof Query){

            List<Operator>  operators = parse((Query) statement);

            execute(operators);

        }else{
            System.err.println("unsupported!!");
        }

    }

    private static List<Operator> parse(Query query) throws IOException, IllegalAccessException, InstantiationException {
        Analyzer analyzer = new Analyzer(new Metadata());
        Analysis analysis = analyzer.analyze(query);
        LogicalPlanner planner = new LogicalPlanner();
        List<Operator> operators = planner.plan(analysis);
        return operators;
    }

    private static void execute(List<Operator>  operators){
        boolean hasData=true;
        while (hasData){
            boolean movePage = false;
            for (int i = 0; i < operators.size() - 1 ; i++) {
                Operator current = operators.get(i);
                Operator next = operators.get(i + 1);
                Page page = current.getOutput();
                if (page != null && page.getPositionCount() != 0) {
                    next.addInput(page);
                    movePage=true;
                }
            }
            if(!movePage){
                hasData=false;
            }
        }
    }
}

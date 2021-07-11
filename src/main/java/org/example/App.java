package org.example;

import com.google.common.collect.Lists;
import org.example.codegen.WhereExprFilterCompiler;
import org.example.execute.Row;
import org.example.filter.FilterFieldVisitor;
import org.example.filter.RowFilter;
import org.example.operator.OperatorContext;
import org.example.operator.OutputOperator_d;
import org.example.operator.TableScanOperator_d;
import org.example.parser.SqlParser;
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
//        String sql = "select id, name from employee where id>1";

        SqlParser sqlParser = new SqlParser();

        Statement statement = sqlParser.createStatement(sql);

        if(statement instanceof Query){

            OperatorContext context = new OperatorContext();

            parse(context, (Query) statement);

            execute(context);

        }else{
            System.err.println("unsupported!!");
        }

    }

    private static void parse(OperatorContext context, Query query) throws IOException, IllegalAccessException, InstantiationException {
        /**
         * 获取待查询的表名和字段名称
         */
        QuerySpecification specification = (QuerySpecification) query.getQueryBody();
        Table table= (Table) specification.getFrom().get();
        List<SelectItem> selectItems = specification.getSelect().getSelectItems();
        List<String> fieldNames = Lists.newArrayList();
        for(SelectItem item:selectItems){
            SingleColumn column = (SingleColumn) item;
            fieldNames.add(((Identifier)column.getExpression()).getValue());
        }

        Optional<Expression> optional = specification.getWhere();
        if(optional.isPresent()){
            /**
             * 获取过滤字段
             */
            FilterFieldVisitor visitor = new FilterFieldVisitor();
            Set<String> filterFields = visitor.extractFields(optional.get());
            context.setFilterFiledNames(filterFields);
            /**
             * 生成编译器
             */
            WhereExprFilterCompiler compiler = new WhereExprFilterCompiler();
            Class clazz = compiler.compile(optional.get());
            RowFilter rowFilter = (RowFilter) clazz.newInstance();
            context.setRowFilter(rowFilter);
        }

        context.setTableName(table.getName().toString());
        context.setOutFieldNames(fieldNames);
    }

    private static void execute(OperatorContext context){


        TableScanOperator_d tableScan = new TableScanOperator_d(context);
        OutputOperator_d output  = new OutputOperator_d(context);
        List<Row> rowList = tableScan.getOutput();
        output.addInput(rowList);
        output.getOutput();
    }
}

package org.example;

import org.example.codegen.WhereExprFilterCompiler;
import org.example.filter.FilterFieldVisitor;
import org.example.filter.RowFilter;
import org.example.filter.WhereExprFilter;
import org.example.parser.SqlParser;
import org.example.tree.Expression;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExpressionFilterTest {

    @Test
    public void testDirectFilter(){
        SqlParser sqlParser = new SqlParser();
        Expression e1 = sqlParser.createExpression("a>100 and b>0");
        Assert.assertTrue(e1!=null);
        RowFilter filter1 = new WhereExprFilter(e1);
        Random r = new Random();
        for(int i=0;i<10;i++){
            long a = r.nextInt(256);
            long b = r.nextInt(256);
            Map<String,Long> row = new HashMap<>();
            row.put("a",a);
            row.put("b",b);
            boolean ret = filter1.filter(row);
            System.out.println("expr: a>100 and b>0, param: "+row+", ret:"+ret);
        }

    }

    @Test
    public void testCompiledFilter() throws IllegalAccessException, InstantiationException {
        SqlParser sqlParser = new SqlParser();
        Expression e1 = sqlParser.createExpression("a>100 and b>0");
        Assert.assertTrue(e1!=null);
        WhereExprFilterCompiler compiler = new WhereExprFilterCompiler();
        Class clazz = compiler.compile(e1);
        RowFilter filter1 = (RowFilter) clazz.newInstance();

        FilterFieldVisitor visitor = new FilterFieldVisitor();
        System.out.println(visitor.extractFields(e1));

        Random r = new Random();
        for(int i=0;i<10;i++){
            long a = r.nextInt(256);
            long b = r.nextInt(256);
            Map<String,Long> row = new HashMap<>();
            row.put("a",a);
            row.put("b",b);
            boolean ret = filter1.filter(row);
            System.out.println("expr: a>100 and b>0, param: "+row+", ret:"+ret);
        }

    }
}

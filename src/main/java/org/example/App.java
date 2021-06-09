package org.example;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.execute.Row;
import org.example.filter.FilterFieldVisitor;
import org.example.filter.WhereExprFilter;
import org.example.parser.SqlParser;
import org.example.tree.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    private String dataDir="data";
    public static void main( String[] args ) throws IOException {

        System.out.print(">> ");
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sql = reader.readLine();
//        String sql = "select City, City from cities";

        SqlParser sqlParser = new SqlParser();

        Statement statement = sqlParser.createStatement(sql);

        if(statement instanceof Query){
            handleQuery((Query) statement);

        }else{
            System.out.println("unsupported!!");
        }

    }

    private static void handleQuery(Query query) throws IOException {
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

        /**
         * 基于表名确定查询的数据源文件
         */
        String fileLoc = String.format("./data/%s.csv",table.getName());

        /**
         * 从csv文件中读取指定的字段
         */
        Reader in = new FileReader(fileLoc);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        List<Row> rowList = Lists.newArrayList();
        for(CSVRecord record:records){
            Row row = new Row();

            for(String field:fieldNames){
                row.addColumn(record.get(field));

            }
            rowList.add(row);
        }

         /**
         * 格式化输出到控制台
         */
        int width=30;
        String format = fieldNames.stream().map(s-> "%-"+width+"s").collect(Collectors.joining("|"));
        System.out.println( "|"+String.format(format, fieldNames.toArray())+"|");

        int flagCnt = width*fieldNames.size()+fieldNames.size();
        String rowDelimiter = String.join("", Collections.nCopies(flagCnt, "-"));
        System.out.println(rowDelimiter);
        for(Row row:rowList){
            System.out.println( "|"+String.format(format, row.getColumnList().toArray())+"|");
        }
    }
}

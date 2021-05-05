package org.example;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.execute.Row;
import org.example.parser.SqlParser;
import org.example.tree.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    private String dataDir="data";
    public static void main( String[] args ) throws IOException {
        SqlParser sqlParser = new SqlParser();
        String sql = "select City, City from cities";
        Statement statement = sqlParser.createStatement(sql);

        if(statement instanceof Query){
            handleQuery((Query) statement);

        }else{
            System.out.println("unsupported!!");
        }

    }

    private static void handleQuery(Query query) throws IOException {
        QuerySpecification specification = (QuerySpecification) query.getQueryBody();
        Table table= (Table) specification.getFrom().get();
        List<SelectItem> selectItems = specification.getSelect().getSelectItems();
        List<String> fieldNames = Lists.newArrayList();
        for(SelectItem item:selectItems){
            SingleColumn column = (SingleColumn) item;
            fieldNames.add(((Identifier)column.getExpression()).getValue());
        }

        String fileLoc = String.format("./data/%s.csv",table.getName());

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

        System.out.println(String.join("\t\t",fieldNames));
        for(Row row:rowList){
            System.out.println(String.join("\t\t",row.getColumnList()));
        }
    }
}

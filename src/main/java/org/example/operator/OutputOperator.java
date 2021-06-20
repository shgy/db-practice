package org.example.operator;

import org.example.execute.Row;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OutputOperator {

    private OperatorContext context;
    private List<Row> rowList;
    public OutputOperator(OperatorContext context){
        this.context = context;
    }

    public void addInput(List<Row> rowList){
        this.rowList = rowList;
    }
    public void getOutput(){
/**
 * 格式化输出到控制台
 */
        List<String> fieldNames = context.getOutFieldNames();
        int width=30;
        String format = fieldNames.stream().map(s-> "%-"+width+"s").collect(Collectors.joining("|"));
        System.out.println( "|"+String.format(format, fieldNames.toArray())+"|");

        int flagCnt = width*fieldNames.size()+fieldNames.size();
        String rowDelimiter = String.join("", Collections.nCopies(flagCnt, "-"));
        System.out.println(rowDelimiter);
        for(Row row:rowList){
            System.out.println( "|"+String.format(format, row.getOutColumnList().toArray())+"|");
        }
    }
}

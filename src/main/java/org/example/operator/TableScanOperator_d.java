package org.example.operator;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.execute.Row;
import org.example.filter.RowFilter;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class TableScanOperator_d {

    private OperatorContext context ;

    public TableScanOperator_d(OperatorContext context){
        this.context = context;
    }

    public List<Row> getOutput(){
        try{
            /**
             * 基于表名确定查询的数据源文件
             */
            String fileLoc = String.format("./data/%s.csv",context.getTableName());

            /**
             * 从csv文件中读取指定的字段
             */
            Reader in = new FileReader(fileLoc);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<Row> rowList = Lists.newArrayList();
            for(CSVRecord record:records){
                Row row = new Row();

                for(String field:context.getOutFieldNames()){
                    row.addOutColumn(record.get(field));
                }
                if(context.getFilterFiledNames()!=null){
                    for(String field: context.getFilterFiledNames()){
                        row.addFilterColumn(field, Long.valueOf(record.get(field)));
                    }
                }

                if(checkPass(row)){
                    rowList.add(row);
                }

            }
            return rowList;
        }catch (Exception e){
            throw new RuntimeException("getOutput error",e);
        }

    }

    private boolean checkPass(Row row) {
        RowFilter rowFilter = context.getRowFilter();
        if(rowFilter==null){
            return true;
        }
        return rowFilter.filter(row.getFieldMap());
    }
}

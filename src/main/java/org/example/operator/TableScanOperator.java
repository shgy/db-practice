package org.example.operator;

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.type.Type;
import com.google.common.collect.Lists;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * 当前版本的TableScan实现做如下约束：
 * 1. 只读取一页
 * 2. 不进行数据过滤
 */
public class TableScanOperator implements Operator{


    /**
     * 对于一张数据表，关键的信息如下：
     * 1. 表的名称
     * 2. 表中的字段：字段名称，字段类型，字段在表中的顺序
     *
     * 这些信息在SQL 分析阶段结合元数据获得
     */

    private String tableName;

    private List<Type> types;

    private List<String> fieldNames;

    private PageBuilder pageBuilder;

    public TableScanOperator(String tableName, List<Type> types,List<String> fieldNames){
        this.tableName = tableName;
        this.types = types;
        this.fieldNames = fieldNames;
        this.pageBuilder = new PageBuilder(types);
    }


    @Override
    public void addInput(Page page) {
        throw new UnsupportedOperationException(getClass().getName() + " can not take input");
    }

    @Override
    public Page getOutput() {
        try{

            /**
             * 基于表名确定查询的数据源文件
             */
            String fileLoc = String.format("./data/%s.csv",tableName);
            /**
             * 从csv文件中读取指定的字段
             */
            Reader in = new FileReader(fileLoc);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            for(CSVRecord record:records){

                pageBuilder.declarePosition();
                for (int column = 0; column < types.size(); column++) {
                    BlockBuilder output = pageBuilder.getBlockBuilder(column);
                    String fieldName = fieldNames.get(column);
                    String colVal = record.get(fieldName);
                    Type type = types.get(column);
                    Class<?> javaType = type.getJavaType();
                    if (javaType == long.class) {
                        type.writeLong(output, Long.valueOf(colVal));
                    }
                    else if (javaType == Slice.class) {
                        Slice slice = Slices.utf8Slice(colVal);
                        type.writeSlice(output, slice, 0, slice.length());
                    }
                    else {
                        throw new UnsupportedOperationException("unsupported type "+type.getDisplayName());
                    }
                }

            }
            Page page = pageBuilder.build();
            pageBuilder.reset();

            return page;
        }catch (Exception e){
            throw new RuntimeException("getOutput error",e);
        }
    }
}

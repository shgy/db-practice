package org.example.operator;

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.type.Type;
import com.google.common.collect.Lists;
import io.airlift.slice.Slice;
import org.example.execute.Row;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 将page中的数据格式化输出到控制台
 */
public class OutputOperator implements Operator{

    private Page page;

    private List<Type> types;
    private List<String> fieldNames;
    private List<Integer> outChannels;

    private boolean headerPrinted = false;

    public OutputOperator(List<Type> types, List<String> fieldNames,Optional<List<Integer>> outChannels) {
        this.types = types;
        this.fieldNames = fieldNames;
        if(outChannels.isPresent()){
            this.outChannels = outChannels.get();
        }else{
            List<Integer> range = Lists.newArrayList();
            for(int i=0;i<types.size();i++){
                range.add(i);
            }
            this.outChannels = range;
        }

    }


    @Override
    public void addInput(Page page) {
       this.page = page;
    }

    private void printHeader(String format ){
        System.out.println( "|"+String.format(format, fieldNames.toArray())+"|");
    }

    @Override
    public Page getOutput() {
        int width=30;
        String format = fieldNames.stream().map(s-> "%-"+width+"s").collect(Collectors.joining("|"));
        if(!headerPrinted){
            printHeader(format);
            headerPrinted=true;
        }
        int flagCnt = width*fieldNames.size()+fieldNames.size();
        String rowDelimiter = String.join("", Collections.nCopies(flagCnt, "-"));
        System.out.println(rowDelimiter);
        for(int pos=0;pos<page.getPositionCount();pos++){
            List<String> rowData = Lists.newArrayList();
            for(int channel=0;channel<types.size();channel++){
                Block block = page.getBlock(channel);
                Type type = types.get(channel);
                Class<?> javaType = type.getJavaType();
                if (javaType == long.class) {
                    rowData.add(""+block.getLong(pos,0));
                }
                else if (javaType == Slice.class) {
                    Slice slice = block.getSlice(pos,0,block.getSliceLength(pos));
                    rowData.add(slice.toStringUtf8());
                }
                else {
                    throw new UnsupportedOperationException("unsupported type "+type.getDisplayName());
                }
            }
            System.out.println( "|"+String.format(format, rowData.toArray())+"|");
        }
        return  null;
    }
}

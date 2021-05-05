package org.example.execute;

import com.google.common.collect.Lists;

import java.util.List;

public class Row {

    private List<String> columnList = Lists.newArrayList();

    public void addColumn(String val){
        columnList.add(val);
    }
    public List<String> getColumnList(){
        return columnList;
    }
}

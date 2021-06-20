package org.example.execute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Row {

    private List<String> outColumnList = Lists.newArrayList();
    private Map<String,Long> fieldMap = Maps.newHashMap();

    public void addOutColumn(String val){
        outColumnList.add(val);
    }
    public void addFilterColumn(String fieldName, Long val){
        fieldMap.put(fieldName,val);
    }
    public List<String> getOutColumnList(){
        return outColumnList;
    }

    public Map<String,Long>  getFieldMap(){
        return fieldMap;
    }
}

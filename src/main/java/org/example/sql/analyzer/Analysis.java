package org.example.sql.analyzer;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.example.meta.Field;
import org.example.tree.Statement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analysis {
    private final Statement root;

    private List<Field> outFields = Lists.newArrayList();
    private Map<Field,Integer> outIndex = Maps.newHashMap();

    private List<Field> allFields = Lists.newArrayList();

    private Map<String,String> tableAlias = Maps.newHashMap();

    private Map<String,List<Field>> tableFields = Maps.newHashMap();

    public Analysis(Statement statement){
        this.root = statement;
    }

    public Statement getStatement()
    {
        return root;
    }

    public void initTableFields(String table,List<Field> fields){
        this.tableFields.put(table,Lists.newArrayList());
        this.allFields.addAll(fields);
    }

    public void addAlias(String alias,String table){
        this.tableAlias.put(alias,table);
    }

    public Field resoveField(String name){
        Field field = null;
        int matchCnt = 0;
        for(Field item:allFields){
            if(item.getName().equals(name)){
                matchCnt++;
                field = item;
            }
        }
        if(matchCnt>1){
            throw new IllegalArgumentException("too many fields match "+name);
        }
        if(matchCnt==0){
            throw new IllegalArgumentException("no field match "+name);
        }
        return field;
    }

    public Field resoveField(String alias, String name){
        String table = tableAlias.get(alias);
        if(table==null){
            throw new IllegalArgumentException("no table match alias "+alias);
        }

        Field field = null;
        int matchCnt = 0;
        for(Field item:allFields){
            if(item.getName().equals(name) && item.getTable().equals(table)){
                matchCnt++;
                field = item;
            }
        }
        if(matchCnt>1){
            throw new IllegalArgumentException("too many fields match "+name);
        }
        if(matchCnt==0){
            throw new IllegalArgumentException("no field match "+name);
        }
        return field;
    }


    public void addOutField(Field field){

        outFields.add(field);
    }

    public List<Field> getOutFields(){
        return outFields;
    }

    public void addTableFields(Field field){

        tableFields.get(field.getTable()).add(field);
    }

    public List<Field> getTableFields(String tableName){
        return tableFields.get(tableName);
    }

}

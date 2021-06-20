package org.example.operator;

import org.example.filter.RowFilter;
import org.example.filter.WhereExprFilter;

import java.util.List;
import java.util.Set;


public class OperatorContext {

    private String tableName;
    private List<String> outFieldNames;
    private Set<String> filterFiledNames;

    private RowFilter rowFilter;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getOutFieldNames() {
        return outFieldNames;
    }

    public void setOutFieldNames(List<String> outFieldNames) {
        this.outFieldNames = outFieldNames;
    }

    public Set<String> getFilterFiledNames() {
        return filterFiledNames;
    }

    public void setFilterFiledNames(Set<String> filterFiledNames) {
        this.filterFiledNames = filterFiledNames;
    }

    public RowFilter getRowFilter() {
        return rowFilter;
    }

    public void setRowFilter(RowFilter rowFilter) {
        this.rowFilter = rowFilter;
    }
}

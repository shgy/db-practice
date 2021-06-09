package org.example.filter;

import java.util.Map;

public interface RowFilter {
    boolean filter(Map<String,Long> row);
}

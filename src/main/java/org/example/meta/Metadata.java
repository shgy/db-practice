package org.example.meta;

import com.facebook.presto.spi.type.BigintType;
import com.facebook.presto.spi.type.VarcharType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Metadata {

    private static Map<String, List<Field>> map = Maps.newHashMap();

    static{
        List<Field> usersTableInfo = Lists.newArrayList(
                new Field("id", BigintType.BIGINT,"users"),
                new Field("name", VarcharType.VARCHAR,"users"),
                new Field("age", BigintType.BIGINT,"users")
        );
        map.put("users",usersTableInfo);

        List<Field> ordersTableInfo = Lists.newArrayList(
                new Field("id", BigintType.BIGINT,"orders"),
                new Field("name", VarcharType.VARCHAR,"orders"),
                new Field("uid", BigintType.BIGINT,"orders")
        );
        map.put("orders",ordersTableInfo);
    }

    public List<Field> getFieldsByTable(String table){
        return map.get(table);
    }

}

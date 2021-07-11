package org.example.meta;

import com.facebook.presto.spi.type.Type;


public class Field {

    private String name;
    private Type type;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

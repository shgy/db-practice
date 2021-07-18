package org.example;

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.type.BigintType;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.VarcharType;
import com.google.common.collect.Lists;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.example.operator.OutputOperator;
import org.example.operator.TableScanOperator;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class TableScanOperatorTest {

    @Test
    public void testReadData(){

        List<Type> types = Lists.newArrayList(BigintType.BIGINT, VarcharType.VARCHAR,BigintType.BIGINT);
        List<String> fieldNames = Lists.newArrayList("id","name","age");
        TableScanOperator operator = new TableScanOperator("employee",types,fieldNames);
        Page page= operator.getOutput();
        OutputOperator outputOperator = new OutputOperator(types,fieldNames, Optional.empty());
        outputOperator.addInput(page);
        outputOperator.getOutput();
    }
}

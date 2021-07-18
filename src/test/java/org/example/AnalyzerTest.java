package org.example;

import org.example.meta.Metadata;
import org.example.parser.SqlParser;
import org.example.sql.analyzer.Analysis;
import org.example.sql.analyzer.Analyzer;
import org.example.tree.Statement;
import org.junit.Assert;
import org.junit.Test;

public class AnalyzerTest {

    SqlParser sqlParser = new SqlParser();
    Metadata metadata = new Metadata();
    Analyzer analyzer = new Analyzer(metadata);
    @Test
    public void testAnalyzer(){
        Statement statement = sqlParser.createStatement("select t1.id, t1.name, t2.uid from users t1 join orders t2 on t2.uid=t1.id  ");
        Analysis analysis = analyzer.analyze(statement);
        Assert.assertTrue(analysis!=null);
    }


}

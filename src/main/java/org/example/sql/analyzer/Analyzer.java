package org.example.sql.analyzer;

import org.example.meta.Metadata;
import org.example.tree.Statement;

public class Analyzer {

    private Metadata metadata;

    public Analyzer(Metadata metadata){
        this.metadata = metadata;
    }

    public Analysis analyze(Statement statement){
        Analysis analysis = new Analysis(statement);

        StatementAnalyzer analyzer = new StatementAnalyzer(analysis,metadata);
        analyzer.analyze(statement);
        
        return analysis;
    }
}

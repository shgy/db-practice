package org.example.sql.analyzer;

import org.example.meta.Field;
import org.example.meta.Metadata;
import org.example.tree.*;

import java.util.List;

public class StatementAnalyzer {

    private Analysis analysis;
    private Metadata metadata;

    public StatementAnalyzer(Analysis analysis, Metadata metadata) {
        this.analysis = analysis;
        this.metadata = metadata;
    }

    public void analyze(Statement statement){
        new Visitor().process(statement);
    }

    private class Visitor extends AstVisitor<Void,Void>{

        @Override
        protected Void visitQuery(Query node, Void context) {
            process(node.getQueryBody());
            return null;
        }

        @Override
        protected Void visitQuerySpecification(QuerySpecification node, Void context) {


            analyzeFrom(node);

            analyzeSelect(node);

            return null;
        }

        private void analyzeSelect(QuerySpecification node) {
            for (SelectItem item : node.getSelect().getSelectItems()) {
                if (item instanceof SingleColumn) {
                    SingleColumn column = (SingleColumn) item;

                    Expression exp = column.getExpression();

                    if(exp instanceof DereferenceExpression){
                        DereferenceExpression deRef = (DereferenceExpression) exp;
                        Field field = analysis.resoveField(deRef.getBase().toString(),deRef.getField().getValue());
                        analysis.addOutField(field);
                        analysis.addTableFields(field);

                    }else if(exp instanceof Identifier){
                        Identifier colName = (Identifier) exp;
                        Field field = analysis.resoveField(colName.getValue());
                        analysis.addOutField(field);
                        analysis.addTableFields(field);

                    }else{
                        throw new UnsupportedOperationException("unknown exp type "+exp.getClass().getName());
                    }
                }
            }
        }

        private Void analyzeFrom(QuerySpecification node)
        {
            if (node.getFrom().isPresent()) {
                return process(node.getFrom().get());
            }
            return null;
        }

        @Override
        protected Void visitJoin(Join node, Void context) {

            process(node.getLeft());

            process(node.getRight());

            return null;
        }


        @Override
        protected Void visitAliasedRelation(AliasedRelation node, Void context) {
            Table table = (Table) node.getRelation();
            process(table);
            String alias = node.getAlias().toString();
            analysis.addAlias(alias,table.getName().toString());
            return null;
        }

        @Override
        protected Void visitTable(Table node, Void context) {

            String tableName = node.getName().toString();
            List<Field> fieldList = metadata.getFieldsByTable(tableName);

            if(fieldList==null){
                throw new IllegalArgumentException("unknown table "+tableName);
            }

            analysis.initTableFields(tableName, fieldList);

            return null;
        }
    }
}

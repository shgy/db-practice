package org.example.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.example.antlr.SqlBaseLexer;
import org.example.antlr.SqlBaseParser;
import org.example.antlr.SqlBaseVisitor;
import org.example.tree.Node;
import org.example.tree.Statement;

import java.util.function.Function;

public class SqlParser {

    /**
     * Consider using {@link #createStatement(String, ParsingOptions)}
     */
    @Deprecated
    public Statement createStatement(String sql)
    {
        return createStatement(sql, new ParsingOptions());
    }

    public Statement createStatement(String sql, ParsingOptions parsingOptions)
    {
        return (Statement) invokeParser("statement", sql, SqlBaseParser::singleStatement, parsingOptions);
    }

    private Node invokeParser(String name, String sql, Function<SqlBaseParser, ParserRuleContext> parseFunction, ParsingOptions parsingOptions)
    {
        try {
            SqlBaseLexer lexer = new SqlBaseLexer(new CaseInsensitiveStream(new ANTLRInputStream(sql)));
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            SqlBaseParser parser = new SqlBaseParser(tokenStream);


            lexer.removeErrorListeners();

            parser.removeErrorListeners();

            ParserRuleContext tree;
            try {
                // first, try parsing with potentially faster SLL mode
                parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
                tree = parseFunction.apply(parser);
            }
            catch (ParseCancellationException ex) {
                // if we fail, parse with LL mode
                tokenStream.reset(); // rewind input stream
                parser.reset();

                parser.getInterpreter().setPredictionMode(PredictionMode.LL);
                tree = parseFunction.apply(parser);
            }

            return new AstBuilder(parsingOptions).visit(tree);
        }
        catch (StackOverflowError e) {
            throw new ParsingException(name + " is too large (stack overflow while parsing)");
        }
    }
}

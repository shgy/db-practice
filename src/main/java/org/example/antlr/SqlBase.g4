
grammar SqlBase;

tokens {
    DELIMITER
}

singleStatement
    : statement EOF
    ;

statement
    : query                                                            #statementDefault
    ;

query
    :  queryNoWith
    ;

queryNoWith:
      queryTerm
    ;

queryTerm
    : queryPrimary                                                             #queryTermDefault
    ;

queryPrimary
    : querySpecification                   #queryPrimaryDefault
    ;

querySpecification
    : SELECT  selectItem (',' selectItem)*
      (FROM relation (',' relation)*)?
    ;

selectItem
    : expression  #selectSingle
    ;

relation
    :  sampledRelation                             #relationDefault
    ;

expression
    : booleanExpression
    ;

booleanExpression
    : valueExpression             #predicated
    ;

valueExpression
    : primaryExpression                                                                 #valueExpressionDefault
    ;

primaryExpression
    : identifier                                                                          #columnReference
    ;

sampledRelation
    : aliasedRelation
    ;

aliasedRelation
    : relationPrimary
    ;

relationPrimary
    : qualifiedName                                                   #tableName
    ;

qualifiedName
    : identifier ('.' identifier)*
    ;

identifier
    : IDENTIFIER             #unquotedIdentifier
    ;

SELECT: 'SELECT';
FROM: 'FROM';

fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;

IDENTIFIER
    : (LETTER | '_') (LETTER | DIGIT | '_' | '@' | ':')*
    ;

WS
    : [ \r\n\t]+ -> channel(HIDDEN)
    ;

// Catch-all for anything we can't recognize.
// We use this to be able to ignore and recover all the text
// when splitting statements with DelimiterLexer
UNRECOGNIZED
    : .
    ;

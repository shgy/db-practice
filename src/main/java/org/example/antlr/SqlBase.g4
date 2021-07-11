
grammar SqlBase;

tokens {
    DELIMITER
}

singleStatement
    : statement EOF
    ;

singleExpression
    : expression EOF
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
      (WHERE where=booleanExpression)?
    ;

selectItem
    : expression  #selectSingle
    ;

relation
    : left=relation
      ( joinType JOIN rightRelation=relation joinCriteria
      )                                           #joinRelation
    | sampledRelation                             #relationDefault
    ;

joinType
    : INNER?
    ;

joinCriteria
    : ON booleanExpression
    ;

expression
    : booleanExpression
    ;

booleanExpression
    : valueExpression predicate[$valueExpression.ctx]?             #predicated
    | NOT booleanExpression                                        #logicalNot
    | left=booleanExpression operator=AND right=booleanExpression  #logicalBinary
    | left=booleanExpression operator=OR right=booleanExpression   #logicalBinary
    ;

// workaround for https://github.com/antlr/antlr4/issues/780
predicate[ParserRuleContext value]
    : comparisonOperator right=valueExpression                            #comparison
    ;

valueExpression
    : primaryExpression                                                                 #valueExpressionDefault
    ;

primaryExpression
    : identifier                                                                          #columnReference
    | number                                                                              #numericLiteral
    | booleanValue                                                                        #booleanLiteral
    | string                                                                              #stringLiteral
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

number
    : INTEGER_VALUE  #integerLiteral
    ;
booleanValue
    : TRUE | FALSE
    ;
string
    : STRING                                #basicStringLiteral
    ;

comparisonOperator
    : EQ | NEQ | LT | LTE | GT | GTE
    ;

identifier
    : IDENTIFIER             #unquotedIdentifier
    ;



AND: 'AND';
FROM: 'FROM';
FALSE: 'FALSE';
JOIN: 'JOIN';
NOT: 'NOT';
OR: 'OR';
ON: 'ON';
SELECT: 'SELECT';
TRUE: 'TRUE';
WHERE: 'WHERE';

EQ  : '=';
NEQ : '<>' | '!=';
LT  : '<';
LTE : '<=';
GT  : '>';
GTE : '>=';

fragment DIGIT
    : [0-9]
    ;

fragment LETTER
    : [A-Z]
    ;

STRING
    : '\'' ( ~'\'' | '\'\'' )* '\''
    ;

INTEGER_VALUE
    : DIGIT+
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

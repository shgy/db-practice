package org.example.codegen;

import com.google.common.collect.ImmutableList;
import io.airlift.bytecode.*;
import io.airlift.bytecode.expression.BytecodeExpression;
import io.airlift.bytecode.expression.BytecodeExpressions;
import org.example.filter.RowFilter;
import org.example.tree.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import static io.airlift.bytecode.Access.*;
import static io.airlift.bytecode.BytecodeUtils.toJavaIdentifierString;
import static io.airlift.bytecode.ClassGenerator.classGenerator;
import static io.airlift.bytecode.Parameter.arg;
import static io.airlift.bytecode.ParameterizedType.type;
import static io.airlift.bytecode.ParameterizedType.typeFromJavaClassName;
import static io.airlift.bytecode.expression.BytecodeExpressions.*;
import static java.time.ZoneOffset.UTC;

/**
 * 生成filter代码
 */
public class WhereExprFilterCompiler extends AstVisitor<Void,MethodDefinition> {
    private ClassDefinition classDefinition;
    private static final String CLASS_NAME="DynamicRowFilter";
    private String METHOD_NAME="filter";

    private void buildClass(){
        ClassDefinition classDefinition = new ClassDefinition(
                a(PUBLIC, FINAL),
                CompilerUtils.makeClassName(CLASS_NAME),type(Object.class),
                type(RowFilter.class));

        MethodDefinition consutructor = classDefinition.declareConstructor(
                a(PUBLIC));
        Variable thisVariable = consutructor.getThis();
        consutructor.getBody().comment("super();")
                .append(thisVariable)
                .invokeConstructor(Object.class)
                .ret();

        this.classDefinition=classDefinition;
    }

    public Class compile(Expression expression){
        buildClass();

        Parameter argRow = arg("row", Map.class);
        MethodDefinition method = classDefinition.declareMethod(
                a(PUBLIC),METHOD_NAME,
                type(boolean.class),
                ImmutableList.of(argRow));

        Scope scope = method.getScope();
        // new Stack()
        Variable stack = scope.declareVariable(Stack.class,"stack");
        method.getBody().append(BytecodeExpressions.newInstance(Stack.class)).putVariable(stack);

        if(expression instanceof ComparisonExpression){
            visitComparisonExpression((ComparisonExpression) expression, method);
        }else  if(expression instanceof LogicalBinaryExpression){
            visitLogicalBinaryExpression((LogicalBinaryExpression) expression,method);
        }else{
            throw new UnsupportedOperationException("not implemented");
        }
        method.getBody().append(stack.invoke("pop",Object.class).cast(boolean.class)).retBoolean();
        Class<?> clazz = classGenerator(this.getClass().getClassLoader())
                .defineClass(classDefinition, Object.class);
        return clazz;
    }

    private void visitLogicalBianryOrComparison(Expression node, MethodDefinition context){
        if(node instanceof LogicalBinaryExpression){
            visitLogicalBinaryExpression((LogicalBinaryExpression) node,context);

        }else if(node instanceof ComparisonExpression){
            visitComparisonExpression((ComparisonExpression) node,context);
        }else{
            throw new UnsupportedOperationException("not implemented");
        }
    }
    @Override
    protected Void visitLogicalBinaryExpression(LogicalBinaryExpression node, MethodDefinition context) {
        visitLogicalBianryOrComparison(node.getLeft(),context);
        visitLogicalBianryOrComparison(node.getRight(),context);

        Variable stack = context.getScope().getVariable("stack");
        BytecodeExpression cResult;
        switch (node.getOperator()){
            case OR:
                cResult = or(stack.invoke("pop",Object.class).cast(boolean.class)
                        ,stack.invoke("pop",Object.class).cast(boolean.class));
                break;
            case AND:
                cResult = and(stack.invoke("pop",Object.class).cast(boolean.class)
                        ,stack.invoke("pop",Object.class).cast(boolean.class));
                break;
            default:
                throw new UnsupportedOperationException("not implemented");
        }
        context.getBody().append(stack.invoke("push",Object.class,cResult.cast(Object.class)));
        return null;
    }

    @Override
    protected Void visitComparisonExpression(ComparisonExpression node, MethodDefinition context) {
        Expression left= node.getLeft(),right= node.getRight();

        if(left instanceof Identifier && right instanceof LongLiteral){

            String leftKey = ((Identifier) left).getValue();

            BytecodeExpression rightKey = constantLong(((LongLiteral) right).getValue());

            Parameter argRow = context.getParameters().get(0);
            Variable stack = context.getScope().getVariable("stack");
            BytecodeBlock body = context.getBody();

            BytecodeExpression leftVal = argRow.invoke("get",Object.class,constantString(leftKey).cast(Object.class)).cast(long.class);
            BytecodeExpression cResult;
            switch (node.getOperator()){
                case EQUAL:
                    cResult = equal(leftVal,rightKey);
                    break;
                case GREATER_THAN:
                    cResult = greaterThan(leftVal,rightKey);
                    break;
                case GREATER_THAN_OR_EQUAL:
                    cResult = greaterThanOrEqual(leftVal,rightKey);
                    break;
                case LESS_THAN:
                    cResult = lessThan(leftVal,rightKey);
                    break;
                case NOT_EQUAL:
                    cResult = notEqual(leftVal,rightKey);
                    break;
                case LESS_THAN_OR_EQUAL:
                    cResult = lessThanOrEqual(leftVal,rightKey);
                    break;
                default:
                    throw new UnsupportedOperationException("not implemented");
            }
            body.append(stack.invoke("push",Object.class,cResult.cast(Object.class)));

        }else{
            throw new UnsupportedOperationException("not implemented");
        }
        return null;
    }
}

package org.example.codegen;

import com.google.common.collect.ImmutableList;
import io.airlift.bytecode.ClassDefinition;
import io.airlift.bytecode.MethodDefinition;
import io.airlift.bytecode.Parameter;
import io.airlift.bytecode.ParameterizedType;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.airlift.bytecode.Access.FINAL;
import static io.airlift.bytecode.Access.PUBLIC;
import static io.airlift.bytecode.Access.STATIC;
import static io.airlift.bytecode.Access.a;
import static io.airlift.bytecode.ClassGenerator.classGenerator;
import static io.airlift.bytecode.Parameter.arg;
import static io.airlift.bytecode.ParameterizedType.type;
import static io.airlift.bytecode.expression.BytecodeExpressions.add;
public class CodeGenDemo {

    private ClassDefinition classDefinition;

    private void buildClass(){
        ClassDefinition classDefinition = new ClassDefinition(
                a(PUBLIC, FINAL),
                ParameterizedType.typeFromJavaClassName("org.example.codegen.RowFilter"),
                type(Object.class));
        this.classDefinition = classDefinition;
    }

    private void buildMethodAdd(){
        Parameter argA = arg("a", int.class);
        Parameter argB = arg("b", int.class);

        MethodDefinition method = classDefinition.declareMethod(
                a(PUBLIC, STATIC),
                "add",
                type(int.class),
                ImmutableList.of(argA, argB));

        method.getBody()
                .append(add(argA, argB))
                .retInt();

    }


    public void executeAdd() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = classGenerator(getClass().getClassLoader())
                .defineClass(classDefinition, Object.class);

        Method add = clazz.getMethod("add", int.class, int.class);
        System.out.println(add.invoke(null, 13, 42));
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        CodeGenDemo demo = new CodeGenDemo();
        demo.buildClass();
        demo.buildMethodAdd();
        demo.executeAdd();

    }
}

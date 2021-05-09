package org.example.calc;

import java.util.*;

public class CalcByHand {
    public static Set<String> opSet1 = new HashSet<>();
    public static Set<String> opSet2 = new HashSet<>();
    static{
        opSet1.add("+");
        opSet1.add("-");
        opSet2.add("*");
        opSet2.add("/");
    }
    public static void main(String[] args) {
        String exp="11+3*4";
        String[] tokens = exp.split("((?<=[\\+|\\-|\\*|\\/])|(?=[\\+|\\-|\\*|\\/]))");
        Stack<String> opStack = new Stack<>();
        Stack<String> numStack = new Stack<>();
        int proi=1;
        for(String token: tokens){
            token = token.trim();

            if(opSet1.contains(token)){
                opStack.push(token);
                proi=1;
            }else if(opSet2.contains(token)){
                proi=2;
                opStack.push(token);
            }else{
                numStack.push(token);
                if(proi==2){
                    calcExp(opStack,numStack);
                }
            }
        }

        while (!opStack.isEmpty()){
            calcExp(opStack,numStack);
        }
        String finalVal = numStack.pop();
        System.out.println(finalVal);
    }

    private static void calcExp(Stack<String> opStack, Stack<String> numStack) {
        double right=Double.valueOf(numStack.pop());
        double left = Double.valueOf(numStack.pop());
        String op = opStack.pop();
        String val;
        switch (op){
            case "+":
                 val =String.valueOf(left+right);
                break;
            case "-":
                 val =String.valueOf(left-right);
                break;
            case "*":
                val =String.valueOf(left*right);
                break;
            case "/":
                val =String.valueOf(left/right);
                break;
            default:
                throw new UnsupportedOperationException("unsupported");
        }
        numStack.push(val);
    }
}

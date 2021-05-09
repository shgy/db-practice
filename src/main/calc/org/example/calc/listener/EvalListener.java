package org.example.calc.listener;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class EvalListener implements LabeledExprListener{

    private Stack<Double> numStack = new Stack<>();
    @Override
    public void enterProg(LabeledExprParser.ProgContext ctx) {

    }

    @Override
    public void exitProg(LabeledExprParser.ProgContext ctx) {

    }

    @Override
    public void enterPrintExpr(LabeledExprParser.PrintExprContext ctx) {

    }

    @Override
    public void exitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Double result = numStack.pop();
        System.out.println("final result: "+result);
    }

    @Override
    public void enterAssign(LabeledExprParser.AssignContext ctx) {

    }

    @Override
    public void exitAssign(LabeledExprParser.AssignContext ctx) {

    }

    @Override
    public void enterBlank(LabeledExprParser.BlankContext ctx) {

    }

    @Override
    public void exitBlank(LabeledExprParser.BlankContext ctx) {

    }

    @Override
    public void enterParens(LabeledExprParser.ParensContext ctx) {

    }

    @Override
    public void exitParens(LabeledExprParser.ParensContext ctx) {

    }

    @Override
    public void enterMulDiv(LabeledExprParser.MulDivContext ctx) {

    }

    @Override
    public void exitMulDiv(LabeledExprParser.MulDivContext ctx) {
        Double left = numStack.pop();
        Double right= numStack.pop();
        Double result;
        if (ctx.op.getType() == LabeledExprParser.MUL) {
            result = left * right;
        } else {
            result = left / right;
        }
        numStack.push(result);
    }

    @Override
    public void enterAddSub(LabeledExprParser.AddSubContext ctx) {

    }

    @Override
    public void exitAddSub(LabeledExprParser.AddSubContext ctx) {
        Double left = numStack.pop();
        Double right= numStack.pop();
        Double result;
        if (ctx.op.getType() == LabeledExprParser.ADD) {
            result = left + right;
        } else {
            result = left - right;
        }
        numStack.push(result);
    }

    @Override
    public void enterId(LabeledExprParser.IdContext ctx) {

    }

    @Override
    public void exitId(LabeledExprParser.IdContext ctx) {

    }

    @Override
    public void enterInt(LabeledExprParser.IntContext ctx) {

    }

    @Override
    public void exitInt(LabeledExprParser.IntContext ctx) {
        numStack.push(Double.valueOf(ctx.INT().getText()));
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}

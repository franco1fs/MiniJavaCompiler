package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.Module;

import java.util.ArrayList;

public class MethodChainCall extends ChainCall{

    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String methodName;

    public MethodChainCall(ArrayList<ExpressionNode> args, String methodName,int lineNumber) {
        this.args = args;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }
}

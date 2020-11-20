package ast.expression.operating;

import ast.expression.ExpressionNode;
import ast.expression.operating.PrimaryNode;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessMethodNode extends PrimaryNode {

    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String methodName;
    private Class myClass;

    public AccessMethodNode(ArrayList<ExpressionNode> args, String methodName, Class myClass,int lineNumber) {
        this.args = args;
        this.methodName = methodName;
        this.myClass = myClass;
        this.lineNumber = lineNumber;
    }

    @Override
    public void check() {

    }
}

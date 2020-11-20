package ast.expression.operating;

import ast.expression.ExpressionNode;
import ast.expression.operating.PrimaryNode;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessConstructorNode extends PrimaryNode {
    private ArrayList<ExpressionNode> currentArgs = new ArrayList<ExpressionNode>();
    private Class myClass;

    public AccessConstructorNode(ArrayList<ExpressionNode> currentArgs,Class myClass, int lineNumber) {
        this.currentArgs = currentArgs;
        this.myClass = myClass;
        this.lineNumber = lineNumber;
    }

    @Override
    public void check() {

    }
}

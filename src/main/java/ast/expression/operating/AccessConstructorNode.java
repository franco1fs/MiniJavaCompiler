package ast.expression.operating;

import ast.expression.ExpressionNode;
import ast.expression.operating.PrimaryNode;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessConstructorNode extends PrimaryNode {
    private ArrayList<ExpressionNode> currentArgs = new ArrayList<ExpressionNode>();
    private Class myClass;

    public AccessConstructorNode(ArrayList<ExpressionNode> currentArgs,Class myClass) {
        this.currentArgs = currentArgs;
        this.myClass = myClass;
    }

}

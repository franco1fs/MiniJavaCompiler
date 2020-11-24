package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.Class;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

import java.util.ArrayList;

public class AccessConstructorNode extends PrimaryNode {
    private ArrayList<ExpressionNode> currentArgs = new ArrayList<ExpressionNode>();
    private Class myClass;

    public AccessConstructorNode(ArrayList<ExpressionNode> currentArgs,Class myClass, int lineNumber) {
        this.currentArgs = currentArgs;
        this.myClass = myClass;
        this.lineNumber = lineNumber;
    }

    public MethodType check() throws SemanticErrorException {
        return null;
    }
}

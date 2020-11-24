package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class ParentOperantExpressionNode extends PrimaryNode {
    private ExpressionNode expressionNode;

    public ParentOperantExpressionNode(ExpressionNode expressionNode,int lineNumber){
        this.expressionNode = expressionNode;
        this.lineNumber = lineNumber;
    }
    public MethodType check() throws SemanticErrorException {
        return null;
    }
}

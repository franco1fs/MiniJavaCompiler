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
        return expressionNode.check();
    }

    @Override
    public String getLexemeOfRepresentation() {
        return "";
    }

    @Override
    public void generate() {
        expressionNode.generate();
    }
}

package ast.expression.operating;

import ast.expression.ExpressionNode;
import ast.expression.operating.PrimaryNode;

public class ParentOperantExpressionNode extends PrimaryNode {
    private ExpressionNode expressionNode;

    public ParentOperantExpressionNode(ExpressionNode expressionNode,int lineNumber){
        this.expressionNode = expressionNode;
        this.lineNumber = lineNumber;
    }
    @Override
    public void check() {

    }
}

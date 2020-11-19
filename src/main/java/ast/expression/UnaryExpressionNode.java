package ast.expression;

import ast.expression.operating.OperantNode;

public class UnaryExpressionNode extends ExpressionNode {
    private OperantNode operantNode;
    private String unaryOperator;

    public UnaryExpressionNode(OperantNode operantNode,int lineNumber){
        this.operantNode = operantNode;
        this.lineNumber = lineNumber;
    }

    public void setUnaryOperator(String unaryOperator){
        this.unaryOperator = unaryOperator;
    }
    @Override
    public void check() {

    }
}

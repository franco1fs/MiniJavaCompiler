package ast.expression;

import ast.expression.ExpressionNode;

public class BinaryExpressionNode extends ExpressionNode {

    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    private String binaryOperator;

    public BinaryExpressionNode(ExpressionNode leftSide, ExpressionNode rightSide, String binaryOperator) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.binaryOperator = binaryOperator;
    }

    @Override
    public void check() {

    }
}

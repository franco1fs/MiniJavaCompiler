package ast.expression;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class BinaryExpressionNode extends ExpressionNode {

    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    private String binaryOperator;

    public BinaryExpressionNode(ExpressionNode leftSide, ExpressionNode rightSide, String binaryOperator) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.binaryOperator = binaryOperator;
    }

    public MethodType check() throws SemanticErrorException {
        return null;
    }
}

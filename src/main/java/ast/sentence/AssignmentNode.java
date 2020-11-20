package ast.sentence;

import ast.expression.ExpressionNode;
import ast.expression.operating.AccessNode;
import ast.sentence.SentenceNode;

public class AssignmentNode extends SentenceNode {

    // The access Node could be a var or This Access
    private AccessNode leftAccessNode;
    private ExpressionNode rightExpressionNode;
    private String assignmentType;

    public AssignmentNode(AccessNode leftAccessNode, ExpressionNode rightExpressionNode,String assignmentType) {
        this.leftAccessNode = leftAccessNode;
        this.rightExpressionNode = rightExpressionNode;
        this.lineNumber = lineNumber;

    }

    @Override
    public void check() {

    }
}

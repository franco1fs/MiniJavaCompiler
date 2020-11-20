package ast.sentence;

import ast.expression.ExpressionNode;
import ast.sentence.SentenceNode;

public class WhileNode extends SentenceNode {
    private ExpressionNode expressionNode;
    private SentenceNode sentenceNode;

    public WhileNode(ExpressionNode expressionNode, SentenceNode sentenceNode) {
        this.expressionNode = expressionNode;
        this.sentenceNode = sentenceNode;
    }

    @Override
    public void check() {

    }
}
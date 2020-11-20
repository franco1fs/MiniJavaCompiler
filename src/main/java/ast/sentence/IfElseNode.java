package ast.sentence;

import ast.expression.ExpressionNode;
import ast.sentence.SentenceNode;

public class IfElseNode extends SentenceNode {

    private ExpressionNode expressionNode;
    private SentenceNode ifSentence;
    private SentenceNode elseSentence;

    public IfElseNode(ExpressionNode expressionNode, SentenceNode ifSentence, SentenceNode elseSentence) {
        this.expressionNode = expressionNode;
        this.ifSentence = ifSentence;
        this.elseSentence = elseSentence;
    }

    @Override
    public void check() {

    }
}

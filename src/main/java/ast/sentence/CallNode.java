package ast.sentence;

import ast.expression.operating.AccessConstructorNode;
import ast.expression.operating.AccessNode;
import ast.sentence.SentenceNode;

public class CallNode extends SentenceNode {

    private AccessNode accessNode;

    public CallNode(AccessNode accessNode) {
        this.accessNode = accessNode;
    }

    @Override
    public void check() {

    }
}

package ast.sentence;

import ast.expression.operating.AccessNode;
import symbolTable.SemanticErrorException;

public class CallNode extends SentenceNode {

    private AccessNode accessNode;

    public CallNode(AccessNode accessNode) {
        this.accessNode = accessNode;
    }

    @Override
    public void check() throws SemanticErrorException {

    }
}

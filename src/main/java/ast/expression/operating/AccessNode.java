package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class AccessNode extends OperantNode {
    private PrimaryNode primaryNode;
    private ChainCallContainer chainCallContainer;

    public AccessNode(PrimaryNode primaryNode, ChainCallContainer chainCallContainer) {
        this.primaryNode = primaryNode;
        this.chainCallContainer = chainCallContainer;
    }

    public MethodType check() throws SemanticErrorException {
        return null;
    }
}

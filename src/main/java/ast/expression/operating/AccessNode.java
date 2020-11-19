package ast.expression.operating;

import ast.expression.operating.PrimaryNode;

public class AccessNode extends OperantNode {
    private PrimaryNode primaryNode;
    private ChainCallContainer chainCallContainer;

    public AccessNode(PrimaryNode primaryNode, ChainCallContainer chainCallContainer) {
        this.primaryNode = primaryNode;
        this.chainCallContainer = chainCallContainer;
    }

    @Override
    public void check() {

    }
}

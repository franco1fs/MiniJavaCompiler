package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class AccessNode extends OperantNode {
    private PrimaryNode primaryNode;
    private ChainCallContainer chainCallContainer;

    public AccessNode(PrimaryNode primaryNode, ChainCallContainer chainCallContainer) {
        this.primaryNode = primaryNode;
        this.chainCallContainer = chainCallContainer;

        if(chainCallContainer.getChainCallArrayList().size()>0){
            primaryNode.setHasChained();
        }
    }

    public PrimaryNode getPrimaryNode() {
        return primaryNode;
    }

    public ChainCallContainer getChainCallContainer() {
        return chainCallContainer;
    }

    public MethodType check() throws SemanticErrorException {
        MethodType type = primaryNode.check();

        return chainCallContainer.check(type);
    }

    @Override
    public void generate() {
        primaryNode.generate();
    }
}

package ast.expression.operating;



public class VarChainCall extends ChainCall {

    private String varName;

    public VarChainCall(String varName) {
        this.varName = varName;
    }
}

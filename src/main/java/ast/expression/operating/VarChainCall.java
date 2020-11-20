package ast.expression.operating;



public class VarChainCall extends ChainCall {

    private String varName;

    public VarChainCall(String varName,int lineNumber) {
        this.varName = varName;
        this.lineNumber=lineNumber;
    }
}

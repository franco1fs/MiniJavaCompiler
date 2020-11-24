package ast.expression.operating;


import symbolTable.SemanticErrorException;
import symbolTable.Type;

public class VarChainCall extends ChainCall {

    private String varName;

    public VarChainCall(String varName,int lineNumber) {
        this.varName = varName;
        this.lineNumber=lineNumber;
    }

    public Type check() throws SemanticErrorException {
        return null;
    }
}

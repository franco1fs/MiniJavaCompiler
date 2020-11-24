package ast.expression.operating;

import symbolTable.SemanticErrorException;
import symbolTable.Type;

public abstract class ChainCall {
    protected int lineNumber;

    public abstract Type check() throws SemanticErrorException;
}

package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.Type;

public abstract class ChainCall {
    protected int lineNumber;

    public abstract MethodType check(MethodType type) throws SemanticErrorException;

    public abstract String getLexemeOfRepresentation();
}

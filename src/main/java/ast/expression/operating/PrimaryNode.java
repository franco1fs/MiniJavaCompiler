package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public abstract class PrimaryNode  {
    protected int lineNumber;

    public abstract MethodType check() throws SemanticErrorException;

    public abstract String getLexemeOfRepresentation();

}

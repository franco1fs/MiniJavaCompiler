package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public abstract class PrimaryNode  {
    protected int lineNumber;
    protected boolean hasChained = false;

    public void setHasChained(){
        hasChained = true;
    }
    public abstract MethodType check() throws SemanticErrorException;

    public abstract String getLexemeOfRepresentation();

    public abstract void generate();

}

package ast.expression;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public abstract class ExpressionNode {
    protected int lineNumber;

    public int getLineNumber(){
        return lineNumber;
    }
    public abstract MethodType check() throws SemanticErrorException;

    public abstract void generate();
}

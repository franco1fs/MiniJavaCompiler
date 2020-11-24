package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.Type;

public class LiteralNode extends OperantNode {
    private Type type;
    private String value;

    public LiteralNode(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public MethodType check() throws SemanticErrorException {
        return type;
    }
}

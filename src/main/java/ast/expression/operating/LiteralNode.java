package ast.expression.operating;

import symbolTable.Type;

public class LiteralNode extends OperantNode {
    private Type type;
    private String value;

    public LiteralNode(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public void check() {

    }
}

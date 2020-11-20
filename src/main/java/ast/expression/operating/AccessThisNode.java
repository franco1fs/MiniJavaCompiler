package ast.expression.operating;

import ast.expression.operating.AccessNode;

public class AccessThisNode extends PrimaryNode {
    public AccessThisNode(int lineNumber){
        this.lineNumber = lineNumber;
    }

    @Override
    public void check() {

    }
}

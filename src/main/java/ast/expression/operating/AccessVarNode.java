package ast.expression.operating;

import ast.expression.operating.AccessNode;

public class AccessVarNode extends PrimaryNode {

    private String varName;

    public AccessVarNode(String varName,int lineNumber) {
        this.varName = varName;
        this.lineNumber = lineNumber;
    }


    @Override
    public void check() {

    }
}

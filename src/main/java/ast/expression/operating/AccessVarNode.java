package ast.expression.operating;

import ast.expression.operating.AccessNode;

public class AccessVarNode extends PrimaryNode {

    private String varName;

    public AccessVarNode(String varName) {
        this.varName = varName;
    }


}

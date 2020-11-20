package ast.expression.operating;

import ast.expression.operating.PrimaryNode;
import symbolTable.Class;

import java.beans.Expression;
import java.util.ArrayList;

public class AccessStaticNode extends PrimaryNode {

    //private ArrayList<Expression> args = new ArrayList<Expression>();
    private String idClass;
    private AccessMethodNode accessMethodNode;

    public AccessStaticNode(String idClass, AccessMethodNode accessMethodNode,int lineNumber) {
        this.idClass = idClass;
        this.accessMethodNode = accessMethodNode;
        this.lineNumber = lineNumber;
    }


    @Override
    public void check() {

    }
}

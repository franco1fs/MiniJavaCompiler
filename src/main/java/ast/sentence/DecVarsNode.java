package ast.sentence;

import ast.sentence.SentenceNode;
import symbolTable.Type;

import java.util.ArrayList;

public class DecVarsNode extends SentenceNode {
    private Type type;
    private ArrayList<String> vars;

    public DecVarsNode(ArrayList<String> vars,Type type) {
        this.vars = vars;
        this.type = type;
    }

    @Override
    public void check() {

    }
}

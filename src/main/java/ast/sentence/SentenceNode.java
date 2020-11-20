package ast.sentence;

import ast.INode;

public abstract class SentenceNode implements INode {
    protected int lineNumber;

    public void setLineNumber(int lineNumber){
        this.lineNumber = lineNumber;
    }
}

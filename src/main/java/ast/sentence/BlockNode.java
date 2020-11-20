package ast.sentence;

import ast.sentence.SentenceNode;
import symbolTable.Unit;

import java.util.ArrayList;

public class BlockNode extends SentenceNode {
    private BlockNode fatherBlock;
    private ArrayList<SentenceNode> sentences= new ArrayList<SentenceNode>();
    private Unit unitWhereIBelong; // The unit knows the Module where belong

    public BlockNode(BlockNode fatherBlock,Unit unitWhereIBelong) {
        this.fatherBlock = fatherBlock;
        this.unitWhereIBelong = unitWhereIBelong;
    }


    public ArrayList<SentenceNode> getSentences() {
        return sentences;
    }

    public Unit getUnitWhereIBelong() {
        return unitWhereIBelong;
    }

    public void setSentences(ArrayList<SentenceNode> sentences) {
        this.sentences = sentences;
    }

    @Override
    public void check() {

    }
}

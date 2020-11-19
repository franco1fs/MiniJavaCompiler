package ast.expression.operating;

import symbolTable.Module;

import java.util.ArrayList;

public class ChainCallContainer {
    protected ArrayList<ChainCall> chainCallArrayList = new ArrayList<ChainCall>();
    private Module myModule;

    public ChainCallContainer(ArrayList<ChainCall> chainCallArrayList, Module myModule) {
        this.chainCallArrayList = chainCallArrayList;
        this.myModule = myModule;
    }
}

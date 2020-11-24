package ast.expression.operating;

import symbolTable.Module;
import symbolTable.SemanticErrorException;
import symbolTable.Type;

import java.util.ArrayList;

public class ChainCallContainer {
    protected ArrayList<ChainCall> chainCallArrayList = new ArrayList<ChainCall>();
    private Module theModuleWhereCallOccur;

    public ChainCallContainer(ArrayList<ChainCall> chainCallArrayList, Module myModule) {
        this.chainCallArrayList = chainCallArrayList;
        this.theModuleWhereCallOccur = myModule;
    }

    public Type check() throws SemanticErrorException {
        return null;
    }
}

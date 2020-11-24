package ast.expression.operating;

import symbolTable.*;

import java.util.ArrayList;

public class ChainCallContainer {

    protected ArrayList<ChainCall> chainCallArrayList = new ArrayList<ChainCall>();
    private Module theModuleWhereCallOccur;

    public ChainCallContainer(ArrayList<ChainCall> chainCallArrayList, Module myModule) {
        this.chainCallArrayList = chainCallArrayList;
        this.theModuleWhereCallOccur = myModule;
    }

    public MethodType check(MethodType type) throws SemanticErrorException {
        if(!(type instanceof TidClass) && chainCallArrayList.size()>0){
            throw new SemanticErrorException(type.getTypeName(),type.getLineNumber(),"Error Semantico en la linea: "+
                    type.getLineNumber()+" no es posible tener un encadenado sobre un tipo que no es de Clase "+
                    type.getTypeName());
        }
        else{
            if(chainCallArrayList.size()==0){
                return type;
            }
            else{
                MethodType toRet=type;
                for(ChainCall chainCall: chainCallArrayList){
                    toRet = chainCall.check(toRet);
                }
                return toRet;
            }
        }
    }

    public ArrayList<ChainCall> getChainCallArrayList() {
        return chainCallArrayList;
    }

    public Module getTheModuleWhereCallOccur() {
        return theModuleWhereCallOccur;
    }
}

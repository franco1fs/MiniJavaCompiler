package ast.sentence;

import symbolTable.LocalVar;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;
import symbolTable.Type;

import java.util.ArrayList;

public class DecVarsNode extends SentenceNode {
    private Type type;
    private ArrayList<String> vars;
    private BlockNode blockWhereBelong;

    public DecVarsNode(ArrayList<String> vars,Type type, BlockNode blockNode) {
        this.vars = vars;
        this.type = type;
        this.blockWhereBelong = blockNode;
    }

    @Override
    public void check() throws SemanticErrorException {
        if(type.mustCheckTypeExistence()){
            if(!SymbolTable.getInstance().getClasses().containsKey(type.getTypeName())){
                throw new SemanticErrorException(type.getTypeName(),lineNumber,"Error semantico en la linea: "+
                        lineNumber+" el tipo de la variable: "+type.getTypeName()+" no Existe");
            }
        }
        for (String var: vars){
            blockWhereBelong.insertLocalVar(new LocalVar(type,var));
        }
    }
}

package symbolTable;

import java.util.ArrayList;

public class Constructor extends Unit{

    public Constructor(String name, Module myModule, ArrayList<Parameter> parameters) {
        this.name = name;
        this.myModule = myModule;
        this.parameters = parameters;
    }

    public Constructor(String name, Module myModule){
        this.name = name;
        this.myModule = myModule;

    }

    public Constructor(String name,int lineNumber, Module module){
        this.name = name;
        this.lineNumber = lineNumber;
        this.myModule = module;

    }

    public void checkConstructorParameterDeclaration() throws SemanticErrorException {
        for(Parameter p: parameters){
            p.checkTypeExistence(this);
        }
    }

    public void generate(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        symbolTable.genInstruction(".CODE");
        symbolTable.genInstruction(name+":");

        symbolTable.genInstruction("LOADFP");
        symbolTable.genInstruction("LOADSP");
        symbolTable.genInstruction("STOREFP");

        if(getMyBlock()!=null){
            getMyBlock().generate();
        }


        symbolTable.genInstruction("LOAD 3");
        symbolTable.genInstruction("STORE "+( 3 + parameters.size() + 1));
        symbolTable.genInstruction("STOREFP");
        symbolTable.genInstruction("RET "+parameters.size()+1);
    }


}

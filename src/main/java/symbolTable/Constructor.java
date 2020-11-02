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



}

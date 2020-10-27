package symbolTable;

import java.util.ArrayList;

public class Constructor extends Unit{

    public Constructor(String name, Module myModule, MethodType returnType, ArrayList<Parameter> parameters) {
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public Constructor(String name, Module myModule, MethodType returnType){
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
    }

}

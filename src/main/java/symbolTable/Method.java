package symbolTable;

import java.util.ArrayList;

public class Method extends Unit{

    private String methodForm;
    //private enum methodForm = {"static", "dynamic"};

    public Method(String name, Module myModule, MethodType returnType, ArrayList<Parameter> parameters) {
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public Method(String name, Module myModule, MethodType returnType, String methodForm) {
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
        this.methodForm = methodForm;
    }

}

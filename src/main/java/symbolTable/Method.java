package symbolTable;

import java.util.ArrayList;

public class Method extends Unit{

    private String methodForm;
    protected MethodType returnType;
    //private enum methodForm = {"static", "dynamic"};


    public Method(String name, Module myModule, MethodType returnType, ArrayList<Parameter> parameters, int lineNumber) {
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
        this.parameters = parameters;
        this.lineNumber = lineNumber;

    }

    public Method(String name, Module myModule, MethodType returnType, String methodForm,int lineNumber) {
        this.name = name;
        this.myModule = myModule;
        this.returnType = returnType;
        this.methodForm = methodForm;
        this.lineNumber = lineNumber;

    }



}

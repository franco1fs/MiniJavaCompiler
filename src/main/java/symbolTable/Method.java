package symbolTable;



import ast.expression.ExpressionNode;

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

    public void checkMethodTypeDeclaration() throws SemanticErrorException{
        if(returnType.mustCheckTypeExistence()){
            TidClass tidClass = (TidClass) returnType;
            if(!SymbolTable.getInstance().getClasses().containsKey(tidClass.getTypeName())){
                throw new SemanticErrorException(name,lineNumber,"Error Semantico en la linea: "+lineNumber+" el" +
                        "metodo "+name+" retorna un tipo no declarado");
            }
        }
        for(Parameter p: parameters){
            p.checkTypeExistence(this);
        }
    }

    public MethodType getReturnType(){
        return returnType;
    }

    public String getMethodForm(){
        return methodForm;
    }

    public boolean equalsForOverWrite(Method m2){
        boolean answer = false;
        if(name.equals(m2.getName()) && returnType.getTypeName().equals(m2.getReturnType().getTypeName()) &&
        methodForm.equals(m2.getMethodForm())){
            answer = compareParameters(m2);
        }
        return answer;
    }

    private boolean compareParameters(Method m2){
        boolean answer = true;
        ArrayList<Parameter> m2Parameters = m2.getParameters();
        if(parameters.size() == m2Parameters.size()){
            for (int index = 0; index<parameters.size();index++){
                answer = parameters.get(index).getType().getTypeName().
                        equals(m2Parameters.get(index).getType().getTypeName());
                if(!answer){
                    break;
                }
            }
        }
        else {
            answer = false;
        }
        return answer;
    }


}

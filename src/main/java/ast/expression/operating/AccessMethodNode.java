package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessMethodNode extends PrimaryNode {

    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String methodName;
    private Class myClass;
    private Method methodWhereBelong = null;
    private Method methodWhoWasCalled;

    public AccessMethodNode(ArrayList<ExpressionNode> args, String methodName, Class myClass,int lineNumber,Method method) {
        this.args = args;
        this.methodName = methodName;
        this.myClass = myClass;
        this.lineNumber = lineNumber;
        this.methodWhereBelong = method;
    }

    public AccessMethodNode(ArrayList<ExpressionNode> args, String methodName, Class myClass,int lineNumber) {
        this.args = args;
        this.methodName = methodName;
        this.myClass = myClass;
        this.lineNumber = lineNumber;
    }

    public ArrayList<ExpressionNode> getArgs() {
        return args;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class getMyClass() {
        return myClass;
    }

    //No chequeo que sea visible porque no entiendo a que se refiere
    public MethodType check() throws SemanticErrorException {
        Method methodCall = myClass.getMethodByName(methodName);
        if(methodCall==null){
            throw new SemanticErrorException(methodName,lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" acceso a metodo invalido debido a que no existe un Metodo con nombre: "+
                    methodName);
        }
        if( methodWhereBelong!= null && methodCall.getMethodForm().equals("dynamic") && methodWhereBelong.getMethodForm().equals("static")){
            throw new SemanticErrorException(methodName,lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" acceso a metodo dynamic invalido dentro de un metodo estatico: "+
                    methodName);
        }
        else{
            methodWhoWasCalled = methodCall;
            ArrayList<Parameter> parameters = methodCall.getParameters();
            if(parameters.size() == args.size()){
                for(int index = 0; index<parameters.size() ; index++){
                    if(!(parameters.get(index).getType().isConformedBy(args.get(index).check()))){
                        throw new SemanticErrorException(methodName,lineNumber,"Error Semantico en la linea: "+
                                lineNumber+" acceso a metodo invalido debido a que no hay conformidad de tipos: "+
                                methodName);
                    }
                }
                return methodCall.getReturnType();
            }
            else{
                throw new SemanticErrorException(methodName,lineNumber,"Error Semantico en la linea: "+
                        lineNumber+" acceso a metodo invalido debido a que la cantidad de argumentos es invalida: "+
                        methodName);
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return methodName;
    }

    @Override
    public void generate() {
        System.out.println("ESTOY EN AccessMethodNode"+this.toString());

        SymbolTable symbolTable = SymbolTable.getInstance();
        if(methodWhoWasCalled.getMethodForm().equals("static")){
            if(!methodWhoWasCalled.getReturnType().equals("void")){
                symbolTable.genInstruction("RMEM 1");
            }
            generateParamForStaticMethod();
            symbolTable.genInstruction("PUSH "+methodWhoWasCalled.getName());
        }
        else{
            symbolTable.genInstruction("LOAD 3");
            if(!methodWhoWasCalled.getReturnType().equals("void")){
                symbolTable.genInstruction("RMEM 1");
                symbolTable.genInstruction("SWAP");
            }
            generateParamForDynamicMethod();
            symbolTable.genInstruction("DUP");
            symbolTable.genInstruction("LOADREF 0");
            symbolTable.genInstruction("LOADREF "+methodWhoWasCalled.getOffsetVt()+1);
        }
        symbolTable.genInstruction("CALL");
    }

    private void generateParamForStaticMethod(){
        int index = args.size()-1;
        while(index >= 0){
            args.get(index).generate();
            index--;
        }
    }
    private void generateParamForDynamicMethod(){
        int index = args.size()-1;
        while(index >= 0){
            args.get(index).generate();
            SymbolTable.getInstance().genInstruction("SWAP");
            index--;
        }
    }

    private void checkArgs(Method method) throws SemanticErrorException{

    }
}

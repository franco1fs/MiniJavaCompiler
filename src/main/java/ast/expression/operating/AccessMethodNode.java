package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessMethodNode extends PrimaryNode {

    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String methodName;
    private Class myClass;

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
        else{
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

    private void checkArgs(Method method) throws SemanticErrorException{

    }
}

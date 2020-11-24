package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

public class AccessStaticNode extends PrimaryNode {

    //private ArrayList<Expression> args = new ArrayList<Expression>();
    private String idClass;
    private AccessMethodNode accessMethodNode;

    public AccessStaticNode(String idClass, AccessMethodNode accessMethodNode,int lineNumber) {
        this.idClass = idClass;
        this.accessMethodNode = accessMethodNode;
        this.lineNumber = lineNumber;
    }


    public MethodType check() throws SemanticErrorException {
        Class clase = SymbolTable.getInstance().getClass(idClass);
        if(clase == null){
            throw new SemanticErrorException(idClass,lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" acceso estatico invalido debido a que no existe una clase con el nombre: "+idClass);
        }
        else{
            Method methodWhoWhereCalled = clase.getMethodByName(accessMethodNode.getMethodName());
            if(methodWhoWhereCalled==null){
                throw new SemanticErrorException(idClass,lineNumber,"Error Semantico en la linea: "+
                        lineNumber+" acceso estatico invalido debido a que no existe un Metodo con nombre: "+
                        accessMethodNode.getMethodName());

            }
            else{
                if(methodWhoWhereCalled.getMethodForm().equals("static")){
                    ArrayList<Parameter> parameters = methodWhoWhereCalled.getParameters();
                    ArrayList<ExpressionNode> args = accessMethodNode.getArgs();

                    if(parameters.size() == args.size()){
                        for(int index = 0; index<parameters.size() ; index++){
                            if(!(parameters.get(index).getType().isConformedBy(args.get(index).check()))){
                                throw new SemanticErrorException(methodWhoWhereCalled.getName(),lineNumber,"Error Semantico en la linea: "+
                                        lineNumber+" acceso a metodo Estatico invalido debido a que no hay conformidad de tipos: "+
                                        methodWhoWhereCalled.getName());
                            }
                        }
                        return methodWhoWhereCalled.getReturnType();
                    }
                    else{
                        throw new SemanticErrorException(methodWhoWhereCalled.getName(),lineNumber,"Error Semantico en la linea: "+
                                lineNumber+" acceso a metodo Estatico invalido debido a que la cantidad de argumentos es invalida: "+
                                methodWhoWhereCalled.getName());
                    }
                }
                else{
                    throw new SemanticErrorException(idClass,lineNumber,"Error Semantico en la linea: "+
                            lineNumber+" acceso estatico invalido debido a que el metodo que se quiere acceder no esta " +
                            "declarado como estatico: "+
                            accessMethodNode.getMethodName());
                }
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return idClass;
    }
}

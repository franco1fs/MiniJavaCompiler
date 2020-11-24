package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AccessConstructorNode extends PrimaryNode {
    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String className;

    public AccessConstructorNode(ArrayList<ExpressionNode> currentArgs,String className, int lineNumber) {
        this.args = currentArgs;
        this.className = className;
        this.lineNumber = lineNumber;
    }

    public MethodType check() throws SemanticErrorException {
        Class clase = SymbolTable.getInstance().getClass(className);
        if(clase==null){
            throw new SemanticErrorException(className,lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" acceso a constructor inválido ya que no existe una clase con ese nombre "+
                    className);
        }
        else{
            Constructor constructor = clase.getConstructor();
            ArrayList<Parameter> parameters = constructor.getParameters();

            if(parameters.size() == args.size()){
                for(int index = 0; index<parameters.size() ; index++){
                    if(!(parameters.get(index).getType().isConformedBy(args.get(index).check()))){
                        throw new SemanticErrorException(className,lineNumber,"Error Semantico en la linea: "+
                                lineNumber+" acceso a Constructor invalido debido a que no hay conformidad de tipos: "+
                                className);
                    }
                }
                return new TidClass(className,lineNumber);

        }
            else {
                throw new SemanticErrorException(className,lineNumber,"Error Semantico en la linea: "+
                        lineNumber+" acceso a Constructor invalido debido a la cantidad de argumentos "+
                        className);
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return className;
    }
}

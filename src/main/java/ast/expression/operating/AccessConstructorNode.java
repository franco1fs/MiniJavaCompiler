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

    @Override
    public void generate() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        symbolTable.genInstruction("RMEM 1");
        symbolTable.genInstruction("PUSH "+symbolTable.getClass(className).getMyAtributes().values().size()+1);
        symbolTable.genInstruction("PUSH simple_malloc");
        symbolTable.genInstruction("CALL");
        symbolTable.genInstruction("DUP");

        if(symbolTable.getClass(className).atLeastExistADynamicMethod()){
            symbolTable.genInstruction("PUSH VT_"+className);
            symbolTable.genInstruction("STOREREF 0");
            symbolTable.genInstruction("DUP");
        }

        generateParam();

        symbolTable.genInstruction("PUSH "+className);
        symbolTable.genInstruction("CALL");

    }

    private void generateParam(){
        int index = args.size()-1;
        while(index >= 0){
            args.get(index).generate();
            SymbolTable.getInstance().genInstruction("SWAP");
            index--;
        }
    }
}

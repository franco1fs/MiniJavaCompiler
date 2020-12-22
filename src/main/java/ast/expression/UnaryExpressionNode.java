package ast.expression;

import ast.expression.operating.OperantNode;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;
import symbolTable.Type;

public class UnaryExpressionNode extends ExpressionNode {
    private OperantNode operantNode;
    private String unaryOperator=null;

    public UnaryExpressionNode(OperantNode operantNode,int lineNumber){
        this.operantNode = operantNode;
        this.lineNumber = lineNumber;
    }

    public void setUnaryOperator(String unaryOperator){
        this.unaryOperator = unaryOperator;
    }

    public MethodType check() throws SemanticErrorException {
        MethodType expressionType = operantNode.check();


        if(unaryOperator!=null && (unaryOperator.equals("+") || unaryOperator.equals("-"))){
            if(!expressionType.getTypeName().equals("int")){
                throw new SemanticErrorException(unaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+unaryOperator+ " no es aplicable a una subexpresion que no sea de tipo int");
            }
        }
        else if(unaryOperator!=null && unaryOperator.equals("!")){
            if(!expressionType.getTypeName().equals("boolean")){
                throw new SemanticErrorException(unaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+unaryOperator+ " no es aplicable a una subexpresion que no sea de tipo boolean");

            }
        }
        return expressionType;
    }

    @Override
    public void generate() {
        operantNode.generate();

        if(unaryOperator!=null){
            if(unaryOperator.equals("!")){
                SymbolTable.getInstance().genInstruction("NOT");
            }
            else if(unaryOperator.equals("-")){
                SymbolTable.getInstance().genInstruction("NEG");
            }
            else{
                //Que va para suma unaria?
            }
        }
    }
}

package ast.expression;

import symbolTable.Method;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.Tboolean;

public class BinaryExpressionNode extends ExpressionNode {

    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    private String binaryOperator;

    public BinaryExpressionNode(ExpressionNode leftSide, ExpressionNode rightSide, String binaryOperator, int ln) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.binaryOperator = binaryOperator;
        this.lineNumber = ln;
    }

    public MethodType check() throws SemanticErrorException {
        MethodType leftType = leftSide.check();
        MethodType rightType = rightSide.check();

        if( binaryOperator.equals("+") || binaryOperator.equals("-") || binaryOperator.equals("*") || binaryOperator.equals("/")
                || binaryOperator.equals("%")){
            if(leftType.getTypeName().equals("int") && rightType.getTypeName().equals("int")){
                return leftType;
            }
            else{
                throw new SemanticErrorException(binaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+binaryOperator+ " no es aplicable a dos subexpresiones que no sean de tipo int");

            }
        }
        else if( binaryOperator.equals("&&") || binaryOperator.equals("||")){
            if(leftType.getTypeName().equals("boolean") && rightType.getTypeName().equals("boolean")){
                return leftType;
            }
            else{
                throw new SemanticErrorException(binaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+binaryOperator+ " no es aplicable a dos subexpresiones que no sean de tipo boolean");

            }
        }
        else if( binaryOperator.equals("==") || binaryOperator.equals("!=")){
            if( leftType.isConformedBy(rightType) || rightType.isConformedBy(leftType)){
                return new Tboolean("boolean",lineNumber);
            }
            else{
                throw new SemanticErrorException(binaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+binaryOperator+ " no es aplicable a dos subexpresiones que no conformen entre si");
            }
        }
        else{
            if(leftType.getTypeName().equals("int") && rightType.getTypeName().equals("int")){
                return new Tboolean("boolean",lineNumber);
            }
            else{
                throw new SemanticErrorException(binaryOperator,lineNumber,"Error Semantico en la linea: "+lineNumber+
                        " El operador "+binaryOperator+ " no es aplicable a dos subexpresiones que no sean de tipo int");

            }
        }
    }

    @Override
    public void generate() {

    }
}

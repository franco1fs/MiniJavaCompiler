package ast.sentence;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

public class ReturnNode extends SentenceNode {
    private ExpressionNode returnExpression;
    private Class myClass;
    private Unit unitWhereBelong;

    public ReturnNode(ExpressionNode returnExpression, Class myClass, Unit myUnit,int ln) {
        this.returnExpression = returnExpression;
        this.myClass = myClass;
        this.unitWhereBelong = myUnit;
        this.lineNumber = ln;

    }


    @Override
    public void check() throws SemanticErrorException {
        if(unitWhereBelong instanceof Constructor){
            throw new SemanticErrorException("return",lineNumber,"Error " +
                    "Semantico en la linea: "+lineNumber+
                    " no puede existir una sentencia return en un consturctor");
        }
        else{
            Method method = (Method) unitWhereBelong;
            MethodType type = method.getReturnType();
            if(returnExpression==null){
                if(!(type.getTypeName().equals("void"))){
                    throw new SemanticErrorException("return",lineNumber,"Error " +
                            "Semantico en la linea: "+lineNumber+
                            " la sentencia return es vacia pero el tipo de retorno del metodo es: "+type.getTypeName());
                }
            }
            else{
                MethodType expressionType = returnExpression.check();
                if(!(type.isConformedBy(expressionType))){
                    throw new SemanticErrorException("return",lineNumber,"Error " +
                            "Semantico en la linea: "+lineNumber+
                            " la sentencia return no es correcta debido a que el tipo de retorno del metodo" +
                            "no es conformado por la expresion de retorno : "+expressionType.getTypeName());
                }
            }
        }
    }

    public void setReturnExpression(ExpressionNode returnExpression) {
        this.returnExpression = returnExpression;
    }

    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }

    public void setMyMethod(Unit unit) {
        this.unitWhereBelong= unit;
    }

    public ExpressionNode getReturnExpression() {
        return returnExpression;
    }

    public Class getMyClass() {
        return myClass;
    }

    public Unit getMyMethod() {
        return unitWhereBelong;
    }



}

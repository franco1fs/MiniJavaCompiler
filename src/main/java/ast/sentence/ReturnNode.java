package ast.sentence;

import ast.expression.ExpressionNode;
import symbolTable.Class;
import symbolTable.SemanticErrorException;
import symbolTable.Unit;

public class ReturnNode extends SentenceNode {
    private ExpressionNode returnExpression;
    private Class myClass;
    private Unit unitWhereBelong;

    public ReturnNode(ExpressionNode returnExpression, Class myClass, Unit myUnit) {
        this.returnExpression = returnExpression;
        this.myClass = myClass;
        this.unitWhereBelong = myUnit;
    }


    @Override
    public void check() throws SemanticErrorException {
        //if(InstanceOf(Constructor)) -- > Error (Constructor no puede tener return)
        //else{
        // Casteo a Method y comienzo a chequear
        // }
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

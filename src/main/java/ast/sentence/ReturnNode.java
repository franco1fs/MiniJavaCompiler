package ast.sentence;

import ast.expression.ExpressionNode;
import ast.sentence.SentenceNode;
import symbolTable.Class;

public class ReturnNode extends SentenceNode {
    private ExpressionNode returnExpression;
    private Class myClass;
    private String myMethod;

    public ReturnNode(ExpressionNode returnExpression, Class myClass, String myMethod) {
        this.returnExpression = returnExpression;
        this.myClass = myClass;
        this.myMethod = myMethod;
    }


    @Override
    public void check() {

    }

    public void setReturnExpression(ExpressionNode returnExpression) {
        this.returnExpression = returnExpression;
    }

    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }

    public void setMyMethod(String myMethod) {
        this.myMethod = myMethod;
    }

    public ExpressionNode getReturnExpression() {
        return returnExpression;
    }

    public Class getMyClass() {
        return myClass;
    }

    public String getMyMethod() {
        return myMethod;
    }



}

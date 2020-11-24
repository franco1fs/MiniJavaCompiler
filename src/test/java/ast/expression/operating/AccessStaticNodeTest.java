package ast.expression.operating;

import ast.expression.ExpressionNode;
import org.junit.jupiter.api.Test;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccessStaticNodeTest {

    @Test
    public void checkTestShouldThrowExceptionNoClassExistence(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        AccessStaticNode accessStaticNode = new AccessStaticNode("C5",accessMethodNode,0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessStaticNode.check();

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTestShouldThrowExceptionNoMethodExistence(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m2",
                SymbolTable.getInstance().getClass("C1"),0);
        AccessStaticNode accessStaticNode = new AccessStaticNode("C1",accessMethodNode,0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessStaticNode.check();

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTestShouldThrowExceptionNoStaticMethod(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        AccessStaticNode accessStaticNode = new AccessStaticNode("C1",accessMethodNode,0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessStaticNode.check();

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTestShouldBeCorrect(){
        loadSymbolTable();
        Method m1 = createMethodStatic(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        AccessStaticNode accessStaticNode = new AccessStaticNode("C1",accessMethodNode,0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessStaticNode.check();
            assertEquals("void",typeMethod.getTypeName());
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    private Method createMethodStatic(Class clase){
        return new Method("m1",clase,new Tvoid(0),"static",2);
    }


    private Method createMethod(Class clase){
        return new Method("m1",clase,new Tvoid(0),"dynamic",2);
    }

    private void insertMethodParameter(Method method){
        Parameter parameter = new Parameter("p1",1,new TString("String",1));
        try {
            method.insertParameter(parameter);
        }
        catch (SemanticErrorException e){

        }
    }
    private void loadSymbolTable(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);
        c3.setAncestor("C4");
        Class c4 = new Class("C4",1);
        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            symbolTable.insertClass(c1);
            symbolTable.insertClass(c2);
            symbolTable.insertClass(c3);
            symbolTable.insertClass(c4);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

}
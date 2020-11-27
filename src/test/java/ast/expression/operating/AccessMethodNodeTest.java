package ast.expression.operating;

import ast.expression.ExpressionNode;
import org.junit.jupiter.api.Test;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccessMethodNodeTest {

    /**
    @Test
    public void checkTestShouldThrowException(){
        loadSymbolTable();
        LiteralNode p1 = new LiteralNode(new Tint("int",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        try {
            accessMethodNode.check();
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());

        }

    }

    @Test
    public void checkTestShouldThrowException2(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new Tint("int",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            accessMethodNode.check();
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void checkTestWithTypeConformed(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessMethodNode.check();
            assertEquals("void",typeMethod.getTypeName());
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void checkTestWithTypeConformed2(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        insertMethodParameter(m1);
        insertMethodParameter2(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        LiteralNode p2 = new LiteralNode(new Tnull("null",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        formalArgs.add(p2);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessMethodNode.check();
            assertEquals("void",typeMethod.getTypeName());
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    private void insertMethodParameter2(Method method){
        Parameter parameter = new Parameter("p2",1,new TidClass("C2",1));
        try {
            method.insertParameter(parameter);
        }
        catch (SemanticErrorException e){

        }
    }

    @Test
    public void checkTestShouldThrowExceptionOfSize(){
        loadSymbolTable();
        Method m1 = createMethod(SymbolTable.getInstance().getClass("C1"));
        //insertMethodParameter(m1);

        LiteralNode p1 = new LiteralNode(new TString("String",0),"1");
        ArrayList<ExpressionNode> formalArgs = new ArrayList<ExpressionNode>();
        formalArgs.add(p1);
        AccessMethodNode accessMethodNode = new AccessMethodNode(formalArgs,"m1",
                SymbolTable.getInstance().getClass("C1"),0);
        try {
            SymbolTable.getInstance().getClass("C1").insertMethod(m1);
            MethodType typeMethod = accessMethodNode.check();
            assertEquals("void",typeMethod.getTypeName());
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
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

    **/
}
package ast.sentence;

import org.junit.jupiter.api.Test;
import symbolTable.*;
import symbolTable.Class;
import symbolTable.Type;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DecVarsNodeTest {


    private Method createMethod(Class clase){
        return new Method("m1",clase,new Tvoid(0),"dynamic",2);
    }

    @Test
    public void checkWhoShouldInsertLocalVars() {
        loadSymbolTable();

        BlockNode blockNode = new BlockNode(null,
                createMethod(SymbolTable.getInstance().getClasses().get("C3")));
        ArrayList<String> vars = new ArrayList<String>();
        vars.add("s1");
        vars.add("s2");
        Type type = new TidClass("C3", 1);

        DecVarsNode decVarsNode = new DecVarsNode(vars,type,blockNode);
        try{
        decVarsNode.check();

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
        finally {
            assertEquals(2,blockNode.getLocalVars().size());
        }
    }

    @Test
    public void checkWhoShouldThrowException() {
        loadSymbolTableWithoutC4();
        BlockNode blockNode = new BlockNode(null,
                createMethod(SymbolTable.getInstance().getClasses().get("C3")));
        ArrayList<String> vars = new ArrayList<String>();
        vars.add("s1");
        vars.add("s2");
        Type type = new TidClass("C4", 1);

        DecVarsNode decVarsNode = new DecVarsNode(vars,type,blockNode);
        try{
            decVarsNode.check();
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
        finally {
            assertEquals(0,blockNode.getLocalVars().size());
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


    private void loadSymbolTableWithoutC4(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);
        c3.setAncestor("C4");
        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            symbolTable.insertClass(c1);
            symbolTable.insertClass(c2);
            symbolTable.insertClass(c3);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }
}
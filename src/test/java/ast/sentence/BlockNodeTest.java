package ast.sentence;

import org.junit.jupiter.api.Test;
import symbolTable.*;
import symbolTable.Class;

import static org.junit.jupiter.api.Assertions.*;

class BlockNodeTest {

    private Method createMethod(Class clase){
        return new Method("m1",clase,new Tvoid(0),"dynamic",2);
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



    @Test
    public void insertLocalVarCorrectly(){
        loadSymbolTable();

        BlockNode blockNode = new BlockNode(null,
                createMethod(SymbolTable.getInstance().getClasses().get("C3")));
        try {
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v1"));
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
        finally {
            assertEquals(1,blockNode.getLocalVars().size());
        }
        }

    @Test
    public void insertLocalVarWithRepeatedlyNameShouldThrowException(){
        loadSymbolTable();

        BlockNode blockNode = new BlockNode(null,
                createMethod(SymbolTable.getInstance().getClasses().get("C3")));
        try {
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v1"));
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v1"));
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("v1",e.getLexeme());
        }
        finally {
            assertEquals(1,blockNode.getLocalVars().size());
        }
    }

    private Method createMethodWithParameter(Class clase){
        Method method = createMethod(clase);
        Parameter parameter = new Parameter("v3",1,new TString("String",1));
        try {
            method.insertParameter(parameter);
        }
        catch (SemanticErrorException e){

        }
        finally {
            return method;
        }
    }
    @Test
    public void insertLocalVarWithVarNameEqualsParamNameShouldThrowException(){
        loadSymbolTable();

        BlockNode blockNode = new BlockNode(null,
                createMethodWithParameter(SymbolTable.getInstance().getClasses().get("C3")));
        try {
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v1"));
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v2"));
            blockNode.insertLocalVar(new LocalVar(new Tchar("char", 0), "v3"));
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("v3",e.getLexeme());
        }
        finally {
            assertEquals(3,blockNode.getLocalVars().size());
        }
    }
}
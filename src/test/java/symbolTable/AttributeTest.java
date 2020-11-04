package symbolTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeTest {

    private void loadSymbolTableWithAttr(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);
        // c3.setAncestor("C4");
        //Class c4 = new Class("C4",1);
        //c4.setAncestor("C3");
        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            c1.insertAttribute(new Attribute("a1",1,new TString("String",1),"",""));
            c1.insertAttribute(new Attribute("a2",1,new TString("String",1),"",""));
            c1.insertAttribute(new Attribute("a3",1,new TidClass("No",1),"",""));
            c2.insertAttribute(new Attribute("a4",1,new TString("String",1),"",""));
            c2.insertAttribute(new Attribute("a5",1,new TString("String",1),"",""));
            c3.insertAttribute(new Attribute("a6",1,new TString("String",1),"",""));
            c3.insertAttribute(new Attribute("a1",1,new TString("String",1),"",""));
            symbolTable.insertClass(c1);
            symbolTable.insertClass(c2);
            symbolTable.insertClass(c3);
            // symbolTable.insertClass(c4);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void checkAttributeTypeExistence(){
        loadSymbolTableWithAttr();
        try {
            assertEquals(SymbolTable.getInstance().getClasses().get("C1").getMyAtributes().size(),3);
            SymbolTable.getInstance().getClasses().get("C1").checkAttributesDeclaration();
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"a3");
        }
    }
}
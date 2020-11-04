package symbolTable;

import org.junit.jupiter.api.Test;
import symbolTable.Class;
import symbolTable.Method;
import symbolTable.SemanticErrorException;
import symbolTable.Tvoid;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {


    @Test
    public void insertMethodTest(){
        Class clase = createClassObj();
        Method method = createMethod(clase);
        try {
            clase.insertMethod(method);
            checkMethodInsertion(clase);
        }
        catch (SemanticErrorException e){

        }
    }

    private void checkMethodInsertion(Class clase){
        HashMap<String,Method> methods = clase.getMyMethods();
        assertEquals(true,methods.containsKey("m1"));
    }

    private Class createClassObj(){
        return new Class("Prueba",1);
    }

    private Method createMethod(Class clase){
        return new Method("m1",clase,new Tvoid(1),"dynamic",2);
    }

    private void loadSymbolTable(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);
        c3.setAncestor("C4");
        Class c4 = new Class("C4",1);
        c4.setAncestor("C3");
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
    public void checkClassDeclarationMustRunAlone(){
        loadSymbolTable();
        SymbolTable symbolTable = SymbolTable.getInstance();
        try {
            symbolTable.checkClassesDeclarationAndConsolidationTable();
            System.out.println("Without issues");
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("C1",e.getLexeme());
        }
    }

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
            c1.insertAttribute(new Attribute("a3",1,new TString("String",1),"",""));
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

    // For Run this TestMethod I should to change the visibility of checkAttributesDeclaration() inside
    // Class. By default is private
    @Test
    public void checkAttributesDeclaration(){
        loadSymbolTableWithAttr();
        try {
            assertEquals(SymbolTable.getInstance().getClasses().get("C1").getMyAtributes().size(),3);
            SymbolTable.getInstance().getClasses().get("C1").checkAttributesDeclaration();
            assertEquals(SymbolTable.getInstance().getClasses().get("C1").getMyAtributes().size(),7);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"a1");
        }

    }

    private void loadSymbolTableWithMethods(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);

        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            c1.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
            c1.insertMethod(new Method("m2",c1,new Tvoid(1),"dynamic",2));
            c2.insertMethod(new Method("m3",c1,new Tvoid(1),"dynamic",2));
            c3.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));

            symbolTable.insertClass(c1);
            symbolTable.insertClass(c2);
            symbolTable.insertClass(c3);
            // symbolTable.insertClass(c4);

        }
        catch (SemanticErrorException e){
            System.out.println("Load Symbol table "+ e.getMessage());
        }
    }

    @Test
    public void checkInheritanceMethodsDeclaration() {
        loadSymbolTableWithMethods();
        Collection<Class> clases = SymbolTable.getInstance().getClasses().values();
        try{
            for(Class c: clases){
                c.checkMethodDeclaration();
            }
            assertEquals(3,SymbolTable.getInstance().getClasses().get("C1").getMyMethods().size());
            assertEquals(2,SymbolTable.getInstance().getClasses().get("C2").getMyMethods().size());
            assertEquals(1,SymbolTable.getInstance().getClasses().get("C3").getMyMethods().size());

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadSymbolTableForThrowExceptionWithMethods(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);

        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            c1.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
            c1.insertMethod(new Method("m2",c1,new Tvoid(1),"dynamic",2));
            c2.insertMethod(new Method("m3",c1,new Tvoid(1),"dynamic",2));
            c3.insertMethod(new Method("m1",c1,new Tint("int",1),"dynamic",2));
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
    public void checkInheritanceMethodsThrowingExceptionDeclaration() {
        loadSymbolTableForThrowExceptionWithMethods();
        Collection<Class> clases = SymbolTable.getInstance().getClasses().values();
        try{
            for(Class c: clases){
                c.checkMethodDeclaration();
            }
            assertEquals(3,SymbolTable.getInstance().getClasses().get("C1").getMyMethods().size());
            assertEquals(2,SymbolTable.getInstance().getClasses().get("C2").getMyMethods().size());
            assertEquals(1,SymbolTable.getInstance().getClasses().get("C3").getMyMethods().size());

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("m1",e.getLexeme());
        }
    }

    private void loadSymbolTableForThrowExceptionOfTypeWithMethods(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);

        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            c1.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
            c1.insertMethod(new Method("m2",c1,new Tvoid(1),"dynamic",2));
            c2.insertMethod(new Method("m3",c1,new TidClass("C4",1),"dynamic",2));
            c3.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
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
    public void checkInheritanceMethodsThrowingExceptionTypeDeclaration() {
        loadSymbolTableForThrowExceptionOfTypeWithMethods();
        Collection<Class> clases = SymbolTable.getInstance().getClasses().values();
        try{
            for(Class c: clases){
                c.checkMethodDeclaration();
            }
            assertEquals(3,SymbolTable.getInstance().getClasses().get("C1").getMyMethods().size());
            assertEquals(2,SymbolTable.getInstance().getClasses().get("C2").getMyMethods().size());
            assertEquals(1,SymbolTable.getInstance().getClasses().get("C3").getMyMethods().size());

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("m3",e.getLexeme());
        }
    }

    private void loadSymbolTableForParameters(){
        Class c1 = new Class("C1",1);
        c1.setAncestor("C2");
        Class c2 = new Class("C2",1);
        c2.setAncestor("C3");
        Class c3 = new Class("C3",1);

        SymbolTable symbolTable ;
        symbolTable = SymbolTable.getInstance();
        try {
            c1.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
            c1.insertMethod(new Method("m2",c1,new Tvoid(1),"dynamic",2));
            c2.insertMethod(new Method("m3",c1,new TidClass("C4",1),"dynamic",2));
            c3.insertMethod(new Method("m1",c1,new Tvoid(1),"dynamic",2));
            Method m4 = new Method("m4",c1,new TidClass("C3",1),"dynamic",2);
            m4.insertParameter(new Parameter("p1",1,new TidClass("No",1)));
            c3.insertMethod(m4);
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
    public void checkMethodParametersAndThrowException(){
        loadSymbolTableForParameters();
        Collection<Class> clases = SymbolTable.getInstance().getClasses().values();
        try{
            for(Class c: clases){
                c.checkMethodDeclaration();
            }

        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals("p1",e.getLexeme());
        }
    }

    @Test
    public void insertMethodAndThrowExceptionTest(){
        Class clase = createClassObj();
        Method method = createMethod(clase);
        try {
            clase.insertMethod(method);
            clase.insertMethod(method);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"m1");
        }
    }

    @Test
    public void insertConstructorTest(){
        Class clase = createClassObj();
        try {
            Constructor constructor = new Constructor("Prueba", 1, clase);
            clase.insertConstructor(constructor);
            assertEquals("Prueba",clase.getConstructor().getName());
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void insertConstructorAndThrowExceptionTest(){
        Class clase = createClassObj();
        try {
            Constructor constructor = new Constructor("Prueba2", 1, clase);
            clase.insertConstructor(constructor);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"Prueba2");
        }
    }

    @Test
    public void insertAttributesTest(){
        Class clase = createClassObj();
        try {
            Attribute attribute = new Attribute("a1",1,new Tint("int",1),"private","");
            Attribute attribute2 = new Attribute("a2",1,new Tint("int",1),"private","");
            Attribute attribute3 = new Attribute("a3",1,new Tint("int",1),"private","");
            clase.insertAttribute(attribute);
            clase.insertAttribute(attribute2);
            clase.insertAttribute(attribute3);
            assertEquals(true,clase.getMyAtributes().containsKey("a1"));
            assertEquals(true,clase.getMyAtributes().containsKey("a2"));
            assertEquals(true,clase.getMyAtributes().containsKey("a3"));
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void insertAttributesAndThrowExceptionTest(){
        Class clase = createClassObj();
        try {
            Attribute attribute = new Attribute("a1",1,new Tint("int",1),"private","");
            Attribute attribute2 = new Attribute("a2",1,new Tint("int",1),"private","");
            Attribute attribute3 = new Attribute("a2",1,new Tint("int",1),"private","");
            clase.insertAttribute(attribute);
            clase.insertAttribute(attribute2);
            clase.insertAttribute(attribute3);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"a2");
        }
    }


}
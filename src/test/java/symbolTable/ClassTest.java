package symbolTable;

import org.junit.jupiter.api.Test;
import symbolTable.Class;
import symbolTable.Method;
import symbolTable.SemanticErrorException;
import symbolTable.Tvoid;

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
        return new Method("m1",clase,new Tvoid(),"dynamic",2);
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
            Attribute attribute = new Attribute("a1",1,new Tint("int"),"private");
            Attribute attribute2 = new Attribute("a2",1,new Tint("int"),"private");
            Attribute attribute3 = new Attribute("a3",1,new Tint("int"),"private");
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
            Attribute attribute = new Attribute("a1",1,new Tint("int"),"private");
            Attribute attribute2 = new Attribute("a2",1,new Tint("int"),"private");
            Attribute attribute3 = new Attribute("a2",1,new Tint("int"),"private");
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
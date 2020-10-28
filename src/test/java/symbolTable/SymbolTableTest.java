package symbolTable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    @Test
    public void checkCreationOfSymbolTableTest(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        HashMap<String,Class> clases = symbolTable.getClasses();

        assertAll("clases",
                () -> assertEquals(true,clases.containsKey("Object")),
                ()-> assertEquals(true,clases.containsKey("System")));
    }

    @Test
    public void cheackMethodsOfSymbolClassTest(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        HashMap<String,Class> clases = symbolTable.getClasses();

        Class clase = clases.get("System");

        HashMap<String,Method> methodsOfSystem = clase.getMyMethods();

        assertAll("clases",
                () -> assertEquals(true,methodsOfSystem.containsKey("read")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printB")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printC")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printI")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printS")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("println")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printBln")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printCln")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printIln")),
                ()-> assertEquals(true,methodsOfSystem.containsKey("printSln"))
        );
    }

    private Class createClassObj(){
        return new Class("Prueba",1);
    }

    @Test
    public void insertClassOnSymbolTableAndCheckCurrentModuleTest(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        Class clase = createClassObj();
        symbolTable.setCurrentModule(clase);
        try {
            symbolTable.insertClass(clase);
            assertEquals(symbolTable.getCurrentModule().getName(),"Prueba");
        }
        catch (SemanticErrorException e){

        }
        }

    @Test
    public void insertClassOnSymbolTableAndCheckClassesTest(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        Class clase = createClassObj();
        symbolTable.setCurrentModule(clase);
        try {
            symbolTable.insertClass(clase);
            HashMap<String,Class> clases = symbolTable.getClasses();
            assertEquals(true, clases.containsKey("Prueba"));
        }
        catch (SemanticErrorException e){

        }
    }

    @Test
    public void insertClassOnSymbolTableAndThrowExceptionTest(){
        SymbolTable symbolTable = SymbolTable.getInstance();
        Class clase = createClassObj();
        symbolTable.setCurrentModule(clase);
        try {
            symbolTable.insertClass(clase);
            symbolTable.insertClass(new Class("Prueba",2345));
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"Prueba");
        }
    }
}
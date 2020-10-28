package symbolTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    private Method createMethod(Class clase){
        return new Method("m1",clase,new Tvoid(),"dynamic",2);
    }

    @Test
    public void insertMethodParameter(){
        Method method = createMethod(null);
        Parameter parameter = new Parameter("p1",1,new TString("String"));
        try {
            method.insertParameter(parameter);
            assertEquals("p1",method.getParameters().get(0).getName());
        }
        catch (SemanticErrorException e){

        }
    }

    @Test
    public void insertMethodParameterAndThrowException(){
        Method method = createMethod(null);
        Parameter parameter = new Parameter("p1",1,new TString("String"));
        try {
            method.insertParameter(parameter);
            method.insertParameter(parameter);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"p1");
        }
    }
}
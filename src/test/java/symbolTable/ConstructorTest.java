package symbolTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorTest {

    private Constructor createConstructor(){
        return new Constructor("C",1,null);
    }
    @Test
    public void insertConstructorParameter(){
        Constructor constructor = createConstructor();
        Parameter parameter = new Parameter("p1",1,new TString("String"));
        try {
            constructor.insertParameter(parameter);
            assertEquals("p1",constructor.getParameters().get(0).getName());
        }
        catch (SemanticErrorException e){

        }
    }

    @Test
    public void insertConstructorParameterAndThrowException(){
        Constructor constructor = createConstructor();
        Parameter parameter = new Parameter("p1",1,new TString("String"));
        try {
            constructor.insertParameter(parameter);
            constructor.insertParameter(parameter);
        }
        catch (SemanticErrorException e){
            System.out.println(e.getMessage());
            assertEquals(e.getLexeme(),"p1");
        }
    }

}
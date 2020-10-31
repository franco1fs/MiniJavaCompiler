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

    @Test
    public void checkEqualsForOverWriteMethod(){
        Method m1 = createMethod(null);
        Parameter parameter = new Parameter("p1",1,new Tint("int"));
        Parameter parameter2 = new Parameter("p2",1,new Tint("int"));
        Parameter parameter3 = new Parameter("p3",1,new Tint("int"));
        Parameter parameter4 = new Parameter("p5",1,new TString("String"));
        Method m2 = createMethod(null);
        try {
            m1.insertParameter(parameter);
            m1.insertParameter(parameter2);
            m1.insertParameter(parameter3);
            m2.insertParameter(parameter);
            m2.insertParameter(parameter2);
            m2.insertParameter(parameter4);

            assertEquals(false,m1.equalsForOverWrite(m2));
        }
        catch (SemanticErrorException e){

        }
    }
}
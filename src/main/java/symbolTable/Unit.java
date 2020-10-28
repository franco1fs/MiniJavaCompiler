package symbolTable;

import java.util.ArrayList;

public class Unit extends Entity {
    protected Module myModule;
    protected ArrayList<Parameter> parameters = new ArrayList<Parameter>();

    //Agregar el metodo que permita cargar parametros a la lista de parametros
    // public void addParameterAndCheckRepeatName(...) throw SemanticExc...
    public void insertParameter(Parameter parameter) throws SemanticErrorException{
        if(parameterNameAlreadyExist(parameter.getName())){
            throw new SemanticErrorException(parameter.getName(),parameter.getLineNumber(),"Hay un nombre de " +
                    "parametro repetido en la unidad "+ name );
        }
        parameters.add(parameter);
    }

    private boolean parameterNameAlreadyExist(String name){
        for (Parameter p : parameters){
            if(p.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Parameter> getParameters(){
        return parameters;
    }
}

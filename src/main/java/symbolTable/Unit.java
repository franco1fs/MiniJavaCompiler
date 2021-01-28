package symbolTable;

import ast.sentence.BlockNode;

import java.util.ArrayList;

public class Unit extends Entity {
    protected Module myModule;
    protected ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    private BlockNode myBlock;
    private int offsetParam = 0;


    public void setMyBlock(BlockNode myBlock) {
        this.myBlock = myBlock;
    }

    public Module getMyModule() {
        return myModule;
    }

    public BlockNode getMyBlock() {
        return myBlock;
    }

    //Agregar el metodo que permita cargar parametros a la lista de parametros
    // public void addParameterAndCheckRepeatName(...) throw SemanticExc...
    public void insertParameter(Parameter parameter) throws SemanticErrorException{
        if(parameterNameAlreadyExist(parameter.getName())){
            throw new SemanticErrorException(parameter.getName(),parameter.getLineNumber(),"Hay un nombre de " +
                    "parametro repetido en la unidad "+ name );
        }
        if(this instanceof Constructor || (this instanceof Method && ((Method) this).getMethodForm().equals("dynamic"))){
            parameter.setOffset(offsetParam+4);
        }
        else{
            parameter.setOffset(offsetParam+3);
        }
        offsetParam++;

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

    public void generate(){

    }

}

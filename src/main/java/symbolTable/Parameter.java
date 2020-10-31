package symbolTable;

public class Parameter extends Entity{
    protected Type type;

    public Parameter(String name, int lineNumber, Type type){
        this.name = name;
        this.lineNumber = lineNumber;
        this.type = type;
    }

    public Type getType(){
        return type;
    }

    public void checkTypeExistence() throws SemanticErrorException {
        if(type.mustCheckTypeExistence()){
            if(!SymbolTable.getInstance().getClasses().containsKey(type.getTypeName())){
                throw new SemanticErrorException(name,lineNumber,"Error Semantico en la linea: "+lineNumber+" el" +
                        "Parametro "+name+" esta declarado con un tipo de clase no existente");
            }
        }
    }
}

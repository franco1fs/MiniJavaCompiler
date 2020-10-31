package symbolTable;

public class Attribute extends Entity{

    protected Type type;
    protected String visibility;

    public Attribute(String name,int lineNumber,Type type,String visibility){
        this.name = name;
        this.lineNumber = lineNumber;
        this.type = type;
        this.visibility = visibility;
    }

    public Type getType(){
        return type;
    }
    public String getVisibility(){
        return visibility;
    }

    public void checkTypeExistence() throws SemanticErrorException {
        if(type.mustCheckTypeExistence()){
            if(!SymbolTable.getInstance().getClasses().containsKey(type.getTypeName())){
                throw new SemanticErrorException(name,lineNumber,"Error Semantico en la linea: "+lineNumber+" el" +
                        "Atributo "+name+" esta declarado con un tipo de clase no existente");
            }
        }
    }
}

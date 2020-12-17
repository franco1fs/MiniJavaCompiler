package symbolTable;

public class Parameter extends Entity{
    protected Type type;
    private int offset;

    public Parameter(String name, int lineNumber, Type type){
        this.name = name;
        this.lineNumber = lineNumber;
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Type getType(){
        return type;
    }

    public void checkTypeExistence(Unit in) throws SemanticErrorException {
        if(type.mustCheckTypeExistence()){
            if(!SymbolTable.getInstance().getClasses().containsKey(type.getTypeName())){
                throw new SemanticErrorException(in.getName(),in.getLineNumber(),
                        "Error Semantico en la linea: "+in.getLineNumber()+" el" +
                        "Parametro "+name+" en el encabezado de la unidad "+
                        in.getName()+" esta declarado con un tipo de clase no existente");
            }
        }
    }
}

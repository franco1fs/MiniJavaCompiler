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
}

package symbolTable;

public abstract class Type extends MethodType{
    protected String name;

    public String getTypeName(){
        return name;
    }
}

package symbolTable;

public abstract class Type extends MethodType{
    protected String name;
    protected int lineNumber;

    public String getTypeName(){
        return name;
    }
    public int getLineNumber(){
        return lineNumber;
    }


}

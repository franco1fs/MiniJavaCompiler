package symbolTable;

public abstract class Entity {
    protected String name;
    protected int lineNumber;

    public String getName(){
        return name;
    }
    public int getLineNumber(){
        return lineNumber;
    }
}

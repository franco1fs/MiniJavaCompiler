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
}

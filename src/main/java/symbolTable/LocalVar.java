package symbolTable;

public class LocalVar extends Entity{
    private Type type;
    private String name;
    private String value;
    private int offset;

    public LocalVar(Type type, String name,int ln) {
        this.type = type;
        this.name = name;
        this.lineNumber = ln;
    }

    public LocalVar(Type type, String name,int ln, int offset) {
        this.type = type;
        this.name = name;
        this.lineNumber = ln;
        this.offset = offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public  int getOffset(){
        return offset;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

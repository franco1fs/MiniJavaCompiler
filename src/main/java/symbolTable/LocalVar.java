package symbolTable;

public class LocalVar extends Entity{
    private Type type;
    private String name;
    private String value;

    public LocalVar(Type type, String name) {
        this.type = type;
        this.name = name;
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

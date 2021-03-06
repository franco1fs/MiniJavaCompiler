package symbolTable;

public class Tint extends Type{

    public Tint(String name,int ln){
        this.name = name;
        this.lineNumber=ln;
    }

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }

    @Override
    public boolean isConformedBy(MethodType type) {
        return type.getTypeName().equals(name);
    }
}

package symbolTable;

public class TString extends Type{

    public TString(String name,int ln){
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

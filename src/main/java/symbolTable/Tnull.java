package symbolTable;

public class Tnull extends Type{

    public Tnull(String name,int ln){
        this.name = name;
        this.lineNumber=ln;
    }
    @Override
    public boolean isConformedBy(MethodType type) {
        return false;
    }

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

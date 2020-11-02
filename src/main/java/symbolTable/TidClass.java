package symbolTable;

public class TidClass extends Type{

    public TidClass(String name,int ln){
        this.name = name;
        this.lineNumber=ln;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return true;
    }
}

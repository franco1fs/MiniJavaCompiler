package symbolTable;

public class Tboolean extends Type{

    public Tboolean(String name,int ln){
        this.name = name;
        this.lineNumber=ln;
    }

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

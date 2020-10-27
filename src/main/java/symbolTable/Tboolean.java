package symbolTable;

public class Tboolean extends Type{

    public Tboolean(String name){
        this.name = name;
    }

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

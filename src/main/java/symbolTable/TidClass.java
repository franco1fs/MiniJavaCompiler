package symbolTable;

public class TidClass extends Type{

    public TidClass(String name){
        this.name = name;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return true;
    }
}

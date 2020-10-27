package symbolTable;

public class Tint extends Type{

    public Tint(String name){
        this.name = name;
    }

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

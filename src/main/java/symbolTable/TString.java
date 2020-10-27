package symbolTable;

public class TString extends Type{

    public TString(String name){
        this.name = name;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

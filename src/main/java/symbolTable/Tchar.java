package symbolTable;

public class Tchar extends Type {

    public Tchar(String name){
        this.name = name;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

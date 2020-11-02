package symbolTable;

public class Tchar extends Type {

    public Tchar(String name,int ln){
        this.lineNumber=ln;
        this.name = name;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

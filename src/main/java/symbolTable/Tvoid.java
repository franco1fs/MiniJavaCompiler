package symbolTable;

public class Tvoid extends MethodType {

    protected int lineNumber;
    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }

    public Tvoid(int ln){
        this.lineNumber = ln;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    @Override
    public boolean isConformedBy(MethodType type) {
        return false;
    }

    public String getTypeName(){
        return "void";
    }
}

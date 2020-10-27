package symbolTable;

public class Tvoid extends MethodType {

    @Override
    public boolean mustCheckTypeExistence() {
        return false;
    }
}

package symbolTable;

public abstract class MethodType {

    public abstract boolean mustCheckTypeExistence();

    public abstract String getTypeName();

    public abstract int getLineNumber();

    public abstract boolean isConformedBy(MethodType type);

}


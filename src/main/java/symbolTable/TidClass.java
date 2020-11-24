package symbolTable;

public class TidClass extends Type{

    public TidClass(String name,int ln){
        this.name = name;
        this.lineNumber=ln;
    }
    @Override
    public boolean mustCheckTypeExistence() {
        return true;
    }

    @Override
    public boolean isConformedBy(MethodType type) {
        if( !(type instanceof TidClass) && !(type instanceof Tnull)){
            return false;
        }
        else{
            if(name.equals("Object") || type instanceof Tnull){
                return true;
            }
            else{
                Class clase = SymbolTable.getInstance().getClass(type.getTypeName());
                boolean answer = clase.getName().equals(name);
                while(!answer && !clase.getName().equals("Object")){
                    if(clase.getName().equals(name)){
                        answer=true;
                        break;
                    }
                    clase = SymbolTable.getInstance().getClass(clase.getAncestor());
                }
                return answer;
            }
        }
    }
}

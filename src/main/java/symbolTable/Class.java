package symbolTable;

import java.util.HashMap;
import java.util.Hashtable;

public class Class extends Module{

    private Unit currentUnit;
    private String ancestor;

    private HashMap<String,Method> myMethods;
    private HashMap<String,Attribute> myAtributes;

    private Constructor constructor= null;

    public Class(String name,int lineNumber){
        this.name = name;
        this.lineNumber = lineNumber;
        myMethods = new HashMap<String, Method>();
        myAtributes = new HashMap<String, Attribute>();
        this.symbolTable = SymbolTable.getInstance();
    }

    public Class(String name,int lineNumber,String inheritanceClass){
        this.name = name;
        this.lineNumber = lineNumber;
        myMethods = new HashMap<String, Method>();
        myAtributes = new HashMap<String, Attribute>();
        this.ancestor = inheritanceClass;
        this.symbolTable = SymbolTable.getInstance();
    }

    public void setAncestor(String ancestor){
        this.ancestor = ancestor;
    }

    public void setCurrentUnit(Unit unit){
        this.currentUnit = unit;
    }

    public Unit getCurrentUnit(){
        return currentUnit;
    }

    //Podria lanzar una Exception
    public void insertMethod(Method method) throws SemanticErrorException{
        if(myMethods.containsKey(method.getName())){
            throw new SemanticErrorException(method.getName(),method.getLineNumber(),"Error Semantico en la linea:"+
                    method.getLineNumber()+"La clase "+name+" tiene metodos" +
                    "con nombres repetidos");
        }
        myMethods.put(method.getName(),method);
        //currentUnit = method;
    }

    public void insertConstructor(Constructor c) throws SemanticErrorException{
        if(constructor != null){
            throw new SemanticErrorException(c.getName(),c.getLineNumber(),"Error Semantico en la linea: "+c.getLineNumber()+
                    "ya existe un constructor en la clase "+ name);
        }
        else if(constructor==null && !c.getName().equals(name)){
            throw new SemanticErrorException(c.getName(),c.getLineNumber(),"Error Semantico en la linea: "+
                    c.getLineNumber()+" el nombre del Constructor no coincide con el nombre de la clase" +
                    "a la cual pertenece");
        }

        constructor = c;
        //currentUnit = c;
    }

    public void insertAttribute(Attribute attribute) throws SemanticErrorException{
        if(myAtributes.containsKey(attribute.getName())){
            throw new SemanticErrorException(attribute.getName(),attribute.getLineNumber(),"Error Semantico en la linea:"+
                    attribute.getLineNumber() +" La clase "+name+
                    " tiene atributos con nombres repetidos");
        }

        myAtributes.put(attribute.getName(),attribute);
    }

    public HashMap<String,Method> getMyMethods(){
        return myMethods;
    }

    public Constructor getConstructor(){
        return constructor;
    }

    public HashMap<String,Attribute> getMyAtributes(){
        return myAtributes;
    }
}

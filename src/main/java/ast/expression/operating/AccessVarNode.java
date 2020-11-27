package ast.expression.operating;

import ast.sentence.BlockNode;
import symbolTable.*;
import symbolTable.Class;

public class AccessVarNode extends PrimaryNode {
    private String varName;
    private BlockNode blockWhereBeingUsed;


    public AccessVarNode(String varName,int lineNumber,BlockNode blockNode) {
        this.varName = varName;
        this.lineNumber = lineNumber;
        this.blockWhereBeingUsed = blockNode;
    }


    public MethodType check() throws SemanticErrorException {
        LocalVar localVar = blockWhereBeingUsed.getParameterOrLocalVarIfExist(varName);
        if(localVar!=null){
            return localVar.getType();
        }
        else{
            Class myClass= (Class) blockWhereBeingUsed.getUnitWhereIBelong().getMyModule();
            Attribute attribute = myClass.getAttributeIfExist(varName);
            if(attribute==null){
                throw new SemanticErrorException(varName,lineNumber,"Error Semantico en la linea: "+
                        lineNumber+" No existe un identificador con ese nombre: "+varName);
            }
            else{
                if( blockWhereBeingUsed.getUnitWhereIBelong() instanceof Method ){
                    Method method = (Method) blockWhereBeingUsed.getUnitWhereIBelong();
                    if( method.getMethodForm().equals("static")){
                        throw new SemanticErrorException(varName,lineNumber,"Error Semantico en la linea: "+
                                lineNumber+" No se puede acceder al atributo: "+varName+ " desde un metodo estatico");
                    }
                }
                if(isAccessibleAttributeFromHere(attribute,myClass)){
                    return attribute.getType();
                }
                else{
                    throw new SemanticErrorException(varName,lineNumber,"Error Semantico en la linea: "+
                            lineNumber+" El atributo de Instancia no es accesible : "+varName);
                }
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return varName;
    }

    private boolean isAccessibleAttributeFromHere(Attribute attribute,Class myClass){
        if(attribute.getVisibility().equals("public")){
            return true;
        }
        else{
            if(attribute.getVisibility().equals("private") && attribute.getClassWhereBelong().equals(myClass.getName())){
                return true;
            }
            else
                return false;
        }
    }
}

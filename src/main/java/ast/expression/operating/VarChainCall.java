package ast.expression.operating;


import symbolTable.*;
import symbolTable.Class;

public class VarChainCall extends ChainCall {

    private String varName;
    private boolean assignmentLeftSide = false;

    public void setAssignmentLeftSide(){
        assignmentLeftSide = true;
    }

    public VarChainCall(String varName,int lineNumber) {
        this.varName = varName;
        this.lineNumber=lineNumber;
    }

    public MethodType check(MethodType type) throws SemanticErrorException {
        if(!(type instanceof TidClass)){
            throw new SemanticErrorException(varName,type.getLineNumber(),"Error Semantico en la linea: "+
                    type.getLineNumber()+" no es posible acceder de forma encadena a un atributo de un tipo que " +
                    "no es de Clase como : "+
                    type.getTypeName());
        }
        else{
            Class clase = SymbolTable.getInstance().getClass(type.getTypeName());
            if(clase==null){
                throw new SemanticErrorException(type.getTypeName(),type.getLineNumber()," Error tipo de clase inexistente :" +
                        ""+type.getTypeName());
            }
            Attribute attributeWhatIAmLookingFor = clase.getAttributeIfExist(varName);
            if(attributeWhatIAmLookingFor==null){
                throw new SemanticErrorException(varName,type.getLineNumber()," Error atributo inexistente en la clase :" +
                        ""+clase.getName());
            }
            else {
                if(!attributeWhatIAmLookingFor.getVisibility().equals("public")){
                    throw new SemanticErrorException(varName,type.getLineNumber()," Error atributo declarado como Privado en la clase :" +
                            ""+clase.getName());
                }
                else{
                    return attributeWhatIAmLookingFor.getType();
                }
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return varName;
    }
}

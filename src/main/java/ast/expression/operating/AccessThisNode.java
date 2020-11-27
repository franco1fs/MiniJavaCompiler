package ast.expression.operating;

import symbolTable.*;

public class AccessThisNode extends PrimaryNode {
    private Unit unitWhereBelong = null;
    public AccessThisNode(int lineNumber,Unit unitWhereBelong){
        this.unitWhereBelong = unitWhereBelong;
        this.lineNumber = lineNumber;
    }

    public MethodType check() throws SemanticErrorException {
        Method me = null;
        if(unitWhereBelong instanceof Method)
            me = (Method) unitWhereBelong;
        if( me!=null &&  me.getMethodForm().equals("static")){
            throw new SemanticErrorException("this",lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" No puedo utilizar la referencia a this en un metodo static ");
        }
        return new TidClass(unitWhereBelong.getMyModule().getName(),lineNumber);
    }

    @Override
    public String getLexemeOfRepresentation() {
        return "this";
    }
}

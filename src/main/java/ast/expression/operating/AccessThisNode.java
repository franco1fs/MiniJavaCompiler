package ast.expression.operating;

import symbolTable.*;

public class AccessThisNode extends PrimaryNode {
    private Method methodWhereBelong;
    public AccessThisNode(int lineNumber,Method methodWhereBelong){
        this.methodWhereBelong = methodWhereBelong;
        this.lineNumber = lineNumber;
    }

    public MethodType check() throws SemanticErrorException {
        if(methodWhereBelong.getMethodForm().equals("static")){
            throw new SemanticErrorException("this",lineNumber,"Error Semantico en la linea: "+
                    lineNumber+" No puedo utilizar la referencia a this en un metodo static ");
        }
        return new TidClass(methodWhereBelong.getMyModule().getName(),lineNumber);
    }
}

package ast.sentence;

import ast.expression.operating.*;
import symbolTable.SemanticErrorException;

public class CallNode extends SentenceNode {

    private AccessNode accessNode;

    public CallNode(AccessNode accessNode) {
        this.accessNode = accessNode;
    }

    @Override
    public void check() throws SemanticErrorException {
        if(accessNode.getChainCallContainer().getChainCallArrayList()==null){
            if( !( accessNode.getPrimaryNode() instanceof AccessMethodNode || accessNode.getPrimaryNode() instanceof AccessStaticNode
                    || accessNode.getPrimaryNode() instanceof AccessConstructorNode)){
                throw new SemanticErrorException(accessNode.getPrimaryNode().getLexemeOfRepresentation(),lineNumber,"Error " +
                        "Semantico en la linea: "+lineNumber+
                        " no puede existir una sentencia llamada con encadenado vacio y con un acceso primario que no sea" +
                        "metodo directo, estatico o constructor");
            }
            else{
                accessNode.check();
            }
        }
        else{
            int lastEnchainCall = accessNode.getChainCallContainer().getChainCallArrayList().size() - 1 ;
            if( !( accessNode.getChainCallContainer().getChainCallArrayList().get(lastEnchainCall) instanceof MethodChainCall)){
                throw new SemanticErrorException(accessNode.getChainCallContainer().getChainCallArrayList().get(lastEnchainCall).getLexemeOfRepresentation()
                        ,lineNumber,"Error " +
                        "Semantico en la linea: "+lineNumber+
                        " no puede existir una sentencia llamada con encadenado y con su ultima acceso que no sea" +
                        "metodo directo");
            }
            accessNode.check();
        }
    }
}

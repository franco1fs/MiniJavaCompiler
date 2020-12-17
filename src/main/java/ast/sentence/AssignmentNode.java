package ast.sentence;

import ast.expression.ExpressionNode;
import ast.expression.operating.AccessNode;
import ast.expression.operating.AccessVarNode;
import ast.expression.operating.ChainCall;
import ast.expression.operating.VarChainCall;
import symbolTable.*;
import symbolTable.Class;

public class AssignmentNode extends SentenceNode {

    private AccessNode leftAccessNode;
    private ExpressionNode rightExpressionNode;
    private String assignmentType;
    private BlockNode blockWhereIBelong;

    public AssignmentNode(AccessNode leftAccessNode, ExpressionNode rightExpressionNode,String assignmentType,BlockNode blockNode) {
        this.leftAccessNode = leftAccessNode;
        this.rightExpressionNode = rightExpressionNode;
        this.lineNumber = lineNumber;
        this.blockWhereIBelong = blockNode;
        this.assignmentType = assignmentType;
    }

    @Override
    public void check() throws SemanticErrorException {
        MethodType typeOfAccess= null;
        if(leftAccessNode.getChainCallContainer().getChainCallArrayList().size() == 0){
            if(leftAccessNode.getPrimaryNode() instanceof AccessVarNode) {
                LocalVar localVar = blockWhereIBelong.getParameterOrLocalVarIfExist
                        (leftAccessNode.getPrimaryNode().getLexemeOfRepresentation());
                if (localVar == null) {
                    Class classWhereIBelong = (Class) blockWhereIBelong.getUnitWhereIBelong().getMyModule();
                    Attribute attribute = classWhereIBelong.getAttributeIfExist
                            (leftAccessNode.getPrimaryNode().getLexemeOfRepresentation());
                    if (attribute == null) {
                        throw new SemanticErrorException(leftAccessNode.getPrimaryNode().getLexemeOfRepresentation(), lineNumber, "Error " +
                                "Semantico en la linea: " + lineNumber +
                                " la asignacion es incorrecta debido a que el acceso no es ni un parametro, ni var local" +
                                "ni atributo de instancia");
                    } else {
                        typeOfAccess = leftAccessNode.check();
                    }
                } else {
                    typeOfAccess = leftAccessNode.check();
                }


            }
            else{
                throw new SemanticErrorException(leftAccessNode.getPrimaryNode().getLexemeOfRepresentation(), lineNumber, "Error " +
                        "Semantico en la linea: " + lineNumber +
                        " la asignacion es incorrecta debido a que el acceso no es ni un parametro, ni var local" +
                        "ni atributo de instancia");
            }

        }
        else if(leftAccessNode.getChainCallContainer().getChainCallArrayList().size()>0){
            int indexOfLastElementOfCall = leftAccessNode.getChainCallContainer().getChainCallArrayList().size() -1 ;
            ChainCall lastElement = leftAccessNode.getChainCallContainer().getChainCallArrayList().get(indexOfLastElementOfCall);
            if(lastElement instanceof VarChainCall){
                typeOfAccess = leftAccessNode.check();
            }
            else{
                throw new SemanticErrorException(leftAccessNode.getChainCallContainer().getChainCallArrayList().
                        get(indexOfLastElementOfCall).getLexemeOfRepresentation()
                        ,lineNumber,"Error " +
                        "Semantico en la linea: "+lineNumber+
                        " la asignacion es incorrecta debido a que el acceso encadenado no tiene como ultimo elemento el acceso a " +
                        "una variable");
            }
        }
        if(typeOfAccess!=null){
            MethodType typeOfExpression = rightExpressionNode.check();
            if(assignmentType.equals("=")){
                if(!(typeOfAccess.isConformedBy(typeOfExpression))){
                    throw new SemanticErrorException(assignmentType,lineNumber,"Error " +
                            "Semantico en la linea: "+lineNumber+
                            " la asignacion es incorrecta debido a que no hay conformidad de tipos entre" +
                            "el acceso y la expresion");
                }
            }
            else{
                if( !(typeOfAccess.getTypeName().equals("int"))  || !(typeOfExpression.getTypeName().equals("int"))){
                    throw new SemanticErrorException(assignmentType,lineNumber,"Error " +
                            "Semantico en la linea: "+lineNumber+
                            " la asignacion es incorrecta debido a que no hay conformidad de tipos entre" +
                            "el acceso y la expresion ya que deberian ser ambos de tipo entero");
                }
            }
        }
    }

    @Override
    public void generate() {
        rightExpressionNode.generate();
        leftAccessNode.generate();
    }
}

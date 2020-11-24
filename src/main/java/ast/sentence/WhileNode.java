package ast.sentence;

import ast.expression.ExpressionNode;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class WhileNode extends SentenceNode {
    private ExpressionNode expressionNode;
    private SentenceNode sentenceNode;

    public WhileNode(ExpressionNode expressionNode, SentenceNode sentenceNode) {
        this.expressionNode = expressionNode;
        this.sentenceNode = sentenceNode;
    }

    @Override
    public void check() throws SemanticErrorException {
        MethodType typeOfExpression = expressionNode.check();

        if(!(typeOfExpression.getTypeName().equals("boolean"))){
            throw new SemanticErrorException("while",lineNumber,"Error " +
                    "Semantico en la linea: "+lineNumber+
                    " el tipo de la condición del While no es booleano");

        }
        else {
            sentenceNode.check();
        }
    }
}

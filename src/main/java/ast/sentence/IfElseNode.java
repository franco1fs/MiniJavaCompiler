package ast.sentence;

import ast.expression.ExpressionNode;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;

public class IfElseNode extends SentenceNode {

    private ExpressionNode expressionNode;
    private SentenceNode ifSentence;
    private SentenceNode elseSentence;

    public IfElseNode(ExpressionNode expressionNode, SentenceNode ifSentence, SentenceNode elseSentence) {
        this.expressionNode = expressionNode;
        this.ifSentence = ifSentence;
        this.elseSentence = elseSentence;
    }

    @Override
    public void check() throws SemanticErrorException {
        MethodType typeOfExpression = expressionNode.check();

        if(!(typeOfExpression.getTypeName().equals("boolean"))){
            throw new SemanticErrorException("if",lineNumber,"Error " +
                    "Semantico en la linea: "+lineNumber+
                    " el tipo de la condición del if no es booleano");

        }
        else {
            ifSentence.check();
            if(elseSentence!=null){
                elseSentence.check();
            }
        }
    }

    @Override
    public void generate() {

    }
}

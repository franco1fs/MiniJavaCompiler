package ast.sentence;

import ast.expression.ExpressionNode;
import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;

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
        SymbolTable symbolTable = SymbolTable.getInstance();
        expressionNode.generate();
        String l1 = symbolTable.getLabel();
        symbolTable.genInstruction("BF "+l1);

        ifSentence.generate();

        if(elseSentence==null){
            symbolTable.genInstruction(l1+": NOP");
        }
        else{
            String l2 = symbolTable.getLabel();
            symbolTable.genInstruction("JUMP "+l2);
            symbolTable.genInstruction(l1+": NOP");
            elseSentence.generate();
            symbolTable.genInstruction(l2+": NOP");
        }
    }
}

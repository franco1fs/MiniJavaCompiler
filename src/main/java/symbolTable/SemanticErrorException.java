package symbolTable;

public class SemanticErrorException extends Exception{


    private String message;
    private int lineNumber;
    private String lexeme;

    public SemanticErrorException(String lexeme,int lineNumber,String msg){
        this.lexeme = lexeme;
        message = msg;
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLexeme() {
        return lexeme;
    }
}

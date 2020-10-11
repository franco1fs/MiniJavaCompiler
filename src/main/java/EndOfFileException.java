public class EndOfFileException extends Exception {
    private String lexeme;
    private int lineNumber;
    private String message;
    public EndOfFileException(String m,String lex,int ln){
        message = m;
        lexeme = lex;
        lineNumber = ln;
    }

    public String getMessage(){
        return message;
    }
    public String getLexeme(){
        return lexeme;
    }
    public int getLineNumber(){
        return lineNumber;
    }
}

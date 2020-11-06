package lexicalAnalyzer;

public class Token {
    private String name;
    private String lexeme;
    private int lineNumber;
    //private int colNumber;

    public Token(String name, String lex, int lineN){
        this.name = name;
        lexeme = lex;
        lineNumber = lineN;

    }

    public String getName() {
        return name;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}

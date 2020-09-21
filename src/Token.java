public class Token {
    private String name;
    private String lexeme;
    private int lineNumber;
    private int colNumber;

    public Token(String name, String lex, int lineN, int colN){
        this.name = name;
        lexeme = lex;
        lineNumber = lineN;
        colNumber = colN;
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

    public int getColNumber() {
        return colNumber;
    }
}

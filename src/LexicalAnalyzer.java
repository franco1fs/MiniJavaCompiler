public class LexicalAnalyzer implements ILexicalAnalyzer {
    private String lexeme;
    private char currentChar;
    IFileManager fileManager;

    public LexicalAnalyzer(IFileManager fm){
        fileManager = fm;
        updateCurrentChar();
    }
    private void updateLexeme(){
        lexeme = lexeme + currentChar;
    }

    private void updateCurrentChar(){
        try {
            currentChar = fileManager.nextChar();
        }
        catch (Exception endOfFileException){
            // Llegue al final del archivo
        }
    }
    @Override
    public Token nextToken() {
        return null;
    }
}

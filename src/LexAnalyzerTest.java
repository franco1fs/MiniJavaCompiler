import java.util.ArrayList;

public class LexAnalyzerTest {
    private String lexeme;
    private char currentChar;
    IFileManager fileManager;
    private int lineNumber;
    private boolean endOfFile;

    private ArrayList<String> reservedWords;

    public LexAnalyzerTest(IFileManager fm){
        fileManager = fm;
        lineNumber = 1;
        endOfFile = false;
        updateCurrentChar();
        initializeReservedWords();
    }
    private void initializeReservedWords(){
        reservedWords = new ArrayList<String>();
        reservedWords.add("class");
        reservedWords.add("extends");
        reservedWords.add("static");
        reservedWords.add("dynamic");
        reservedWords.add("void");
        reservedWords.add("boolean");
        reservedWords.add("char");
        reservedWords.add("int");
        reservedWords.add("String");
        reservedWords.add("public");
        reservedWords.add("private");
        reservedWords.add("if");
        reservedWords.add("else");
        reservedWords.add("while");
        reservedWords.add("return");
        reservedWords.add("this");
        reservedWords.add("new");
        reservedWords.add("null");
        reservedWords.add("true");
        reservedWords.add("false");
    }

    private void updateLexeme(){
        lexeme = lexeme + currentChar;
    }

    private void updateCurrentChar(){
        try {
            currentChar = fileManager.nextChar();
            if(currentChar == '\n')
                System.out.println("Encontre un enter");
            if(currentChar == ' ')
                System.out.println("Encontre un espacio");

            System.out.println(currentChar);
        }
        catch (Exception endOfFileException){
            currentChar = '\n';
            endOfFile = true;
        }
    }

    public Token nextToken() throws EndOfFileException {
        lexeme = "";
        return state_initial();
    }

    private Token state_endOfFile(){
        return new Token("EOF",lexeme,lineNumber);
    }
    //Initial State
    private Token state_initial() throws EndOfFileException{
        if(endOfFile){
            return state_endOfFile();
        }
        if(Character.isWhitespace(currentChar) || currentChar == '\n'){
            updateCurrentChar();
            return state_initial();
        }
        else if(currentChar== '\''){
            updateLexeme();
            updateCurrentChar();
            return state_literalChar();
        }

        else if(currentChar == '"'){
            updateLexeme();
            updateCurrentChar();
            return state_literalString();
        }
        else {
            return null;
        }


    }
    private Token state_literalChar() throws EndOfFileException{
        if(currentChar == '\\'){
            updateLexeme();
            updateCurrentChar();
            return state_allPossibleChar();
        }
        else if (currentChar == '\''){
            throw new EndOfFileException("Character mal formado, no se permite ''' ",lexeme,lineNumber);
        }
        else{
            updateLexeme();
            updateCurrentChar();
            return state_checkSingleQuote();
        }
    }

    private Token state_checkSingleQuote() throws EndOfFileException{
        if(currentChar == '\''){
            updateLexeme();
            updateCurrentChar();
            return  state_returnLiteralChar();
        }
        else{
            throw new EndOfFileException("Character mal formado, falto cerrar las comillas simples",lexeme,lineNumber);
        }
    }
    private Token state_allPossibleChar() throws EndOfFileException {
        if (endOfFile) {
            throw new EndOfFileException("Character mal formado",lexeme,lineNumber);
        } else {
            updateLexeme();
            updateCurrentChar();
            return state_checkSingleQuote();
        }
    }

    private Token state_returnLiteralChar(){
        return  new Token("Literal Character",lexeme,lineNumber);
    }

    private Token state_literalString() throws EndOfFileException{
        if(endOfFile){
            throw new EndOfFileException("Finalizo el archivo antes de cerrar el String",lexeme,lineNumber);
        }
        else if(currentChar == '\n'){
            throw new EndOfFileException("Salto de Linea invalido dentro de un String",lexeme,lineNumber);
        }
        else if (currentChar== '"'){
            updateLexeme();
            updateCurrentChar();
            return state_closeString();
        }
        else{
            updateLexeme();
            updateCurrentChar();
            return state_literalString();
        }
    }

    private Token state_closeString() throws EndOfFileException{
        //lexeme.replace('"',' ');
        return new Token("Literal String",lexeme,lineNumber);
    }


}

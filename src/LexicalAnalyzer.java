import java.util.ArrayList;

public class LexicalAnalyzer implements ILexicalAnalyzer {
    private String lexeme;
    private char currentChar;
    IFileManager fileManager;
    private int lineNumber;
    private boolean endOfFile;

    private ArrayList<String> reservedWords;

    public LexicalAnalyzer(IFileManager fm){
        fileManager = fm;
        lineNumber = 1;
        endOfFile = false;
        updateCurrentChar();
        initializeReservedWords();
    }
    private void initializeReservedWords(){
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
        }
        catch (Exception endOfFileException){
            currentChar = '\n';
            endOfFile = true;
        }
    }
    @Override
    public Token nextToken() throws Exception {
        lexeme = "";
        return state_initial();
    }
    //Initial State
    private Token state_initial() throws Exception{
        if(endOfFile){
            return state_endOfFile();
        }
        if(Character.isUpperCase(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_idClass();
        }
        else if(Character.isLowerCase(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_idVarMet();
        }
        else if(Character.isDigit(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_literalInt();
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
        else{
            //Provisiorio
            return null;
        }

    }
    private Token state_endOfFile(){
        return new Token("EOF",lexeme,lineNumber);
    }

    private Token state_idClass() throws Exception{
        if(Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_'){
            updateLexeme();
            updateCurrentChar();
            return state_idClass();
        }
        else{
            return new Token("Identificador de clase",lexeme,lineNumber);
        }
    }

    private Token state_idVarMet() throws Exception{
        if(Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_'){
            updateLexeme();
            updateCurrentChar();
            return state_idVarMet();
        }
        else{
            if(reservedWords.contains(lexeme)){
                return new Token(lexeme,lexeme,lineNumber);
            }
            else{
                return new Token("Identificador de Variable/Metodo",lexeme,lineNumber);
            }

        }
    }

    private Token state_literalInt() throws Exception{
        if(Character.isDigit(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_literalInt();
        }
        else{
            return new Token("Literal de tipo Integer",lexeme,lineNumber);
        }
    }

    private Token state_literalChar() throws Exception{
        if(currentChar == '\\'){
            updateLexeme();
            updateCurrentChar();
            return state_allPossibleChar();
        }
        else if (currentChar != '\''){
            throw new Exception("Character mal formado, no se permite ''' ");
        }
        else{
            updateLexeme();
            updateCurrentChar();
            return state_checkSingleQuote();
        }
    }

    private Token state_checkSingleQuote() throws Exception{
        if(currentChar == '\''){
            updateLexeme();
            updateCurrentChar();
            return  state_returnLiteralChar();
        }
        else{
            throw new Exception("Character mal formado, falto cerrar las comillas simples");
        }
    }
    private Token state_allPossibleChar() throws Exception {
        if (endOfFile) {
            throw new Exception("Character mal formado");
        } else {
            updateLexeme();
            updateCurrentChar();
            return state_checkSingleQuote();
        }
    }

    private Token state_returnLiteralChar(){
        return  new Token("Literal Character",lexeme,lineNumber);
    }
    private Token state_literalString() throws Exception{
        if(endOfFile){
            throw new Exception("Finalizo el archivo antes de cerrar el String");
        }
        else if(currentChar == '\n'){
            throw new Exception("Salto de Linea invalido dentro de un String");
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

    private Token state_closeString() throws Exception{
        lexeme.replace('"',' ');
        return new Token("Literal String",lexeme,lineNumber);
    }
}

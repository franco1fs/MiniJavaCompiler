import java.util.*;

public class LexicalAnalyzer implements ILexicalAnalyzer {
    private String lexeme;
    private char currentChar;
    IFileManager fileManager;
    private int lineNumber;
    private boolean endOfFile;

    private ArrayList<String> reservedWords;
    private Hashtable<String,String> tokensConversion;

    public LexicalAnalyzer(IFileManager fm){
        fileManager = fm;
        lineNumber = 1;
        endOfFile = false;
        updateCurrentChar();
        initializeReservedWords();
        initializeTokensNames();
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

    private void initializeTokensNames(){
        tokensConversion = new Hashtable<String, String>();
        tokensConversion.put("{","LLave abre");
        tokensConversion.put("}","Llave cierra");
        tokensConversion.put(";","Punto y coma");
        tokensConversion.put(".","Punto");
        tokensConversion.put(",","Coma");
        tokensConversion.put("(","Parentesis abre");
        tokensConversion.put(")","Parentesis cierra");
        //tokensConversion.put("[","Corchete abre");
        //tokensConversion.put("]", "Corchete cierra");

        tokensConversion.put("+","Operador suma");
        tokensConversion.put("-","Operador resta");
        tokensConversion.put("*","Operador multiplicación");
        tokensConversion.put("/","Operador modulo");
        tokensConversion.put("%","Operador división");
        tokensConversion.put(">","Operador Mayor");
        tokensConversion.put(">=", "Operador Mayor o igual");
        tokensConversion.put("<", "Operador Menor");
        tokensConversion.put("<=", "Operador Menor o igual");
        tokensConversion.put("==", "Operador Comparacion");
        tokensConversion.put("!", "Operador Not");
        tokensConversion.put("!=", "Operador Distinto");
        tokensConversion.put("&&","Operador AND");
        tokensConversion.put("||","Operador OR");
        tokensConversion.put("=", "Asignacion");
        tokensConversion.put("+=", "Asignacion Compuesta con +");
        tokensConversion.put("-=", "Asignacion Compuesta con -");
    }
    private void updateLexeme(){
        lexeme = lexeme + currentChar;
    }

    private void updateCurrentChar(){
        try {
            currentChar = fileManager.nextChar();
        }
        catch (Exception endOfFileLexicalErrorException){
            currentChar = '\n';
            endOfFile = true;
        }
    }
    @Override
    public Token nextToken() throws LexicalErrorException {
        lexeme = "";
        return state_initial();
    }
    //Initial State
    private Token state_initial() throws LexicalErrorException{
        if(endOfFile){
            return state_endOfFile();
        }
        else if(Character.isWhitespace(currentChar)){
            if(currentChar == '\n'){
                lineNumber++;
            }
            updateCurrentChar();
            return state_initial();
        }
        else if(Character.isUpperCase(currentChar)){
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
        else if (isPunctuationSymbol(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_punctuaction();
        }
        else if (currentChar == '*' || currentChar == '%'){
            updateLexeme();
            updateCurrentChar();
            return state_operator_final();
        }
        else if(currentChar == '>' || currentChar == '<' || currentChar == '!'){
            updateLexeme();
            updateCurrentChar();
            return state_operator_waitForEquals();
        }
        else if(currentChar == '&'){
            updateLexeme();
            updateCurrentChar();
            return state_waitForAmpersand();
        }
        else if(currentChar == '|'){
            updateLexeme();
            updateCurrentChar();
            return state_waitForVerticalLine();
        }

        else if(currentChar == '+' || currentChar == '-' || currentChar == '='){
            updateLexeme();
            updateCurrentChar();
            return state_assignment_waitForEquals();
        }
        else if(currentChar == '/'){
            updateLexeme();
            updateCurrentChar();
            return state_possibleComment();
        }
        else{
            updateLexeme();
            updateCurrentChar();
            return state_unallowedSymbol();
        }
    }


    private Token state_possibleComment() throws LexicalErrorException{
        if(currentChar == '/'){
            updateCurrentChar();
            return state_commentUntilEoL();
        }
        else if(currentChar == '*'){
            updateCurrentChar();
            return state_commentMultiLine();
        }
        else{
            return state_operator_final();
        }
    }


    private Token state_commentUntilEoL() throws LexicalErrorException{
        while (currentChar != '\n'){
            updateCurrentChar();
        }
        return nextToken();
    }

    private Token state_commentMultiLine() throws LexicalErrorException{
        while (currentChar != '*' && !endOfFile){
            if(currentChar == '\n')
                lineNumber++;
            updateCurrentChar();
        }
        if(endOfFile){
            throw new LexicalErrorException("Error al no cerrar el comentario Multilinea","",lineNumber);
        }
        else if (currentChar == '*') {
            updateCurrentChar();
            if(currentChar == '/') {
                updateCurrentChar();
                return nextToken();
            }
            else if(endOfFile){
                throw new LexicalErrorException("Error al no cerrar el comentario Multilinea","",lineNumber);
            }
            else
                return state_commentMultiLine();
        }
        else
            return nextToken();
    }

    private Token state_unallowedSymbol() throws LexicalErrorException{
        throw new LexicalErrorException("No es un simbolo válido",lexeme,lineNumber);
    }

    private boolean isPunctuationSymbol(char currentChar){
        return currentChar == '(' || currentChar == ')' || currentChar== '{'
                || currentChar == '}' || currentChar == ';'
                || currentChar == ',' || currentChar == '.' ;
    }

    private Token state_endOfFile(){
        return new Token("EOF",lexeme,lineNumber);
    }

    private Token state_idClass() throws LexicalErrorException{
        if(Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_'){
            updateLexeme();
            updateCurrentChar();
            return state_idClass();
        }
        else{
            if(reservedWords.contains(lexeme))
                return new Token("pr_"+lexeme,lexeme,lineNumber);
            else
                return new Token("Identificador de clase",lexeme,lineNumber);
        }
    }

    private Token state_idVarMet() throws LexicalErrorException{
        if(Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_'){
            updateLexeme();
            updateCurrentChar();
            return state_idVarMet();
        }
        else{
            if(reservedWords.contains(lexeme)){
                return new Token("pr_"+lexeme,lexeme,lineNumber);
            }
            else{
                return new Token("Identificador de Variable/Metodo",lexeme,lineNumber);
            }

        }
    }

    private Token state_literalInt() throws LexicalErrorException{
        if(Character.isDigit(currentChar)){
            updateLexeme();
            updateCurrentChar();
            return state_literalInt();
        }
        else{

            return new Token("Literal Integer",lexeme,lineNumber);
        }
    }

    private Token state_literalChar() throws LexicalErrorException{
        if(currentChar == '\\'){
            updateLexeme();
            updateCurrentChar();
            return state_allPossibleChar();
        }
        else if (currentChar == '\''){
            updateLexeme();
            updateCurrentChar();
            throw new LexicalErrorException("Character mal formado, no se permite un caracter vacio '' ",lexeme,lineNumber);
        }
        else {
            if(endOfFile){
                throw new LexicalErrorException("Finalizo el archivo",lexeme,lineNumber);
            }
            else {
                if(Character.isWhitespace(currentChar) && (int) currentChar == 13) {
                    lexeme += '\n';
                }else
                    updateLexeme();

                updateCurrentChar();
                return state_checkSingleQuote();
            }
        }
    }

    private Token state_checkSingleQuote() throws LexicalErrorException{
        if(currentChar == '\''){
            updateLexeme();
            updateCurrentChar();
            return  state_returnLiteralChar();
        }
        else{
            throw new LexicalErrorException("Character mal formado ",lexeme,lineNumber);
        }
    }
    private Token state_allPossibleChar() throws LexicalErrorException {
        if (endOfFile) {
            throw new LexicalErrorException("Character mal formado",lexeme,lineNumber);
        } else {
            if(Character.isWhitespace(currentChar) && (int)currentChar == 13) {
                lexeme += '\n';
            }
            else
                updateLexeme();

            updateCurrentChar();
            return state_checkSingleQuote();
        }
    }

    private Token state_returnLiteralChar(){
        return  new Token("Literal Character",lexeme,lineNumber);
    }
    private Token state_literalString() throws LexicalErrorException{
        if(endOfFile){
            throw new LexicalErrorException("Finalizo el archivo antes de cerrar el String",lexeme,lineNumber);
        }
        else if(currentChar == '\n'){
            updateLexeme();
            throw new LexicalErrorException("String mal formado, salto de linea inválido",lexeme,lineNumber);
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

    private Token state_closeString() throws LexicalErrorException{
        //lexeme.replace('"',' ');
        return new Token("Literal String",lexeme,lineNumber);
    }

    private Token state_punctuaction(){
        return  new Token(tokensConversion.get(lexeme),lexeme,lineNumber);
    }
    private Token state_operator_final(){
        return  new Token(tokensConversion.get(lexeme),lexeme,lineNumber);
    }
    private Token state_operator_waitForEquals(){
        if(currentChar == '='){
            updateLexeme();
            updateCurrentChar();
            return state_operator_final();
        }
        else{
            return  new Token(tokensConversion.get(lexeme),lexeme,lineNumber);
        }
    }

    private Token state_waitForAmpersand() throws LexicalErrorException{
        if(currentChar == '&'){
            updateLexeme();
            updateCurrentChar();
            return state_operator_final();
        }
        else {
            throw new LexicalErrorException("Operador AND mal formado, falto agregar el segundo &",lexeme,lineNumber);
        }
    }
    private Token state_waitForVerticalLine() throws LexicalErrorException{
        if(currentChar == '|'){
            updateLexeme();
            updateCurrentChar();
            return state_operator_final();
        }
        else {
            throw new LexicalErrorException("Operador OR mal formado, falto agregar el segundo |",lexeme,lineNumber);
        }
    }

    private Token state_assignment_final(){
        return  new Token(tokensConversion.get(lexeme),lexeme,lineNumber);
    }

    private Token state_assignment_waitForEquals(){
        if(currentChar == '='){
            updateLexeme();
            updateCurrentChar();
            return state_assignment_final();
        }
        else{
            return  new Token(tokensConversion.get(lexeme),lexeme,lineNumber);
        }
    }
}

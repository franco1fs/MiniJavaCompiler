import java.util.*;

public class SyntacticAnalyzer {


    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;

    public SyntacticAnalyzer(LexicalAnalyzer aLex) throws SyntacticErrorException, LexicalErrorException{
        lexicalAnalyzer = aLex;
        try {
            currentToken = aLex.nextToken();
            inicial();
        }
        catch (LexicalErrorException e){
            // Ver como manejo la exepcion
        }
    }

    private void match(String tokenName) throws SyntacticErrorException, LexicalErrorException{
        if(tokenName.equals(currentToken.getName())) {
            currentToken = lexicalAnalyzer.nextToken();
        }
        else{
            throw new SyntacticErrorException(currentToken,tokenName);
        }
    }

    private void inicial() throws SyntacticErrorException, LexicalErrorException{
        listaClases();
        match("EOF");
    }

    private void listaClases() throws SyntacticErrorException, LexicalErrorException{
        clase();
        restoDeClases();
    }

    private void restoDeClases() throws SyntacticErrorException, LexicalErrorException{
        // Primeros (<Clase>) = {class}
        if(Objects.equals("pr_class", currentToken.getName())){
            clase();
        }
        else{
            // <RestoDeClases> -> e
        }
    }

    private void clase() throws SyntacticErrorException, LexicalErrorException{
        match("pr_class");
        match("idClase");
        herencia();
        match("Llave abre");
        listaMiembros();
        match("Llave cierra");
    }

    private void herencia() throws SyntacticErrorException, LexicalErrorException{
        // Pr(<herencia>)= {extends}
        if(Objects.equals("pr_extends", currentToken.getName())){
            match("pr_extends");
            match("idClase");
        }
        else{
            //  herencia -> e
        }
    }

    private void listaMiembros() throws SyntacticErrorException, LexicalErrorException{
        List<String> first = Arrays.asList("pr_public","pr_private", "idClase",
                "pr_static","pr_dynamic");
        if(first.contains(currentToken.getName())){
            miembro();
            listaMiembros();
        }
        else{
            // listaMiembros -> e
        }
    }

    private void miembro() throws SyntacticErrorException, LexicalErrorException{
        if(Arrays.asList("pr_public","pr_private").contains(currentToken.getName())){
            atributo();
        }
        else if(Objects.equals("idClase", currentToken.getName())){
            constructor();
        }
        else if(Arrays.asList( "pr_static","pr_dynamic").contains(currentToken.getName())){
            metodo();
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_public, pr_private, idClase, pr_static o pr_dynamic");
        }
    }

    private void atributo() throws SyntacticErrorException, LexicalErrorException{
        visibilidad();
        tipo();
        listaDecAtrs();
        match("Punto y coma");
    }

    private void metodo() throws SyntacticErrorException, LexicalErrorException{
        formaMetodo();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
        bloque();
    }

    private void constructor() throws SyntacticErrorException, LexicalErrorException{
        match("idClase");
        argsFormales();
        bloque();
    }

    private void visibilidad() throws SyntacticErrorException, LexicalErrorException{

        if(Objects.equals("pr_public", currentToken.getName())) {
            match("pr_public");
        }
        else if(Objects.equals("pr_private", currentToken.getName())) {
            match("pr_private");
        }
        else{
            throw new SyntacticErrorException(currentToken, " pr_public o pr_private");
        }
        }
    private void tipo() throws SyntacticErrorException, LexicalErrorException{
        //Primeros (TipoPrimitivo) = {boolean,char,int,string}
        if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipoPrimitivo();
        }
        else if(Objects.equals("idClase", currentToken.getName())) {
            match("idClase");
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_boolean, pr_char, pr_int, pr_String o idClase");
        }
    }

    private void tipoPrimitivo() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("pr_boolean", currentToken.getName())){
            match("pr_boolean");
        }
        else if(Objects.equals("pr_char", currentToken.getName())){
            match("pr_char");
        }
        else if(Objects.equals("pr_int", currentToken.getName())){
            match("pr_int");
        }
        else if(Objects.equals("pr_String", currentToken.getName())){
            match("pr_String");
        }
        else{
            throw  new SyntacticErrorException(currentToken,"pr_boolean, pr_char, pr_int o pr_String");
        }
    }

    private void listaDecAtrs() throws SyntacticErrorException, LexicalErrorException{
        match("idMetVar");
        restoListaDecAtrs();
    }

    private void restoListaDecAtrs() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaDecAtrs();
        }
        else {
            // restoListaDecAtrs -> e
        }
    }


    private void formaMetodo() throws SyntacticErrorException,LexicalErrorException{
        if(Objects.equals("pr_static", currentToken.getName())){
            match("pr_static");
        }
        else if(Objects.equals("pr_dynamic", currentToken.getName())){
            match("pr_dynamic");
        }
        else{
            throw new SyntacticErrorException(currentToken,"pr_satic o pr_dynamic");
        }
    }
    private void tipoMetodo() throws SyntacticErrorException,LexicalErrorException{
        //Pr(tipo) = {boolean,char,int,String,idClase}
        if(Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipo();
        }
        else if(Objects.equals("pr_void", currentToken.getName())){
            match("pr_void");
        }
        else{
            throw new SyntacticErrorException(currentToken,"pr_boolean, pr_char, pr_int o pr_String, idClase o pr_void");
        }
    }

    private void argsFormales() throws SyntacticErrorException, LexicalErrorException{
        match("Parentesis abre");
        listaArgsFormalesOVacio();
        match("Parentesis cierra");
    }

    private void listaArgsFormalesOVacio() throws SyntacticErrorException, LexicalErrorException{
        //Pr(listaArgsFormales)= {boolean,int,char,String,idClase}
        if(Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())) {
            listaArgsFormales();
        }
        else{
            //listaArgsFormalesOVacio -> e
        }
    }

    private void listaArgsFormales() throws SyntacticErrorException, LexicalErrorException{
        argFormal();
        restoArgFormales();
    }

    private void restoArgFormales() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaArgsFormales();
        }
        else{
            // restoArgsFormales -> e
        }
    }
    private void argFormal() throws SyntacticErrorException, LexicalErrorException{
        tipo();
        match("idMetVar");
    }

    private void bloque() throws SyntacticErrorException, LexicalErrorException{
        match("Llave abre");
        listaSentencias();
        match("Llave cierra");
    }

    private void listaSentencias() throws SyntacticErrorException, LexicalErrorException{
        List<String> firstOfSentence = Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String",
                "Llave abre","Punto y coma","pr_if","pr_while","pr_return",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre");

        if(firstOfSentence.contains(currentToken.getName())){
            sentencia();
            listaSentencias();
        }
        else{
            //listaSentencias -> e
        }
    }

    private void sentencia() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Punto y coma", currentToken.getName())){
            match("Punto y coma");
        }
        else if(Arrays.asList("pr_this","idMetVar","pr_static","pr_new","Parentesis abre").contains(currentToken.getName())){
            asignacionOLlamada();
            match("Punto y coma");
        }
        else if(Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipo();
            listaDecVars();
            match("Punto y coma");
        }
        else if(Objects.equals("pr_if", currentToken.getName())){
            match("pr_if");
            match("Parentesis abre");
            expresion();
            match("Parentesis cierra");
            sentencia();
            restoSentenciaElseOVacio();
        }
        else if(Objects.equals("pr_while", currentToken.getName())){
            match("pr_while");
            match("Parentesis abre");
            expresion();
            match("Parentesis cierra");
            sentencia();
        }
        else if(Objects.equals("Llave abre", currentToken.getName())){
            bloque();
        }
        else if(Objects.equals("pr_return", currentToken.getName())){
            match("pr_return");
            expresionOVacio();
            match("Punto y coma");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Punto y coma, pr_this, idMetVar, pr_static ,pr_new ," +
                    "Parentesis abre, pr_if, icClase, pr_boolean, pr_char, pr_int, pr_String, pr_while, Llave abre, pr_return");
        }
    }
    private void restoSentenciaElseOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("pr_else", currentToken.getName())){
            match("pr_else");
            sentencia();
        }
        else{
            // -> e
        }
    }

    private void asignacionOLlamada() throws SyntacticErrorException, LexicalErrorException {
        acceso();
        restoAsignacionOVacio();
    }
    private void restoAsignacionOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Arrays.asList("Asignacion","Asignacion +","Asignacion -").contains(currentToken.getName())){
            tipoDeAsignacion();
            expresion();
        }
        else{
            // -> e
        }
    }
    private void tipoDeAsignacion() throws SyntacticErrorException, LexicalErrorException{
        if (Objects.equals("Asignacion", currentToken.getName())) {
            match("Asignacion");
        }
        else if (Objects.equals("Asignacion +", currentToken.getName())){
            match("Asignacion +");
        }
        else if (Objects.equals("Asignacion -", currentToken.getName())){
            match("Asignacion -");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Asignacion, Asignacion + o Asignacion -");
        }
    }
    private void listaDecVars() throws SyntacticErrorException,LexicalErrorException{
        match("idMetVar");
        restoListaVarsOVacio();
    }

    private void restoListaVarsOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Coma", currentToken.getName())) {
            match("Coma");
            listaDecVars();
        }
        else{
            // -> e
        }
    }

    private void expresionOVacio() throws SyntacticErrorException, LexicalErrorException {
        List<String> firstOfExpOrEmp = Arrays.asList("pr_this","idMetVar","pr_static","pr_new","Parentesis abre",
                "Operador suma","Operador resta","Operador not",
                "pr_null","pr_true","pr_false","Literal int","Literal char","Literal String");

        if(firstOfExpOrEmp.contains(currentToken.getName())){
            expresion();
        }
        else{
            // -> e
        }
    }

    private void expresion() throws SyntacticErrorException, LexicalErrorException{
        expresionUnaria();
        restoExpresion();
    }

    private void restoExpresion() throws SyntacticErrorException, LexicalErrorException{
        List<String> firstOfBinOp = Arrays.asList("Operador suma","Operador resta","Operador multiplicacion","Operador modulo",
               "Operador division","Operador OR","Operador AND","Operador mayor" , "Operador menor", "Operador mayor o igual",
                "Operador menor o igual","Operador distinto","Operador comparacion");

        if(firstOfBinOp.contains(currentToken.getName())){
            operadorBinario();
            expresionUnaria();
            restoExpresion();
        }
        else{
            // -> e
        }
    }

    private void operadorBinario() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Operador comparacion", currentToken.getName())){
            match("Operador comparacion");
        }
        else  if(Objects.equals("Operador distinto", currentToken.getName())){
            match("Operador distinto");
        }
        else  if(Objects.equals("Operador menor", currentToken.getName())){
            match("Operador menor");
        }
        else if(Objects.equals("Operador mayor", currentToken.getName())){
            match("Operador mayor");
        }
        else if(Objects.equals("Operador menor o igual", currentToken.getName())){
            match("Operador menor o igual");
        }
        else if(Objects.equals("Operador mayor o igual", currentToken.getName())){
            match("Operador mayor o igual");
        }
        else if(Objects.equals("Operador suma", currentToken.getName())){
            match("Operador suma");
        }
        else if(Objects.equals("Operador resta", currentToken.getName())){
            match("Operador resta");
        }
        else if(Objects.equals("Operador multiplicacion", currentToken.getName())){
            match("Operador multiplicacion");
        }
        else if(Objects.equals("Operador modulo", currentToken.getName())){
            match("Operador modulo");
        }
        else if(Objects.equals("Operador division", currentToken.getName())){
            match("Operador division");
        }
        else if(Objects.equals("Operador OR", currentToken.getName())){
            match("Operador OR");
        }
        else if(Objects.equals("Operador AND", currentToken.getName())){
            match("Operador AND");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Operador comparacion, Operador distinto, Operador menor," +
                    "Operador mayor, Operador menor o igual, Operador mayor o igual, Operador suma," +
                    "Operador resta, Operador multiplicacion, Operador modulo, Operador division," +
                    "Operador OR o Operador AND");
        }
    }
    private void expresionUnaria() throws SyntacticErrorException, LexicalErrorException{
        if(Arrays.asList("Operador suma","Operador resta","Operador not").contains(currentToken.getName())) {
            operadorUnario();
            operando();
        }
        else if(Arrays.asList("pr_null","pr_true","pr_false","Literal int","Literal char","Literal String",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre").contains(currentToken.getName())){
            operando();
        }
        else{
            throw new SyntacticErrorException(currentToken, "Operador suma, Operador resta, Operador not,"+
                    "pr_null, pr_true, pr_false, Literal int, Literal char, Literal String, "+
                    "pr_this, idMetVar, pr_static ,pr_new o Parentesis abre");
        }
    }

    private void operadorUnario() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Operador suma", currentToken.getName())){
            match("Operador suma");
        }
        else if(Objects.equals("Operador resta", currentToken.getName())){
            match("Operador resta");
        }
        else if(Objects.equals("Operador not", currentToken.getName())){
            match("Operador not");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Operador suma, Operador resta o Operador not");
        }
    }
    private void literal() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("pr_null", currentToken.getName())){
            match("pr_null");
        }
        else if(Objects.equals("pr_true", currentToken.getName())){
            match("pr_true");
        }
        else if(Objects.equals("pr_false", currentToken.getName())){
            match("pr_false");
        }
        else if(Objects.equals("Literal int", currentToken.getName())){
            match("Literal int");
        }
        else if(Objects.equals("Literal char", currentToken.getName())){
            match("Literal char");
        }
        else if(Objects.equals("Literal String", currentToken.getName())){
            match("Literal String");
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_null, pr_true, pr_false, Literal int, Literal char o " +
                    "Listeral String");
        }
    }

    private void operando() throws SyntacticErrorException, LexicalErrorException{
        if(Arrays.asList("pr_null","pr_true","pr_false","Literal int","Literal char","Literal String").contains(currentToken.getName())){
            literal();
        }
        else if(Arrays.asList("pr_this","idMetVar","pr_static","pr_new","Parentesis abre").contains(currentToken.getName())){
            acceso();
        }
        else{
             throw new SyntacticErrorException(currentToken," pr_null, pr_true, pr_false, Literal int, " +
                     "Literal char, Literal String, pr_this, idMetVar, pr_static, pr_new o Parentesis abre");
        }
    }

    private void acceso() throws SyntacticErrorException,LexicalErrorException{
        primario();
        encadenado();
    }

    private void primario() throws SyntacticErrorException,LexicalErrorException{
        if(Objects.equals("pr_this",currentToken.getName())){
            accesoThis();
        }
        else if(Objects.equals("idMetVar",currentToken.getName())){
            accesoVarOMetodo();
        }
        else if(Objects.equals("pr_static",currentToken.getName())){
            accesoEstatico();
        }
        else if (Objects.equals("pr_new",currentToken.getName())){
            accesoConstructor();
        }
        else if (Objects.equals("Parentesis abre",currentToken.getName())){
            match("Parentesis abre");
            expresion();
            match("Parentesis cierra");
        }
        else {
            throw new SyntacticErrorException(currentToken, " pr_this, idMetVar, pr_static, pr_new o Parentesis abre");
        }
    }

    private void accesoThis() throws SyntacticErrorException, LexicalErrorException{
        match("pr_this");
    }

    private void accesoVarOMetodo() throws SyntacticErrorException, LexicalErrorException{
        match("idMetVar");
        restoAccesoMetodo();
    }

    private void restoAccesoMetodo() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Parentesis abre",currentToken.getName())){
            argsActuales();
        }
        else {
            // -> e
        }
    }

    private void accesoEstatico() throws SyntacticErrorException, LexicalErrorException{
        match("pr_static");
        match("idClase");
        match("Punto");
        accesoVarOMetodo();
    }

    private void accesoConstructor() throws SyntacticErrorException, LexicalErrorException{
        match("pr_new");
        match("idClase");
        argsActuales();
    }

    private void argsActuales() throws SyntacticErrorException, LexicalErrorException{
        match("Parentesis abre");
        listaExpsOVacio();
        match("Parentesis cierra");
    }

    private void listaExpsOVacio() throws SyntacticErrorException, LexicalErrorException{
        List<String> first = Arrays.asList("Operador suma","Operador resta","Operador not",
                "pr_null","pr_true","pr_false","Literal int","Literal char","Literal String",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre");

        if(first.contains(currentToken.getName())){
            listaExps();
        }
        else{
            // -> e
        }
    }

    private void listaExps() throws SyntacticErrorException, LexicalErrorException{
        expresion();
        restoListaExpsOVacio();
    }

    private void restoListaExpsOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Coma",currentToken.getName())) {
            match("Coma");
            listaExps();
        }
        else {
            // -> e
        }
    }

    private void encadenado() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Punto",currentToken.getName())){
            varOMetodoEncadenado();
            encadenado();
        }
        else{
            // -> e
        }
    }

    private void varOMetodoEncadenado() throws SyntacticErrorException, LexicalErrorException{
        idEncadenado();
    }

    private void idEncadenado() throws SyntacticErrorException, LexicalErrorException{
        match("Punto");
        match("idMetVar");
        argsActualesOVacio();
    }

    private void argsActualesOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Parentesis abre",currentToken.getName())){
            argsActuales();
        }
        else{
            // -> e
        }
    }










}


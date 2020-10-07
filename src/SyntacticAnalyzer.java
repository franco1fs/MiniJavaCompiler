import java.util.*;

public class SyntacticAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;

    public SyntacticAnalyzer(LexicalAnalyzer aLex){
        lexicalAnalyzer = aLex;
        try {
            currentToken = aLex.nextToken();
            inicial();
        }
        catch (LexicalErrorException e){
            // Ver como manejo la exepcion
        }
    }

    private void match(String tokenName){
        if(tokenName.equals(currentToken.getName())) {
            try {
                currentToken = lexicalAnalyzer.nextToken();
            }
            catch (LexicalErrorException e){
                // Ver como manejo la exepcion
            }
        }
        else{
            // throw New SintacticException
        }
    }

    private void inicial(){
        listaClases();
    }

    private void listaClases(){
        clase();
        restoDeClases();
    }

    private void restoDeClases(){
        // Primeros (<Clase>) = {class}
        if(Objects.equals("pr_class", currentToken.getName())){
            clase();
        }
        else{
            // <RestoDeClases> -> e
        }
    }

    private void clase(){
        match("pr_class");
        match("idClase");
        herencia();
        match("Llave abre");
        listaMiembros();
        match("Llave cierra");
    }

    private void herencia(){
        // Pr(<herencia>)= {extends}
        if(Objects.equals("pr_extends", currentToken.getName())){
            match("pr_extends");
            match("idClase");
        }
        else{
            //  herencia -> e
        }
    }

    private void listaMiembros(){
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

    private void miembro(){
        if(Arrays.asList("pr_public","pr_private").contains(currentToken.getName())){
            atributo();
        }
        else if(Collections.singletonList("idClase").contains(currentToken.getName())){
            constructor();
        }
        else if(Arrays.asList( "pr_static","pr_dynamic").contains(currentToken.getName())){
            metodo();
        }
        else{
            // throw new SyntacticExeption ( )
        }
    }

    private void atributo(){
        visibilidad();
        tipo();
        listaDecAtrs();
    }

    private void metodo(){
        formaMetodo();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
        bloque();
    }

    private void constructor(){
        match("idClase");
        argsFormales();
        bloque();
    }

    private void visibilidad(){

        if(Collections.singletonList("pr_public").contains(currentToken.getName())) {
            match("pr_public");
        }
        else if(Collections.singletonList("pr_private").contains(currentToken.getName())) {
            match("pr_private");
        }
        else{
            // throw new SyntacticExeption ( )
        }
        }
    private void tipo(){
        //Primeros (TipoPrimitivo) = {boolean,char,int,string}
        if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipoPrimitivo();
        }
        else if(Objects.equals("icClase", currentToken.getName())) {
            match("idClase");
        }
        else{
            //throw new Syn...
        }
    }

    private void tipoPrimitivo(){
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
            // throw new Syn...
        }
    }

    private void listaDecAtrs(){
        match("idMetVar");
        restoListaDecAtrs();
    }

    private void restoListaDecAtrs(){
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaDecAtrs();
        }
        else {
            // restoListaDecAtrs -> e
        }
    }


    private void formaMetodo(){
        if(Objects.equals("pr_static", currentToken.getName())){
            match("pr_static");
        }
        else if(Objects.equals("pr_dynamic", currentToken.getName())){
            match("pr_dynamic");
        }
        else{
            // throw new Excep...
        }
    }
    private void tipoMetodo(){
        //Pr(tipo) = {boolean,char,int,String,idClase}
        if(Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipo();
        }
        else if(Objects.equals("pr_void", currentToken.getName())){
            match("pr_void");
        }
        else{
            //throw new Ex...
        }
    }

    private void argsFormales(){
        match("Parentesis abre");
        listaArgsFormalesOVacio();
        match("Parentesis cierra");
    }

    private void listaArgsFormalesOVacio(){
        //Pr(listaArgsFormales)= {boolean,int,char,String,idClase}
        if(Arrays.asList("icClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())) {
            listaArgsFormales();
        }
        else{
            //listaArgsFormalesOVacio -> e
        }
    }

    private void listaArgsFormales(){
        argFormal();
        restoArgFormales();
    }

    private void restoArgFormales(){
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaArgsFormales();
        }
        else{
            // restoArgsFormales -> e
        }
    }
    private void argFormal(){
        tipo();
        match("idMetVar");
    }

    private void bloque(){
        match("Llave abre");
        listaSentencias();
        match("Llave cierra");
    }

    private void listaSentencias(){
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

    private void sentencia(){
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
            // throw new Ex...
        }
    }
    private void restoSentenciaElseOVacio(){
        if(Objects.equals("pr_else", currentToken.getName())){
            match("pr_else");
            sentencia();
        }
        else{
            // -> e
        }
    }

    private void asignacionOLlamada(){
        acceso();
        restoAsignacionOVacio();
    }
    private void restoAsignacionOVacio(){
        if(Arrays.asList("Asignacion","Asignacion +","Asignacion -").contains(currentToken.getName())){
            tipoDeAsignacion();
            expresion();
        }
        else{
            // -> e
        }
    }
    private void tipoDeAsignacion() {
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
            //throw new Ex...
        }
    }
    private void listaDecVars(){
        match("idMetVar");
        restoListaVarsOVacio();
    }

    private void restoListaVarsOVacio(){
        if(Objects.equals("Coma", currentToken.getName())) {
            match("Coma");
            listaDecVars();
        }
        else{
            // -> e
        }
    }

    private void expresionOVacio(){
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

    private void expresion(){
        expresionUnaria();
        restoExpresion();
    }

    private void restoExpresion(){
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

    private void operadorBinario(){
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
            // throw new EXCEP...
        }
    }
    private void expresionUnaria(){
        if(Arrays.asList("Operador suma","Operador resta","Operador not").contains(currentToken.getName())) {
            operadorUnario();
            operando();
        }
        else if(Arrays.asList("pr_null","pr_true","pr_false","Literal int","Literal char","Literal String",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre").contains(currentToken.getName())){
            operando();
        }
        else{
            // throw new Exception...
        }
    }

    private void operadorUnario(){
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
            // throw new Ex
        }
    }
    private void literal(){
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
            // throw new Exc...
        }
    }

    private void operando(){

    }

    private void acceso(){

    }


}


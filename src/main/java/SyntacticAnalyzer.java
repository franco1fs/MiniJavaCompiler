import java.util.*;
import symbolTable.*;
import symbolTable.Class;

public class SyntacticAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    private Token nextToken = null;

    private SymbolTable symbolTable;

    public SyntacticAnalyzer(LexicalAnalyzer aLex) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        lexicalAnalyzer = aLex;
        currentToken = aLex.nextToken();
        symbolTable = SymbolTable.getInstance();
        inicial();

    }

    private void match(String tokenName) throws SyntacticErrorException, LexicalErrorException{
        if(tokenName.equals(currentToken.getName())) {
            if(nextToken != null){
                currentToken = nextToken;
                nextToken = null;
            }
            else {
                currentToken = lexicalAnalyzer.nextToken();
            }
        }
        else{
            throw new SyntacticErrorException(currentToken,tokenName);
        }
    }



    private void inicial() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        listaClasesOInterfaces();
        match("EOF");
    }

    private void listaClasesOInterfaces() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("pr_class",currentToken.getName())){
            clase();
            restoDeClasesOInterface();
        }
        else if(Objects.equals("pr_interface",currentToken.getName())){
            encabezadoInterface();
            restoDeClasesOInterface();
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_class o pr_interface");
        }
    }
    private void restoDeClasesOInterface() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Arrays.asList("pr_class","pr_interface").contains(currentToken.getName())) {
            listaClasesOInterfaces();
        }
        else {
            // -> e
        }

    }

    private void encabezadoInterface () throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("pr_interface");
        match("idClase");
        genericidad();
        extendsInterface();
        match("Llave abre");
        listaSignaturaMetodo();
        match("Llave cierra");
    }

    private void extendsInterface() throws SyntacticErrorException, LexicalErrorException{
        if (Objects.equals("pr_extends",currentToken.getName())){
            match("pr_extends");
            match("idClase");
            genericidad();
            restoExtendsInterface();
        }
        else{
            //-> e
        }
    }

    private void restoExtendsInterface() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Coma",currentToken.getName())){
            match("Coma");
            match("idClase");
            genericidad();
            restoExtendsInterface();
        }
        else{
            // -> e
        }
    }

    private void genericidad() throws SyntacticErrorException, LexicalErrorException {
        if(Objects.equals("Operador menor",currentToken.getName())){
            match("Operador menor");
            match("idClase");
            match("Operador mayor");
        }
        else {
            // -> e
        }
    }
    private void listaSignaturaMetodo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Arrays.asList("pr_dynamic","pr_static").contains(currentToken.getName())) {
            signaturaMetodo();
            listaSignaturaMetodo();
        }
        else {
            // -> e
        }
        }

    private void signaturaMetodo() throws SyntacticErrorException,LexicalErrorException, SemanticErrorException{
        formaMetodo();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
        match("Punto y coma");
    }

    private void clase() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("pr_class");
        String className = currentToken.getLexeme();
        int lineNumber = currentToken.getLineNumber();
        match("idClase");

        Class clase = new Class(className,lineNumber);
        symbolTable.setCurrentModule(clase);

        genericidad();

        String ancestorName = herencia();
        clase.setAncestor(ancestorName);

        match("Llave abre");
        listaMiembros();
        match("Llave cierra");
        symbolTable.insertClass(clase);
    }

    private String herencia() throws SyntacticErrorException, LexicalErrorException{
        String inheritanceClassName = "";
        if(Objects.equals("pr_extends", currentToken.getName())){
            match("pr_extends");
            inheritanceClassName = currentToken.getName();
            match("idClase");
            genericidad();
        }
        else{
            //  herencia -> e
            inheritanceClassName = "Object";
        }
        return inheritanceClassName;
    }

    private void listaMiembros() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        List<String> first = Arrays.asList("pr_public","pr_private", "idClase",
                "pr_static","pr_dynamic","pr_boolean","pr_char","pr_int","pr_String");
        if(first.contains(currentToken.getName())){
            miembro();
            listaMiembros();
        }
        else{
            // listaMiembros -> e
        }
    }

    private void predictMemberPath () throws LexicalErrorException, SyntacticErrorException, SemanticErrorException {
        nextToken = lexicalAnalyzer.nextToken();
        if(Objects.equals(nextToken.getName(),"idMetVar")){
            atributo();
        }
        else if(Arrays.asList("Operador menor","Parentesis abre").contains(nextToken.getName())){
            constructor();
        }
        else{
            throw new SyntacticErrorException(nextToken,"Constructor o lista de atributos");
        }
    }



    private void miembro() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Arrays.asList("pr_public","pr_private").contains(currentToken.getName())){
            atributo();
        }
        else if(Objects.equals("idClase",currentToken.getName())){
            predictMemberPath();
        }
        else  if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            atributo();
        }
        else if(Arrays.asList( "pr_static","pr_dynamic").contains(currentToken.getName())){
            metodo();
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_public, pr_private, idClase, pr_static o pr_dynamic");
        }
    }

    private void atributo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException {
        String visibility = "public";
        Type type;
        if (Arrays.asList("pr_public", "pr_private").contains(currentToken.getName())) {
            visibility = visibilidad();
            type = tipo();
            listaDecAtrs(visibility,type);
            match("Punto y coma");
        }
        else if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String","idClase").contains(currentToken.getName())){
            type = tipo();
            listaDecAtrs(visibility,type);
            match("Punto y coma");
        }
        else{
            throw new SyntacticErrorException(currentToken,"declaración de un atributo con o sin visibilidad");
        }
    }
    private void metodo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        String methodForm = formaMetodo();
        MethodType methodType = tipoMetodo();
        String methodName = currentToken.getLexeme();
        int lineNumber =  currentToken.getLineNumber();
        match("idMetVar");

        Class currentClass = (Class) symbolTable.getCurrentModule();

        Method method = new Method(methodName,currentClass,methodType,methodForm,lineNumber);
        currentClass.setCurrentUnit(method);
        argsFormales();
        bloque();

        currentClass.insertMethod(method);
    }

    private void constructor() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        String constructorName = currentToken.getLexeme();
        int line = currentToken.getLineNumber();

        match("idClase");

        Class currentClass = (Class) symbolTable.getCurrentModule();
        Constructor c = new Constructor(constructorName,line,currentClass);
        currentClass.setCurrentUnit(c);

        genericidad();
        argsFormales();
        bloque();
        currentClass.insertConstructor(c);
    }

    private String visibilidad() throws SyntacticErrorException, LexicalErrorException{
        String visibility = "";
        if(Objects.equals("pr_public", currentToken.getName())) {
            visibility = "public";
            match("pr_public");
            return visibility;
        }
        else if(Objects.equals("pr_private", currentToken.getName())) {
            visibility = "private";
            match("pr_private");
            return visibility;
        }
        else{
            throw new SyntacticErrorException(currentToken, " pr_public o pr_private");
        }
        }
    private Type tipo() throws SyntacticErrorException, LexicalErrorException{
        //Primeros (TipoPrimitivo) = {boolean,char,int,string}
        Type type;
        if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            type = tipoPrimitivo();
            return type;
        }
        else if(Objects.equals("idClase", currentToken.getName())) {
            type = new TidClass(currentToken.getLexeme());
            match("idClase");
            return type;
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_boolean, pr_char, pr_int, pr_String o idClase");
        }
    }

    private Type tipoPrimitivo() throws SyntacticErrorException, LexicalErrorException{
        Type type;
        if(Objects.equals("pr_boolean", currentToken.getName())){
            type = new Tboolean("boolean");
            match("pr_boolean");
            return type;
        }
        else if(Objects.equals("pr_char", currentToken.getName())){
            type = new Tchar("char");
            match("pr_char");
            return type;
        }
        else if(Objects.equals("pr_int", currentToken.getName())){
            type = new Tint("int");
            match("pr_int");
            return type;
        }
        else if(Objects.equals("pr_String", currentToken.getName())){
            type = new TString("String");
            match("pr_String");
            return type;
        }
        else{
            throw  new SyntacticErrorException(currentToken,"pr_boolean, pr_char, pr_int o pr_String");
        }
    }

    private void listaDecAtrs(String visibility,Type type) throws SyntacticErrorException, LexicalErrorException,
            SemanticErrorException{
        Attribute attribute = new Attribute(currentToken.getLexeme(),currentToken.getLineNumber(),type,visibility);
        match("idMetVar");

        Class currentClass = (Class) symbolTable.getCurrentModule();
        currentClass.insertAttribute(attribute);

        asignacionOVacio();
        restoListaDecAtrs(visibility,type);
    }

    private void restoListaDecAtrs(String visibility, Type type) throws SyntacticErrorException, LexicalErrorException,
            SemanticErrorException{
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaDecAtrs(visibility,type);
        }
        else {
            // restoListaDecAtrs -> e
        }
    }


    private String formaMetodo() throws SyntacticErrorException,LexicalErrorException{
        String methodForm = "";
        if(Objects.equals("pr_static", currentToken.getName())){
            methodForm = "static";
            match("pr_static");
            return methodForm;
        }
        else if(Objects.equals("pr_dynamic", currentToken.getName())){
            methodForm = "dynamic";
            match("pr_dynamic");
            return methodForm;
        }
        else{
            throw new SyntacticErrorException(currentToken,"pr_satic o pr_dynamic");
        }
    }
    private MethodType tipoMetodo() throws SyntacticErrorException,LexicalErrorException, SemanticErrorException{
        MethodType methodType;
        //Pr(tipo) = {boolean,char,int,String,idClase}
        if(Arrays.asList("idClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            methodType = tipo();
            return methodType;
        }
        else if(Objects.equals("pr_void", currentToken.getName())){
            methodType = new Tvoid();
            match("pr_void");
            return methodType;
        }
        else{
            throw new SyntacticErrorException(currentToken,"pr_boolean, pr_char, pr_int o pr_String, idClase o pr_void");
        }
    }

    private void argsFormales() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("Parentesis abre");
        listaArgsFormalesOVacio();
        match("Parentesis cierra");
    }

    private void listaArgsFormalesOVacio() throws SyntacticErrorException, LexicalErrorException,
            SemanticErrorException{
        //Pr(listaArgsFormales)= {boolean,int,char,String,idClase}
        if(Arrays.asList("idClase","pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())) {
            listaArgsFormales();
        }
        else{
            //listaArgsFormalesOVacio -> e
        }
    }

    private void listaArgsFormales() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        argFormal();
        restoArgFormales();
    }

    private void restoArgFormales() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Coma", currentToken.getName())){
            match("Coma");
            listaArgsFormales();
        }
        else{
            // restoArgsFormales -> e
        }
    }
    private void argFormal() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        Type type = tipo();
        Parameter parameter = new Parameter(currentToken.getLexeme(),currentToken.getLineNumber(),type);
        match("idMetVar");
        Class currentClass = (Class) symbolTable.getCurrentModule();
        //Constructor constructor = (Constructor) currentClass.getCurrentUnit();
        //constructor.insertParameter(parameter);
        Unit unit = currentClass.getCurrentUnit();
        unit.insertParameter(parameter);
    }

    private void bloque() throws SyntacticErrorException, LexicalErrorException{
        match("Llave abre");
        listaSentencias();
        match("Llave cierra");
    }

    private void listaSentencias() throws SyntacticErrorException, LexicalErrorException{
        List<String> firstOfSentence = Arrays.asList("idClase","pr_boolean","pr_char","pr_int","pr_String",
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

    private void predictSentencePath () throws LexicalErrorException, SyntacticErrorException {
        nextToken = lexicalAnalyzer.nextToken();
        if(Objects.equals(nextToken.getName(),"idMetVar")){
            tipo();
            listaDecVars();
            match("Punto y coma");
        }
        else if(Arrays.asList("Operador menor","Punto").contains(nextToken.getName())){
            asignacionOLlamada();
            match("Punto y coma");
        }
        else{
            throw new SyntacticErrorException(nextToken,"Esperaba un acceso estático o lista de Variables");
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
        else if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            tipo();
            listaDecVars();
            match("Punto y coma");
        }
        else if (Objects.equals("idClase", currentToken.getName())){
            predictSentencePath();
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
                    "Parentesis abre, pr_if, idClase, pr_boolean, pr_char, pr_int, pr_String, pr_while, Llave abre, pr_return");
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
        asignacionOVacio();
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

    private void asignacionOVacio() throws SyntacticErrorException, LexicalErrorException{
        if(Objects.equals("Asignacion",currentToken.getName())){
            match("Asignacion");
            expresion();
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
                "pr_this","idMetVar","pr_static","idClase","pr_new","Parentesis abre").contains(currentToken.getName())){
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
        else if(Arrays.asList("pr_this","idMetVar","pr_static","idClase","pr_new","Parentesis abre").contains(currentToken.getName())){
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
        else if(Arrays.asList("pr_static","idClase").contains(currentToken.getName())){
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
        if(Objects.equals("pr_static",currentToken.getName())) {
            match("pr_static");
            match("idClase");
            restoAccesoEstatico();
        }
        else if(Objects.equals("idClase",currentToken.getName())){
            match("idClase");
            restoAccesoEstatico();
        }
        else {
            throw new SyntacticErrorException(currentToken,"Esperaba un acceso Estatico");
        }

    }

    private void restoAccesoEstatico () throws SyntacticErrorException, LexicalErrorException {
        genericidad();
        match("Punto");
        accesoVarOMetodo();
    }
    private void accesoConstructor() throws SyntacticErrorException, LexicalErrorException{
        match("pr_new");
        match("idClase");
        genericidad();
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


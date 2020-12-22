package syntacticAnalyzer;

import java.util.*;

import ast.expression.BinaryExpressionNode;
import ast.expression.ExpressionNode;
import ast.expression.UnaryExpressionNode;
import ast.expression.operating.*;
import ast.sentence.*;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.LexicalErrorException;
import lexicalAnalyzer.Token;
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
        symbolTable.reLoadSymbolTable();
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

        implementsInterfaces();

        match("Llave abre");
        listaMiembros();
        match("Llave cierra");
        symbolTable.insertClass(clase);
    }

    private void implementsInterfaces() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException {
        if(Objects.equals("pr_implements",currentToken.getName())){
            match("pr_implements");
            match("idClase");
            restoInterfaces();
        }
        else {
            // -> e
        }
    }

    private void restoInterfaces() throws SyntacticErrorException, LexicalErrorException , SyntacticErrorException {
        if(Objects.equals("Coma",currentToken.getName())){
            match("Coma");
            match("idClase");
            restoInterfaces();
        }
        else{
            // - > e
        }
    }
    private String herencia() throws SyntacticErrorException, LexicalErrorException{
        String inheritanceClassName = "";
        if(Objects.equals("pr_extends", currentToken.getName())){
            match("pr_extends");
            inheritanceClassName = currentToken.getLexeme();
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
        else if(Arrays.asList("pr_static", "pr_dynamic").contains(currentToken.getName())){
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
            estatico();
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

    private void estatico() throws SyntacticErrorException, LexicalErrorException, SyntacticErrorException {
        if(Objects.equals("pr_static",currentToken.getName())){
            match("pr_static");
        }
        else{
            // -> e
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
        BlockNode blockNode = bloque(null);
        method.setMyBlock(blockNode);
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
        BlockNode blockNode = bloque(null);
        c.setMyBlock(blockNode);
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
            type = new TidClass(currentToken.getLexeme(),currentToken.getLineNumber());
            match("idClase");
            genericidad();
            return type;
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_boolean, pr_char, pr_int, pr_String o idClase");
        }
    }

    private Type tipoPrimitivo() throws SyntacticErrorException, LexicalErrorException{
        Type type;
        if(Objects.equals("pr_boolean", currentToken.getName())){
            type = new Tboolean("boolean",currentToken.getLineNumber());
            match("pr_boolean");
            return type;
        }
        else if(Objects.equals("pr_char", currentToken.getName())){
            type = new Tchar("char",currentToken.getLineNumber());
            match("pr_char");
            return type;
        }
        else if(Objects.equals("pr_int", currentToken.getName())){
            type = new Tint("int",currentToken.getLineNumber());
            match("pr_int");
            return type;
        }
        else if(Objects.equals("pr_String", currentToken.getName())){
            type = new TString("String",currentToken.getLineNumber());
            match("pr_String");
            return type;
        }
        else{
            throw  new SyntacticErrorException(currentToken,"pr_boolean, pr_char, pr_int o pr_String");
        }
    }

    private void listaDecAtrs(String visibility,Type type) throws SyntacticErrorException, LexicalErrorException,
            SemanticErrorException{
        Attribute attribute = new Attribute(currentToken.getLexeme(),currentToken.getLineNumber(),
                type,visibility,symbolTable.getCurrentModule().getName());

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
            methodType = new Tvoid(currentToken.getLineNumber());
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

    private BlockNode bloque(BlockNode fatherBlock) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber= currentToken.getLineNumber();
        Class currentClass = (Class) symbolTable.getCurrentModule();
        BlockNode blockNode = new BlockNode(fatherBlock, currentClass.getCurrentUnit());
        symbolTable.setCurrentBlock(blockNode);
        match("Llave abre");
        ArrayList<SentenceNode> sentenceNodes= new ArrayList<SentenceNode>();
        listaSentencias(sentenceNodes,blockNode);
        match("Llave cierra");
        blockNode.setSentences(sentenceNodes);

        symbolTable.setCurrentBlock(blockNode.getFatherBlock());

        return blockNode;

    }

    private void listaSentencias(ArrayList<SentenceNode> sentenceNodes,BlockNode fatherBlock)
            throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        List<String> firstOfSentence = Arrays.asList("idClase","pr_boolean","pr_char","pr_int","pr_String",
                "Llave abre","Punto y coma","pr_if","pr_while","pr_return",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre");

        if(firstOfSentence.contains(currentToken.getName())){
            SentenceNode sentenceNode = sentencia(fatherBlock);
            sentenceNodes.add(sentenceNode);
            listaSentencias(sentenceNodes,symbolTable.getCurrentBlock());
        }
        else{
            //listaSentencias -> e
        }
    }

    private SentenceNode predictSentencePath (BlockNode myBlock) throws LexicalErrorException, SyntacticErrorException, SemanticErrorException{
        nextToken = lexicalAnalyzer.nextToken();
        Class currentClass = (Class) symbolTable.getCurrentModule();
        if(Objects.equals(nextToken.getName(),"idMetVar")){
            Type type = tipo();
            ArrayList<String> vars = new ArrayList<String>();
            listaDecVars(vars);
            match("Punto y coma");
            DecVarsNode decVarsNode = new DecVarsNode(vars,type,myBlock);
            return decVarsNode;
        }
        else if(Arrays.asList("Operador menor","Punto").contains(nextToken.getName())){
            SentenceNode sentenceNode = asignacionOLlamada();
            match("Punto y coma");
            return sentenceNode;
        }
        else{
            throw new SyntacticErrorException(nextToken,"Esperaba un acceso estático o lista de Variables");
        }
    }


    private SentenceNode sentencia(BlockNode myBlock) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber;
        lineNumber = currentToken.getLineNumber();
        Class currentClass = (Class) symbolTable.getCurrentModule();
        if(Objects.equals("Punto y coma", currentToken.getName())){
            match("Punto y coma");
            SemicolonNode semicolonNode = new SemicolonNode();
            semicolonNode.setLineNumber(lineNumber);

            return semicolonNode;
        }
        else if(Arrays.asList("pr_this","idMetVar","pr_static","pr_new","Parentesis abre").contains(currentToken.getName())){
            SentenceNode sentenceNode = asignacionOLlamada();
            match("Punto y coma");
            sentenceNode.setLineNumber(lineNumber);

            return sentenceNode;
        }
        else if(Arrays.asList("pr_boolean","pr_char","pr_int","pr_String").contains(currentToken.getName())){
            Type type = tipo();
            ArrayList<String> vars = new ArrayList<String>();
            listaDecVars(vars);
            match("Punto y coma");
            DecVarsNode decVarsNode = new DecVarsNode(vars,type,myBlock);
            decVarsNode.setLineNumber(lineNumber);

            return decVarsNode;
        }
        else if (Objects.equals("idClase", currentToken.getName())){
            SentenceNode sentenceNode = predictSentencePath(myBlock);
            sentenceNode.setLineNumber(lineNumber);

            return sentenceNode;
        }
        else if(Objects.equals("pr_if", currentToken.getName())){
            match("pr_if");
            match("Parentesis abre");
            ExpressionNode expressionNode = expresion();
            match("Parentesis cierra");
            SentenceNode sentenceIfNode=sentencia(myBlock);
            SentenceNode sentenceElseNode = restoSentenciaElseOVacio(myBlock);
            IfElseNode ifElseNode = new IfElseNode(expressionNode,sentenceIfNode,sentenceElseNode);
            ifElseNode.setLineNumber(lineNumber);

            return ifElseNode;
        }
        else if(Objects.equals("pr_while", currentToken.getName())){
            match("pr_while");
            match("Parentesis abre");
            ExpressionNode expressionNode = expresion();
            match("Parentesis cierra");
            SentenceNode sentenceNode=sentencia(myBlock);
            WhileNode whileNode = new WhileNode(expressionNode,sentenceNode);
            whileNode.setLineNumber(lineNumber);

            return whileNode;
        }
        else if(Objects.equals("Llave abre", currentToken.getName())){
            return bloque(myBlock);

        }
        else if(Objects.equals("pr_return", currentToken.getName())){
            match("pr_return");
            ExpressionNode expressionNode = expresionOVacio();
            match("Punto y coma");
            return new ReturnNode(expressionNode, currentClass,currentClass.getCurrentUnit(),lineNumber);
        }
        else{
            throw new SyntacticErrorException(currentToken,"Punto y coma, pr_this, idMetVar, pr_static ,pr_new ," +
                    "Parentesis abre, pr_if, idClase, pr_boolean, pr_char, pr_int, pr_String, pr_while, Llave abre, pr_return");
        }
    }
    private SentenceNode restoSentenciaElseOVacio(BlockNode fatherBlock) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("pr_else", currentToken.getName())){
            match("pr_else");
            return sentencia(fatherBlock);
        }
        else{
            // -> e
            return null;
        }
    }

    private SentenceNode asignacionOLlamada() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException {
        int lineNumber = currentToken.getLineNumber();
        AccessNode accessNode = acceso();
        SentenceNode sentenceNode = restoAsignacionOVacio(accessNode);
        sentenceNode.setLineNumber(lineNumber);
        return sentenceNode;
    }
    private SentenceNode restoAsignacionOVacio(AccessNode accessNode) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Arrays.asList("Asignacion","Asignacion +","Asignacion -").contains(currentToken.getName())){
            String assignmentType=tipoDeAsignacion();
            ExpressionNode expressionNode=expresion();
            return new AssignmentNode(accessNode,expressionNode,assignmentType,symbolTable.getCurrentBlock());
        }
        else{
            // -> e
            return new CallNode(accessNode);
        }
    }
    private String tipoDeAsignacion() throws SyntacticErrorException, LexicalErrorException{
        String assignmentType;
        assignmentType = currentToken.getLexeme();
        if (Objects.equals("Asignacion", currentToken.getName())) {
            match("Asignacion");
            return assignmentType;
        }
        else if (Objects.equals("Asignacion +", currentToken.getName())){
            match("Asignacion +");
            return assignmentType;
        }
        else if (Objects.equals("Asignacion -", currentToken.getName())){
            match("Asignacion -");
            return assignmentType;
        }
        else{
            throw new SyntacticErrorException(currentToken,"Asignacion, Asignacion + o Asignacion -");
        }
    }
    private void listaDecVars(ArrayList<String> vars) throws SyntacticErrorException,LexicalErrorException, SemanticErrorException{
        vars.add(currentToken.getLexeme());
        match("idMetVar");
        asignacionOVacio();
        restoListaVarsOVacio(vars);
    }

    private void restoListaVarsOVacio(ArrayList<String> vars) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Coma", currentToken.getName())) {
            match("Coma");
            listaDecVars(vars);
        }
        else{
            // -> e
        }
    }

    private void asignacionOVacio() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Asignacion",currentToken.getName())){
            match("Asignacion");
            expresion();
        }
        else{
            // -> e
        }
    }

    private ExpressionNode expresionOVacio() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException {
        List<String> firstOfExpOrEmp = Arrays.asList("pr_this","idMetVar","pr_static","pr_new","Parentesis abre",
                "Operador suma","Operador resta","Operador not",
                "pr_null","pr_true","pr_false","Literal int","Literal char","Literal String");

        if(firstOfExpOrEmp.contains(currentToken.getName())){
            return expresion();
        }
        else{
            // -> e
            return null;
        }
    }

    private ExpressionNode expresion() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        UnaryExpressionNode unaryExpressionNode = expresionUnaria();
        return restoExpresion(unaryExpressionNode);
    }

    private ExpressionNode restoExpresion(ExpressionNode leftExpressionNode) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        List<String> firstOfBinOp = Arrays.asList("Operador suma","Operador resta","Operador multiplicacion","Operador modulo",
               "Operador division","Operador OR","Operador AND","Operador mayor" , "Operador menor", "Operador mayor o igual",
                "Operador menor o igual","Operador distinto","Operador comparacion");

        if(firstOfBinOp.contains(currentToken.getName())){
            int lineNumber = currentToken.getLineNumber();
            String binaryOperator = operadorBinario();
            UnaryExpressionNode rightExpressionNode = expresionUnaria();
            BinaryExpressionNode binaryExpressionNode = new BinaryExpressionNode(leftExpressionNode,rightExpressionNode,binaryOperator,
                    lineNumber);
            return restoExpresion(binaryExpressionNode);
        }
        else{
            // -> e
            return leftExpressionNode;
        }
    }

    private String operadorBinario() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        String binaryOperator;
        if(Objects.equals("Operador comparacion", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador comparacion");
        }
        else  if(Objects.equals("Operador distinto", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador distinto");
        }
        else  if(Objects.equals("Operador menor", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador menor");
        }
        else if(Objects.equals("Operador mayor", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador mayor");
        }
        else if(Objects.equals("Operador menor o igual", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador menor o igual");
        }
        else if(Objects.equals("Operador mayor o igual", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador mayor o igual");
        }
        else if(Objects.equals("Operador suma", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador suma");
        }
        else if(Objects.equals("Operador resta", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador resta");
        }
        else if(Objects.equals("Operador multiplicacion", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador multiplicacion");
        }
        else if(Objects.equals("Operador modulo", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador modulo");
        }
        else if(Objects.equals("Operador division", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador division");
        }
        else if(Objects.equals("Operador OR", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador OR");
        }
        else if(Objects.equals("Operador AND", currentToken.getName())){
            binaryOperator = currentToken.getLexeme();
            match("Operador AND");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Operador comparacion, Operador distinto, Operador menor," +
                    "Operador mayor, Operador menor o igual, Operador mayor o igual, Operador suma," +
                    "Operador resta, Operador multiplicacion, Operador modulo, Operador division," +
                    "Operador OR o Operador AND");
        }
        return binaryOperator;
    }
    private UnaryExpressionNode expresionUnaria() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber = currentToken.getLineNumber();
        OperantNode operantNode;
        String operator = null;
        if(Arrays.asList("Operador suma","Operador resta","Operador not").contains(currentToken.getName())) {
             operator = operadorUnario();
             operantNode = operando();
        }
        else if(Arrays.asList("pr_null","pr_true","pr_false","Literal int","Literal char","Literal String",
                "pr_this","idMetVar","pr_static","idClase","pr_new","Parentesis abre").contains(currentToken.getName())){
            operantNode = operando();
        }
        else{
            throw new SyntacticErrorException(currentToken, "Operador suma, Operador resta, Operador not,"+
                    "pr_null, pr_true, pr_false, Literal int, Literal char, Literal String, "+
                    "pr_this, idMetVar, pr_static ,pr_new o Parentesis abre");
        }
        UnaryExpressionNode unaryExpressionNode = new UnaryExpressionNode(operantNode,lineNumber);
        if(operator!=null){
            unaryExpressionNode.setUnaryOperator(operator);
        }
        return unaryExpressionNode;
    }

    private String operadorUnario() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        String operator;
        if(Objects.equals("Operador suma", currentToken.getName())){
            operator = currentToken.getLexeme();
            match("Operador suma");
        }
        else if(Objects.equals("Operador resta", currentToken.getName())){
            operator = currentToken.getLexeme();
            match("Operador resta");
        }
        else if(Objects.equals("Operador not", currentToken.getName())){
            operator = currentToken.getLexeme();
            match("Operador not");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Operador suma, Operador resta o Operador not");
        }
        return operator;
    }
    private LiteralNode literal() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("pr_null", currentToken.getName())){
            match("pr_null");
            return new LiteralNode(new Tnull("null",currentToken.getLineNumber()),"null");
        }
        else if(Objects.equals("pr_true", currentToken.getName())){
            String val = currentToken.getLexeme();
            match("pr_true");
            return new LiteralNode( new Tboolean("boolean",currentToken.getLineNumber()),val);
        }
        else if(Objects.equals("pr_false", currentToken.getName())){
            String val = currentToken.getLexeme();
            match("pr_false");
            return new LiteralNode( new Tboolean("boolean",currentToken.getLineNumber()),val);
        }
        else if(Objects.equals("Literal int", currentToken.getName())){
            String val = currentToken.getLexeme();
            match("Literal int");
            return new LiteralNode( new Tint("int",currentToken.getLineNumber()),val);
        }
        else if(Objects.equals("Literal char", currentToken.getName())){
            String val = currentToken.getLexeme();
            match("Literal char");
            return new LiteralNode( new Tchar("char",currentToken.getLineNumber()),val);
        }
        else if(Objects.equals("Literal String", currentToken.getName())){
            String val = currentToken.getLexeme();
            match("Literal String");
            return new LiteralNode( new Tint("String",currentToken.getLineNumber()),val);
        }
        else{
            throw new SyntacticErrorException(currentToken, "pr_null, pr_true, pr_false, Literal int, Literal char o " +
                    "Listeral String");
        }
    }

    // Esta harcodeado para poder realizar primero la expresion
    private OperantNode operando() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Arrays.asList("pr_null","pr_true","pr_false","Literal int","Literal char","Literal String").contains(currentToken.getName())){
            return literal();
        }
        else if(Arrays.asList("pr_this","idMetVar","pr_static","idClase","pr_new","Parentesis abre").contains(currentToken.getName())){
            return acceso();
        }
        else{
             throw new SyntacticErrorException(currentToken," pr_null, pr_true, pr_false, Literal int, " +
                     "Literal char, Literal String, pr_this, idMetVar, pr_static, pr_new o Parentesis abre");
        }
    }

    private AccessNode acceso() throws SyntacticErrorException,LexicalErrorException, SemanticErrorException{
        PrimaryNode primaryNode = primario();
        ArrayList<ChainCall> chainCallArrayList = new ArrayList<ChainCall>();
        encadenado(chainCallArrayList);
        ChainCallContainer chainCallContainer = new ChainCallContainer(chainCallArrayList,
                SymbolTable.getInstance().getCurrentModule());


        return new AccessNode(primaryNode,chainCallContainer);


    }

    private PrimaryNode predictAccessPath () throws LexicalErrorException, SyntacticErrorException, SemanticErrorException{
        nextToken = lexicalAnalyzer.nextToken();
        if(Objects.equals(nextToken.getName(),"Parentesis abre")){
            return accesoMetodo();
        }
        else{
            return accesoVar();
        }

    }

    private PrimaryNode primario() throws SyntacticErrorException,LexicalErrorException, SemanticErrorException{
        int lineNumber;
        if(Objects.equals("pr_this",currentToken.getName())){
            return accesoThis();
        }
        else if(Objects.equals("idMetVar",currentToken.getName())){
            //modifique
            return predictAccessPath();
        }
        else if(Arrays.asList("pr_static","idClase").contains(currentToken.getName())){
            return accesoEstatico();
        }
        else if (Objects.equals("pr_new",currentToken.getName())){
            return accesoConstructor();
        }
        else if (Objects.equals("Parentesis abre",currentToken.getName())){
            lineNumber = currentToken.getLineNumber();
            match("Parentesis abre");
            ExpressionNode expressionNode = expresion();
            match("Parentesis cierra");
            return new ParentOperantExpressionNode(expressionNode,lineNumber);
        }
        else {
            throw new SyntacticErrorException(currentToken, " pr_this, idMetVar, pr_static, pr_new o Parentesis abre");
        }
    }

    private AccessThisNode accesoThis() throws SyntacticErrorException, LexicalErrorException{
        int lineNumber = currentToken.getLineNumber();
        match("pr_this");
        Class classe = (Class) symbolTable.getCurrentModule();
        Unit unit = classe.getCurrentUnit();
        //Method method = null;
        //if( unit instanceof Method)
            //method = (Method) unit;
        return new AccessThisNode(lineNumber,unit);
    }

    private AccessVarNode accesoVar() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber = currentToken.getLineNumber();
        String var = currentToken.getLexeme();
        match("idMetVar");
        return new AccessVarNode(var,lineNumber,symbolTable.getCurrentBlock());
    }

    private AccessMethodNode accesoMetodo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber = currentToken.getLineNumber();
        String method = currentToken.getLexeme();
        match("idMetVar");
        //Tomar la lista que devuelve args actuales y agregarla al constructor de retorno
        ArrayList<ExpressionNode> args = argsActuales();
        Class myClass = (Class) symbolTable.getCurrentModule();
        Unit unit = myClass.getCurrentUnit();
        if( unit instanceof Method)
            return new AccessMethodNode(args,method,myClass,lineNumber,(Method) myClass.getCurrentUnit());
        else{
            return new AccessMethodNode(args,method,myClass,lineNumber);
        }
    }

    /**
    private void accesoVarOMetodo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("idMetVar");
        restoAccesoMetodo();
        //if restoAccesoMetodo() == null
        // new NodoVAr else new NodoMethod(Lista args)
    }

    private void restoAccesoMetodo() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Parentesis abre",currentToken.getName())){
            argsActuales();
        }
        else {
            // -> e
        }
    }
     **/

    private AccessStaticNode accesoEstatico() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        String idClass;
        AccessMethodNode accessMethodNode;
        int lineNumber = currentToken.getLineNumber();
        if(Objects.equals("pr_static",currentToken.getName())) {
            match("pr_static");
            idClass = currentToken.getLexeme();
            match("idClase");
            accessMethodNode = restoAccesoEstatico();
        }
        else if(Objects.equals("idClase",currentToken.getName())){
            idClass = currentToken.getLexeme();
            match("idClase");
            accessMethodNode = restoAccesoEstatico();
        }
        else {
            throw new SyntacticErrorException(currentToken,"Esperaba un acceso Estatico");
        }
        return new AccessStaticNode(idClass,accessMethodNode,lineNumber);

    }

    private AccessMethodNode restoAccesoEstatico () throws SyntacticErrorException, LexicalErrorException, SemanticErrorException {
        genericidad();
        match("Punto");
        return accesoMetodo();
    }
    private AccessConstructorNode accesoConstructor() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        int lineNumber = currentToken.getLineNumber();
        match("pr_new");
        String className = currentToken.getLexeme();
        match("idClase");
        genericidadConstructor();
        ArrayList<ExpressionNode> args = argsActuales();
        return new AccessConstructorNode(args,className,lineNumber);
    }

    private void genericidadConstructor() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Operador menor",currentToken.getName())){
            match("Operador menor");
            restoGenerecidadConstructor();
        }
        else {
            // -> e
        }
    }

    private void restoGenerecidadConstructor() throws SyntacticErrorException, SemanticErrorException, LexicalErrorException{
        if(Objects.equals("idClase",currentToken.getName())){
            match("idClase");
            match("Operador mayor");
        }
        else if(Objects.equals("Operador mayor",currentToken.getName())){
            match("Operador mayor");
        }
        else{
            throw new SyntacticErrorException(currentToken,"Esperaba Genericidad en el llamado a un constructor");
        }
    }

    private ArrayList<ExpressionNode> argsActuales() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("Parentesis abre");
        ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
        listaExpsOVacio(args);
        match("Parentesis cierra");
        return args;
    }

    private void listaExpsOVacio(ArrayList<ExpressionNode> args) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        List<String> first = Arrays.asList("Operador suma","Operador resta","Operador not",
                "pr_null","pr_true","pr_false","Literal int","Literal char","Literal String",
                "pr_this","idMetVar","pr_static","pr_new","Parentesis abre");

        if(first.contains(currentToken.getName())){
            listaExps(args);
        }
        else{
            // -> e
        }
    }

    private void listaExps(ArrayList<ExpressionNode> args) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        ExpressionNode expressionNode = expresion();
        args.add(expressionNode);
        restoListaExpsOVacio(args);
    }

    private void restoListaExpsOVacio(ArrayList<ExpressionNode> args) throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Coma",currentToken.getName())) {
            match("Coma");
            listaExps(args);
        }
        else {
            // -> e
        }
    }

    private void encadenado(ArrayList<ChainCall> chainCallArrayList) throws SyntacticErrorException, LexicalErrorException,SemanticErrorException{
        if(Objects.equals("Punto",currentToken.getName())){
            ChainCall chainCall = varOMetodoEncadenado();
            chainCallArrayList.add(chainCall);
            encadenado(chainCallArrayList);
        }
        else{
            // -> e
        }
    }

    private ChainCall varOMetodoEncadenado() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        return idEncadenado();
    }

    private ChainCall idEncadenado() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        match("Punto");
        String idMetVar = currentToken.getLexeme();
        int lineNumber = currentToken.getLineNumber();
        match("idMetVar");
        ArrayList<ExpressionNode> args = argsActualesOVacio();
        if(args==null){
            return new VarChainCall(idMetVar,lineNumber);
        }
        else {
            return new MethodChainCall(args,idMetVar,lineNumber);
        }
    }

    private ArrayList<ExpressionNode> argsActualesOVacio() throws SyntacticErrorException, LexicalErrorException, SemanticErrorException{
        if(Objects.equals("Parentesis abre",currentToken.getName())){
            return argsActuales();
        }
        else{
            // -> e
            return null;
        }
    }

}


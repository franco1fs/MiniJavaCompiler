package symbolTable;

import ast.sentence.BlockNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SymbolTable {

    private static final SymbolTable myInstance = new SymbolTable();
    private HashMap<String,Class> classes;
    private Module currentModule;
    private ArrayList<String> orderOfClases = new ArrayList<String>();

    private BlockNode currentBlock;

    private File outputFile;
    private FileWriter fileWriter;

    private int indexLabel = 0;


    public static SymbolTable getInstance(){
        return myInstance;
    }

    private SymbolTable(){
        classes = new HashMap<String, Class>();
        loadBasicModules();
    }

    public void setAndCreateFileWriter(String outputPath){

        try{
            outputFile = new File(outputPath);
            fileWriter = new FileWriter(outputFile);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void closeFile(){
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genInstruction(String code){
        try {
            fileWriter.write(code+'\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBasicModules(){

        try {
        Class object = new Class("Object",0,null);

        object.insertConstructor(new Constructor("Object",0,object));
        classes.put("Object",object);

        Class system = new Class("System",0,"Object");
        system.insertConstructor(new Constructor("System",0,object));


        Type integer = new Tint("int",0);
        Type booleano = new Tboolean("boolean",0);
        Type caracter = new Tchar("char",0);
        Type cadena = new TString("String",0);
        Tvoid tVoid = new Tvoid(0);



            Method m1 = new Method("read", system, integer, "static", 0);
            system.insertMethod(m1);

            Method m2 = new Method("printB",system,tVoid,"static",0);
            Parameter p = new Parameter("b",0,booleano);
            m2.insertParameter(p);
            system.insertMethod(m2);

            Method m3 = new Method("printC",system,tVoid,"static",0);
            Parameter p2 = new Parameter("c",0,caracter);
            m3.insertParameter(p2);
            system.insertMethod(m3);

            Method m4 = new Method("printI",system,tVoid,"static",0);
            Parameter p3 = new Parameter("i",0,integer);
            m4.insertParameter(p3);
            system.insertMethod(m4);

            Method m5 = new Method("printS",system,tVoid,"static",0);
            Parameter p4 = new Parameter("s",0,cadena);
            m5.insertParameter(p4);
            system.insertMethod(m5);

            Method m6 = new Method("println",system,tVoid,"static",0);
            system.insertMethod(m6);

            Method m7 = new Method("printBln",system,tVoid,"static",0);
            Parameter p5 = new Parameter("b",0,booleano);
            m7.insertParameter(p5);
            system.insertMethod(m7);

            Method m8 = new Method("printCln",system,tVoid,"static",0);
            Parameter p6 = new Parameter("c",0,caracter);
            m8.insertParameter(p6);
            system.insertMethod(m8);

            Method m9 = new Method("printIln",system,tVoid,"static",0);
            Parameter p7 = new Parameter("i",0,integer);
            m9.insertParameter(p7);
            system.insertMethod(m9);

            Method m10 = new Method("printSln",system,tVoid,"static",0);
            Parameter p8 = new Parameter("s",0,cadena);
            m10.insertParameter(p8);
            system.insertMethod(m10);

            classes.put("System",system);
        }
        //Nunca deberia ocurrir esta exception a no ser un error de tipeo
        catch (SemanticErrorException e){
            System.out.println("Exception cargando los datos basicos de Object y System (Error de escritura)");
        }
    }

    public void reLoadSymbolTable(){
        classes = new HashMap<String, Class>();
        loadBasicModules();
        currentModule = null;
        orderOfClases = new ArrayList<String>();
    }

    public void insertClass(Class c) throws SemanticErrorException{
        if(classes.containsKey(c.name)){
            throw new SemanticErrorException(c.getName(),c.getLineNumber(),"Error en la linea: "+c.getLineNumber()
            +" existen dos clases con el mismo nombre");
        }

        classes.put(c.getName(),c);
        orderOfClases.add(c.getName());

         }

    public void setCurrentModule(Module module){
        this.currentModule = module;
    }
    public Module getCurrentModule(){
        return currentModule;
    }

    public HashMap<String,Class> getClasses(){
        return classes;
    }

    public void checkClassesDeclarationAndConsolidationTable() throws SemanticErrorException{
        ArrayList<Method> allMethods = new ArrayList<Method>();
        for (String c: orderOfClases){
            classes.get(c).checkCorrectDeclaration();
            allMethods.addAll(classes.get(c).getMyMethods().values());
        }
        if(noExistMainOrExistMoreThanOne(allMethods)){
            throw new SemanticErrorException("main",0,"Error semantico en el programa debido al metodo main");
        }
        }

        public void consolidateOffsets(){
            for (String c: orderOfClases) {
                classes.get(c).consolidateOffsets();
            }
        }

        public void checkSentences() throws SemanticErrorException {
            for (String c: orderOfClases){
                classes.get(c).checkSentences();
            }
        }
        private boolean noExistMainOrExistMoreThanOne(ArrayList<Method> allMethods){
            boolean toRet = true;
            int mainCount = 0;
            Method method;
            for (int i= 0 ; i<allMethods.size(); i++){
                method = allMethods.get(i);
                if(method.getName().equals("main") && method.getReturnType().getTypeName().equals("void") &&
                    method.getParameters().size() == 0){
                    mainCount++;
                    if(mainCount==1){
                        toRet = false;
                    }
                    if(mainCount>1){
                        toRet = true;
                        break;
                    }
                }
            }
            return toRet;
        }

        public void setCurrentBlock(BlockNode blockNode){
            this.currentBlock = blockNode;
        }
        public BlockNode getCurrentBlock(){
            return  currentBlock;
        }

        public Class getClass(String className){
            return classes.get(className);
        }

        public String getLabel(){
            String label = "etiqueta"+indexLabel;
            indexLabel++;
            return label;
        }

        //Deberia llamarse previamente a generar
        public void initializeCIVM(){

                genInstruction(".CODE");

                genInstruction("PUSH simple_heap_init");
                genInstruction("CALL");
                genInstruction("PUSH main" );
                genInstruction("CALL");
                genInstruction("HALT" );

                //CODIGO SIMPLE HEAP INIT DE LA CATEDRA
                genInstruction("simple_heap_init: ");
                genInstruction("RET 0	; Retorna inmediatamente");

                //CODIGO SIMPLE MALLOC DE LA CATEDRA
                genInstruction("simple_malloc: ");
                genInstruction("LOADFP ; inicializa RA" );
                genInstruction("LOADSP ");
                genInstruction("STOREFP ; Fin ini RA");
                genInstruction("LOADHL ; hl");
                genInstruction("DUP ; hl");
                genInstruction("PUSH 1; 1");
                genInstruction("ADD ; hl+1" );
                genInstruction("STORE 4; Guarda el resultado (un puntero a la primer celda de la región de memoria)");
                genInstruction("LOAD 3; Carga la cantidad de celdas a alojar (parámetro que debe ser positivo)" );
                genInstruction("ADD" );
                genInstruction("STOREHL ;  agranda el heap");
                genInstruction("STOREFP ");
                genInstruction("RET 1 ; Retorna eliminando el parámetro");

                generateSystemClassCode();

        }

        private void generateSystemClassCode(){
            genInstruction(".CODE" );

            genInstruction("system_read: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("READ");
            genInstruction("PUSH 48");
            genInstruction("SUB");
            genInstruction("STORE 3");
            genInstruction("STOREFP");
            genInstruction("RET 0");

            genInstruction("system_printi: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("IPRINT");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printiln: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("IPRINT");
            genInstruction("PRNLN");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printb: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("BPRINT");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printbln:");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("BPRINT");
            genInstruction("PRNLN");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printc: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("CPRINT");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printcln:");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("CPRINT");
            genInstruction("PRNLN");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_prints: ");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("SPRINT");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_printsln:");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("LOAD 3");
            genInstruction("SPRINT");
            genInstruction("PRNLN");
            genInstruction("STOREFP");
            genInstruction("RET 1");

            genInstruction("system_println:");
            genInstruction("LOADFP");
            genInstruction("LOADSP");
            genInstruction("STOREFP");
            genInstruction("PRNLN");
            genInstruction("STOREFP");
            genInstruction("RET 0");
        }

        public void generateCode(){
            for(Class classe : classes.values()){
                classe.generate();
            }
        }
}


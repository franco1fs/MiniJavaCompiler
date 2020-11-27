package symbolTable;

import ast.sentence.BlockNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SymbolTable {

    private static final SymbolTable myInstance = new SymbolTable();
    private HashMap<String,Class> classes;
    private Module currentModule;
    private ArrayList<String> orderOfClases = new ArrayList<String>();

    private BlockNode currentBlock;


    public static SymbolTable getInstance(){
        return myInstance;
    }

    private SymbolTable(){
        classes = new HashMap<String, Class>();
        loadBasicModules();
    }

    private void loadBasicModules(){
        Class object = new Class("Object",0,null);

        classes.put("Object",object);

        Class system = new Class("System",0,"Object");

        Type integer = new Tint("int",0);
        Type booleano = new Tboolean("boolean",0);
        Type caracter = new Tchar("char",0);
        Type cadena = new TString("String",0);
        Tvoid tVoid = new Tvoid(0);


        try {
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
}


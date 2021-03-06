package symbolTable;

import ast.sentence.BlockNode;

import java.util.*;

public class Class extends Module{

    private Unit currentUnit;
    private String ancestor = null;

    private HashMap<String,Method> myMethods;
    private HashMap<String,Attribute> myAtributes;

    //La idea de este mapeo es almacenar una tabla con el nombre de la clase Super
    // y la lista de variables de instancia de esa clase que heredamos y al haber redefinido en nuestro modulo
    //son tapadas
    private HashMap<String,ArrayList<Attribute>> hideAttrs = new HashMap<String, ArrayList<Attribute>>();


    private Constructor constructor= null;

    private ArrayList<String> orderOfAttributes = new ArrayList<String>();
    private ArrayList<String> orderOfMethods = new ArrayList<String>();

    private boolean areOffsetsConsolidate = false;

    private ArrayList<Method> inheritMethods = new ArrayList<Method>();

    public Class(String name,int lineNumber){
        this.name = name;
        this.lineNumber = lineNumber;
        myMethods = new HashMap<String, Method>();
        myAtributes = new HashMap<String, Attribute>();
        this.symbolTable = SymbolTable.getInstance();
    }

    public Class(String name,int lineNumber,String inheritanceClass){
        this.name = name;
        this.lineNumber = lineNumber;
        myMethods = new HashMap<String, Method>();
        myAtributes = new HashMap<String, Attribute>();
        this.ancestor = inheritanceClass;
        this.symbolTable = SymbolTable.getInstance();
    }

    public void setAncestor(String ancestor){
        this.ancestor = ancestor;
    }

    public String getAncestor(){
        return this.ancestor;
    }

    public void setCurrentUnit(Unit unit){
        this.currentUnit = unit;
    }

    public Unit getCurrentUnit(){
        return currentUnit;
    }

    //Podria lanzar una Exception
    public void insertMethod(Method method) throws SemanticErrorException{
        if(myMethods.containsKey(method.getName())){
            throw new SemanticErrorException(method.getName(),method.getLineNumber(),"Error Semantico en la linea:"+
                    method.getLineNumber()+"La clase "+name+" tiene metodos" +
                    "con nombres repetidos");
        }
        myMethods.put(method.getName(),method);
        orderOfMethods.add(method.getName());

    }

    public void insertConstructor(Constructor c) throws SemanticErrorException{
        if(constructor != null){
            throw new SemanticErrorException(c.getName(),c.getLineNumber(),"Error Semantico en la linea: "+c.getLineNumber()+
                    "ya existe un constructor en la clase "+ name);
        }
        else if(constructor==null && !c.getName().equals(name)){
            throw new SemanticErrorException(c.getName(),c.getLineNumber(),"Error Semantico en la linea: "+
                    c.getLineNumber()+" el nombre del Constructor no coincide con el nombre de la clase" +
                    "a la cual pertenece");
        }

        constructor = c;
        //currentUnit = c;
    }

    public void insertAttribute(Attribute attribute) throws SemanticErrorException{
        if(myAtributes.containsKey(attribute.getName())){
            throw new SemanticErrorException(attribute.getName(),attribute.getLineNumber(),"Error Semantico en la linea:"+
                    attribute.getLineNumber() +" La clase "+name+
                    " tiene atributos con nombres repetidos");
        }

        myAtributes.put(attribute.getName(),attribute);
        orderOfAttributes.add(attribute.getName());
    }

    public HashMap<String,Method> getMyMethods(){
        return myMethods;
    }

    public Method getMethodByName(String methodName){
        return myMethods.get(methodName);
    }

    public Constructor getConstructor(){
        return constructor;
    }

    public HashMap<String,Attribute> getMyAtributes(){
        return myAtributes;
    }

    public Attribute getAttributeIfExist(String name){
        //Only check Attributes that are not hide

        return myAtributes.get(name);
    }


    public void checkCorrectDeclaration() throws SemanticErrorException {
        ArrayList<String> ancestors = new ArrayList<String>();
        ancestors.add(name);
        checkCircularInheritance(ancestors,ancestor,SymbolTable.getInstance());

        checkAttributesDeclaration();

        checkConstructorExistenceAndCorrectDeclaration();

        checkMethodDeclaration();

    }

    private void checkCircularInheritance(ArrayList<String> ancestors,String ancestor,SymbolTable symbolTable)
            throws SemanticErrorException{
        if(ancestor != null){
            if(!symbolTable.getClasses().containsKey(ancestor)){
                throw new SemanticErrorException(name,lineNumber,"Error Semantico en la linea: "+ lineNumber+
                        "La clase "+name+" tiene en su cadena de herencia una clase que no existe");
            }
            if(ancestors.contains(ancestor)){
                throw new SemanticErrorException(name,lineNumber,"Error Semantico en la linea: "+ lineNumber+
                        "La clase "+name+" sufre de herencia circular");
            }
            else{
                ancestors.add(ancestor);
                checkCircularInheritance(ancestors,symbolTable.getClasses().get(ancestor).getAncestor(),symbolTable);
            }
        }
    }

    private void checkConstructorExistenceAndCorrectDeclaration() throws SemanticErrorException {
        if(constructor == null){
            constructor = new Constructor(name,0,this);
            BlockNode blockNode = new BlockNode(null,constructor);
            constructor.setMyBlock(blockNode);
        }
        else {
            constructor.checkConstructorParameterDeclaration();
        }
    }


    public void checkAttributesDeclaration() throws SemanticErrorException {
        for (String attr : orderOfAttributes){
            myAtributes.get(attr).checkTypeExistence();
        }

        attributesConsolidation();
    }

    private void attributesConsolidation() {
        Collection<Attribute> inheritanceAttr = getInheritanceAttr(ancestor);

        for (Attribute attr: inheritanceAttr){
            if(myAtributes.containsKey(attr.getName())){
                if(!hideAttrs.containsKey(attr.getClassWhereBelong())){
                    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                    attributes.add(attr);
                    hideAttrs.put(attr.getClassWhereBelong(),attributes);
                }
                else{
                    ArrayList<Attribute> allAttr = hideAttrs.get(attr.getClassWhereBelong());
                    allAttr.add(attr);
                    hideAttrs.put(attr.getClassWhereBelong(),allAttr);
                }
            }
            else {
                myAtributes.put(attr.getName(),attr);
            }
        }
    }

    private Collection<Attribute> getInheritanceAttr(String ancestor){
        Collection<Attribute> attrs = new ArrayList<Attribute>();
        SymbolTable symbolTable = SymbolTable.getInstance();
        Collection <Attribute> inheritanceAttr;
        while (ancestor != null){
            inheritanceAttr = symbolTable.getClasses().get(ancestor).getMyAtributes().values();
            attrs.addAll(inheritanceAttr);
            ancestor = SymbolTable.getInstance().getClasses().get(ancestor).getAncestor();
        }
        return attrs;
    }


    public void checkMethodDeclaration() throws SemanticErrorException {
        for (String method : orderOfMethods){
            myMethods.get(method).checkMethodTypeDeclaration();
        }

        Collection<Method> inheritanceMethod = getInheritanceMethod(ancestor);

        for(Method method: inheritanceMethod){
            if(myMethods.containsKey(method.getName())){
                if(!myMethods.get(method.getName()).equalsForOverWrite(method)){
                    throw new SemanticErrorException(myMethods.get(method.getName()).getName(),
                            myMethods.get(method.getName()).getLineNumber(), "Error Semantico en la linea: "+
                            myMethods.get(method.getName()).getLineNumber()+" el metodo "+ myMethods.get(method.getName()).getName()+
                            " tiene el mismo nombre que en una clase Super sin poder SobreEscribirlo");
                }
                else{
                    myMethods.get(method.getName()).setIsAOverwriteMethod();
                }
            }
            else {
                myMethods.put(method.getName(),method);
            }
        }
    }

    private Collection<Method> getInheritanceMethod(String ancestor){
        Collection<Method> methods = new ArrayList<Method>();
        SymbolTable symbolTable = SymbolTable.getInstance();
        Collection<Method> inheritanceMethods;
        /*
        while (ancestor != null){
            inheritanceMethods = symbolTable.getClasses().get(ancestor).getMyMethods().values();
            methods.addAll(inheritanceMethods);
            inheritMethods.addAll(inheritanceMethods);
            ancestor = symbolTable.getClasses().get(ancestor).getAncestor();
        }
        */

        if(ancestor!=null){
            inheritanceMethods = symbolTable.getClasses().get(ancestor).getMyMethods().values();
            methods.addAll(inheritanceMethods);
            inheritMethods.addAll(inheritanceMethods);
        }
        return methods;
    }

    public boolean getIfAreOffsetConsolidate(){
        return areOffsetsConsolidate;
    }

    public void consolidateOffsets(){
        if(!areOffsetsConsolidate){
            areOffsetsConsolidate = true;
            if(ancestor!=null && !symbolTable.getClasses().get(ancestor).areOffsetsConsolidate){
                symbolTable.getClasses().get(ancestor).consolidateOffsets();
            }
            int greaterOffsetVt = getGreaterOffsetVt();
            setMethodsOffset(greaterOffsetVt);
            int greaterOffsetCir = getGreaterOffsetCir();
            setAttributesOffset(greaterOffsetCir);

            //System.out.println("CLASE::"+name+" greater offsetVT:"+greaterOffsetVt+" greaterOffCir "+ greaterOffsetCir);
        }
    }

    private int getGreaterOffsetVt(){
        Collection<Method> methods = myMethods.values();
        int greaterOffset = -1;
        for(Method method : methods){
            if(method.getOffsetVt()>greaterOffset){
                greaterOffset = method.getOffsetVt();
            }
        }
        return greaterOffset;
    }

    private void setMethodsOffset(int greaterOff){
        greaterOff = greaterOff +1 ;

        for(String name : orderOfMethods){
            if(!myMethods.get(name).getIsOverwriteMethod()){
                myMethods.get(name).setOffsetVt(greaterOff);
                greaterOff = greaterOff + 1;
            }
            else{
                int off = findMethodOffset(name);
                myMethods.get(name).setOffsetVt(off);
            }
        }
    }

    private int findMethodOffset(String methodName){
        int answer=0;
      //  System.out.println("La cant Met en inheritMethods es:: "+inheritMethods.size()+" en "+name);
        for(Method method: inheritMethods){
            if(method.getName().equals(methodName)){
                answer = method.getOffsetVt();
                break;
            }
        }
        return answer;
    }

    private int getGreaterOffsetCir(){
        Collection<Attribute> attributes = myAtributes.values();
        int greaterOffset = 0;
        for(Attribute attribute : attributes){
            if(attribute.getOffsetCir()>greaterOffset){
                greaterOffset = attribute.getOffsetCir();
            }
        }
        return greaterOffset;
    }

    private void setAttributesOffset(int greaterOffset){
        greaterOffset = greaterOffset + 1 ;

        for(String name: orderOfAttributes){
            myAtributes.get(name).setOffsetCir(greaterOffset);
            greaterOffset = greaterOffset + 1;
        }
    }


    public void checkSentences() throws SemanticErrorException {
        constructor.getMyBlock().check();
        for(String method: orderOfMethods){
            myMethods.get(method).getMyBlock().check();
        }
    }
    public boolean atLeastExistADynamicMethod(){
        boolean answer = false;
        for(Method method: myMethods.values()){
            if(method.getMethodForm().equals("dynamic")){
                answer = true;
                break;
            }
        }
        return answer;
    }

    private void createVTandLabels(){
        consolidateOffsets();
        StringBuilder string = new StringBuilder();
        if(atLeastExistADynamicMethod()){
            symbolTable.genInstruction(".DATA");
            //symbolTable.genInstruction("VT_"+name+":");
            string.append("VT_"+name+": DW ");
        }

        ArrayList<Method> myMethodsByOrder = orderMethodsByOffset(myMethods.values());


        for(Method method: myMethodsByOrder){
            if(method.getMethodForm().equals("dynamic")){
                //symbolTable.genInstruction("DW "+method.getName()+"_"+name);
                string.append(method.getName()+"_"+method.getMyModule().getName()+", ");
               //System .out.println("Metodo: "+method.getName()+" offset: "+method.getOffsetVt()+" clase: "+method.getMyModule().getName());
            }
        }
        if(string.length()>0){
            string.delete(string.length() -2, string.length());
            symbolTable.genInstruction(string.toString());
        }

    }

    private ArrayList<Method> orderMethodsByOffset(Collection<Method> myMethods){
        int offset = 0;
        ArrayList<Method> toRet = new ArrayList<Method>();
        int initIndex = 0 , endIndex = myMethods.size();
        while(initIndex<endIndex){
            Method methodAux = null;
            for(Method method: myMethods){
                if(method.getOffsetVt() == offset){
                    methodAux = method;
                    toRet.add(method);
                    offset++;
                    initIndex++;
                    break;
                }
            }
            //myMethods.remove(methodAux);
        }
        return toRet;
    }


    public void generate(){
        createVTandLabels();

        constructor.generate();

        if(!name.equals("System")) {
            for (Method method : myMethods.values()) {
                if(!method.isCodeAlreadyGenerated())
                    method.generate();
            }
        }
    }

}

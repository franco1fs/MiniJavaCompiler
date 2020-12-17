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
        while (ancestor != null){
            inheritanceMethods = symbolTable.getClasses().get(ancestor).getMyMethods().values();
            methods.addAll(inheritanceMethods);
            ancestor = symbolTable.getClasses().get(ancestor).getAncestor();
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
            myMethods.get(name).setOffsetVt(greaterOff);
            greaterOff = greaterOff + 1;
        }
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
        if(atLeastExistADynamicMethod()){
            symbolTable.genInstruction(".DATA");
            symbolTable.genInstruction("VT_"+name+":");
        }
        for(Method method: myMethods.values()){
            if(method.getMethodForm().equals("dynamic")){
                symbolTable.genInstruction("DW "+method.getName());
            }
        }
    }

    public void generate(){
        createVTandLabels();

        constructor.generate();

        for(Method method: myMethods.values()){
           method.generate();
        }

    }
}

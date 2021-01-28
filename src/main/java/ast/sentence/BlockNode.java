package ast.sentence;

import symbolTable.*;

import java.util.ArrayList;

public class BlockNode extends SentenceNode {
    private BlockNode fatherBlock;
    private ArrayList<SentenceNode> sentences= new ArrayList<SentenceNode>();
    private Unit unitWhereIBelong; // The unit knows the Module where belong
    private ArrayList<LocalVar> localVars = new ArrayList<LocalVar>();

    private int offsetLocalVar=0;

    public BlockNode(BlockNode fatherBlock,Unit unitWhereIBelong) {
        this.fatherBlock = fatherBlock;
        this.unitWhereIBelong = unitWhereIBelong;
        getDownParameters();
    }

    private void getDownParameters(){
        if(fatherBlock==null){
            convertAndAddParametersToLocalVars();
        }
    }

    private void convertAndAddParametersToLocalVars(){
        ArrayList<Parameter> parameters = unitWhereIBelong.getParameters();
        for(Parameter parameter: parameters){
            localVars.add(new LocalVar(parameter.getType(),parameter.getName(),lineNumber,parameter.getOffset()));
        }
    }


    public ArrayList<SentenceNode> getSentences() {
        return sentences;
    }

    public Unit getUnitWhereIBelong() {
        return unitWhereIBelong;
    }

    public void setSentences(ArrayList<SentenceNode> sentences) {
        this.sentences = sentences;
    }

    public int getOffsetLocalVar(){
        return offsetLocalVar;
    }
    public void insertLocalVar(LocalVar var) throws SemanticErrorException{
        if(thereIsOtherLocalVarOParamWithSameName(var)){
            throw new SemanticErrorException(var.getName(),var.getLineNumber(),"Error Semantico en la linea: "+
                    var.getLineNumber()+" ya existe un Parametro Formal/ Variable Local con el mismo nombre: "+var.getName());
        }
        localVars.add(var);
        if(fatherBlock!=null){
            var.setOffset(fatherBlock.getOffsetLocalVar()+offsetLocalVar);
        }
        else{
            var.setOffset(offsetLocalVar);
        }
        offsetLocalVar--;
    }
    private boolean thereIsOtherLocalVarOParamWithSameName(LocalVar var){
        boolean answer = false;
        for(LocalVar localVar: localVars){
            if(localVar.getName().equals(var.getName())){
                answer = true;
                break;
            }
        }
        return answer;
    }

    public ArrayList<LocalVar> getLocalVars(){
        return localVars;
    }

    private void getDownLocalVarsFromFatherBlock(){
        if(fatherBlock!=null){
            localVars.addAll(fatherBlock.getLocalVars());
        }
    }

    public LocalVar getParameterOrLocalVarIfExist(String name){
        LocalVar toRet = null;
        for(LocalVar localVar: localVars){
            if(localVar.getName().equals(name)){
                toRet = localVar;
                break;
            }
        }
        return toRet;
    }
    @Override
    public void check() throws SemanticErrorException {
        getDownLocalVarsFromFatherBlock();
        for (SentenceNode sentenceNode: sentences){
            sentenceNode.check();
        }
    }

    public BlockNode getFatherBlock(){
        return fatherBlock;
    }

    @Override
    public void generate() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        for(SentenceNode sentenceNode: sentences){
            sentenceNode.generate();
        }

        if(fatherBlock!=null){
           symbolTable.genInstruction("FMEM "+(localVars.size()-fatherBlock.getLocalVars().size()));
           System.out.println("LIBERO :"+(localVars.size()-fatherBlock.getLocalVars().size()));
        }
        else{
            symbolTable.genInstruction("FMEM "+(localVars.size()-unitWhereIBelong.getParameters().size()));
            System.out.println("LIBERO :"+(localVars.size()-unitWhereIBelong.getParameters().size()));
        }
    }
}

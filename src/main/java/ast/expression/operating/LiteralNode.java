package ast.expression.operating;

import symbolTable.MethodType;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;
import symbolTable.Type;

public class LiteralNode extends OperantNode {
    private Type type;
    private String value;

    public LiteralNode(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public MethodType check() throws SemanticErrorException {
        return type;
    }

    @Override
    public void generate() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        switch (type.getTypeName()){
            case "boolean":
                if(value.equals("true")){
                    symbolTable.genInstruction("PUSH 1");
                }
                else{
                    symbolTable.genInstruction("PUSH 0");
                }
                break;
            case "null":
                symbolTable.genInstruction("PUSH 0");
                break;
            case "int":
                symbolTable.genInstruction(value);
                break;
            case "char":
                char letter = '0';
                if(value.length()>0){
                    letter = value.charAt(0);
                }
                symbolTable.genInstruction("PUSH "+(int) letter);
                break;
            case "String":
                String label = symbolTable.getLabel();
                symbolTable.genInstruction(".DATA");
                symbolTable.genInstruction(label+": DW \""+ value+"\",0");
                symbolTable.genInstruction(".CODE");
                symbolTable.genInstruction("PUSH "+label);
                break;
        }
    }


}

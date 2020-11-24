package ast.expression.operating;

import ast.expression.ExpressionNode;
import symbolTable.*;
import symbolTable.Class;

import java.util.ArrayList;

public class MethodChainCall extends ChainCall {

    private ArrayList<ExpressionNode> args = new ArrayList<ExpressionNode>();
    private String methodName;

    public MethodChainCall(ArrayList<ExpressionNode> args, String methodName, int lineNumber) {
        this.args = args;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    public MethodType check(MethodType type) throws SemanticErrorException {
        if (!(type instanceof TidClass)) {
            throw new SemanticErrorException(methodName, type.getLineNumber(), "Error Semantico en la linea: " +
                    type.getLineNumber() + " no es posible acceder de forma encadenada a un metodp de un tipo que " +
                    "no es de Clase como : " +
                    type.getTypeName());
        } else {
            Class clase = SymbolTable.getInstance().getClass(type.getTypeName());
            Method method = clase.getMethodByName(methodName);
            if (method == null) {
                throw new SemanticErrorException(methodName, type.getLineNumber(), "Error Semantico en la linea: " +
                        type.getLineNumber() + " no es posible acceder de forma encadenada a un metodo que no existe en la clase " +
                        ": " +
                        type.getTypeName());
            } else {
                ArrayList<Parameter> parameters = method.getParameters();

                if (parameters.size() == args.size()) {
                    for (int index = 0; index < parameters.size(); index++) {
                        if (!(parameters.get(index).getType().isConformedBy(args.get(index).check()))) {
                            throw new SemanticErrorException(methodName, lineNumber, "Error Semantico en la linea: " +
                                    lineNumber + " acceso a metodo invalido debido a que no hay conformidad de tipos: " +
                                    methodName);
                        }
                    }
                    return method.getReturnType();
                }
                else{
                    throw new SemanticErrorException(methodName, lineNumber, "Error Semantico en la linea: " +
                            lineNumber + " la cantidad de argumentos actuales no coincide con los argumentos formales: " +
                            methodName);
                }
            }
        }
    }

    @Override
    public String getLexemeOfRepresentation() {
        return methodName;
    }
}

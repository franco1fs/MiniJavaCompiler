package ast;

import symbolTable.SemanticErrorException;

public interface INode  {
    void check() throws SemanticErrorException;
}

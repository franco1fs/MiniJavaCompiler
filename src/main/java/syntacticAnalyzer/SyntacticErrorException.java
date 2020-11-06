package syntacticAnalyzer;

import lexicalAnalyzer.Token;

public class SyntacticErrorException extends Exception {

    private String expectedToken;
    private Token receivedToken;

    public SyntacticErrorException( Token rt, String et){
        receivedToken = rt;
        expectedToken = et;
    }

    public String getExpectedTokenName(){
        return expectedToken;
    }

    public Token getReceivedToken(){
        return receivedToken;
    }
}

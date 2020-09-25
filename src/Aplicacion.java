public class Aplicacion {
    public static void main (String arg[]){
        IFileManager fileManager = new FileManager("C:\\Users\\sacomanif\\Desktop\\file.txt");
        //LexAnalyzerTest lex = new LexAnalyzerTest(fileManager);
        LexicalAnalyzer lex = new LexicalAnalyzer(fileManager);

            String eof = "";
            do {
                try {
                    Token token = lex.nextToken();
                    System.out.println("(" + token.getName() + "," + token.getLexeme() + "," + token.getLineNumber() + ")");
                    eof = token.getName();
                } catch (LexicalErrorException e) {
                    System.out.println("Error Léxico en la linea "+e.getLineNumber()+":"+e.getMessage()+"\n"+
                            "[ERROR:"+e.getLexeme()+"|"+e.getLineNumber()+"]");
                }
            }while (!eof.equals("EOF"));
    }


}

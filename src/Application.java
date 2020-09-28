public class Application {
    public static void main (String args[]){
        String fileName;

        if(args.length == 1){
            fileName = args[0];
            //IFileManager fileManager = new FileManager("C:\\Users\\sacomanif\\Desktop\\file.txt");
            //LexAnalyzerTest lex = new LexAnalyzerTest(fileManager);
            IFileManager fileManager = new FileManager(fileName);
            LexicalAnalyzer lex = new LexicalAnalyzer(fileManager);

            String eof = "";
            do {
                try {
                    Token token = lex.nextToken();
                    System.out.println("(" + token.getName() + "," + token.getLexeme() + "," + token.getLineNumber() + ")");
                    eof = token.getName();
                } catch (LexicalErrorException e) {
                    System.out.println("Error Léxico en la linea "+e.getLineNumber()+":"+e.getMessage());
                    System.out.println("[ERROR:"+e.getLexeme()+"|"+e.getLineNumber()+"]");
                }
            }while (!eof.equals("EOF"));
                        }
        }


}

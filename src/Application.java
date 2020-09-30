import java.io.FileNotFoundException;

public class Application {
    public static void main (String args[]){
        String fileName;

        if(args.length == 1){
            fileName = args[0];
            try {
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
            catch (FileNotFoundException f){
                System.out.println("Se produjo un error al intentar abrir el archivo::"+f.getMessage());
                System.out.println("Intente nuevamente introduciendo de forma correcta la ruta del archivo");
            }
        }
        else{
            System.out.println("La cantidad de argumentos es INVALIDA, falta agregar la ruta del archivo de entrada");
        }


    }


}

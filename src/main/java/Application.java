import lexicalAnalyzer.FileManager;
import lexicalAnalyzer.IFileManager;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.LexicalErrorException;
import symbolTable.SemanticErrorException;
import syntacticAnalyzer.SyntacticAnalyzer;
import syntacticAnalyzer.SyntacticErrorException;

import java.io.FileNotFoundException;

public class Application {
    public static void main (String args[]){

        String fileName;

        if(args.length == 1){
            fileName = args[0];
            try {
                IFileManager fileManager = new FileManager(fileName);

                LexicalAnalyzer lex = new LexicalAnalyzer(fileManager);

                try {
                    SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lex);

                    System.out.println("Compilacion Exitosa \n \n [SinErrores]");

                }
                catch (SyntacticErrorException e){
                    System.out.println("Error Sintactico en linea "+e.getReceivedToken().getLineNumber()+": \n" +
                            "se esperaba "+e.getExpectedTokenName()+" y se encontro "+ e.getReceivedToken().getLexeme() +"\n" +
                            "[Error:"+e.getReceivedToken().getLexeme()+"|"+e.getReceivedToken().getLineNumber()+"]");
                }
                catch (LexicalErrorException e){
                    System.out.println("Error Lexico en la linea "+e.getLineNumber()+":"+e.getMessage());
                    System.out.println("[Error:"+e.getLexeme()+"|"+e.getLineNumber()+"]");
                }
                catch (SemanticErrorException e){
                    System.out.println(e.getMessage());
                    System.out.println("[Error:"+e.getLexeme()+"|"+e.getLineNumber()+"]");
                }

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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import symbolTable.Class;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public class SemanticAnalyzerTest {

    // To run this Test correctly i should delete the sentence who do consolidation table
    @ParameterizedTest
    @MethodSource("args")
    void syntacticTest(FileManager file) {
        String firstLine = file.firstLine();
        LexicalAnalyzer la = new LexicalAnalyzer(file);
        try {
            SyntacticAnalyzer sa = new SyntacticAnalyzer(la);
            System.out.println("Paso Exitosamente");
        }
        catch (SyntacticErrorException e){
            String received = "//"+e.getReceivedToken().getName();
            System.out.println(e.getMessage());
            Assertions.assertEquals(firstLine,received);
        }
        catch (LexicalErrorException e){
            System.out.println(e.getMessage());
        }
        catch (SemanticErrorException e){
            String received = "//"+e.getLexeme();
            System.out.println(e.getMessage());
            Assertions.assertEquals(firstLine,received);
        }
    }


    static Stream<Arguments> args() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p1.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p2.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p3.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p4.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p5.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p6.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p7.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p8.txt"))



        );
    }

    @ParameterizedTest
    @MethodSource("args2")
    void syntacticTestWithSecondLap(FileManager file) {
        String firstLine = file.firstLine();
        LexicalAnalyzer la = new LexicalAnalyzer(file);
        try {
            SyntacticAnalyzer sa = new SyntacticAnalyzer(la);
            System.out.println("Paso Exitosamente");
        }
        catch (SyntacticErrorException e){
            String received = "//"+e.getReceivedToken().getName();
            System.out.println(e.getMessage());
            Assertions.assertEquals(firstLine,received);
        }
        catch (LexicalErrorException e){
            System.out.println(e.getMessage());
        }
        catch (SemanticErrorException e){
            String received = "//"+e.getLexeme();
            System.out.println(e.getMessage());
            Assertions.assertEquals(firstLine,received);
        }
    }

    static Stream<Arguments> args2() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p9.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p10_attributes.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p11_atributes.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p12_constructor.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p13_ObjectRepetido.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p14_HerenciaCircular.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p15_HC2.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p16_atR.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p17_MetSuperForma.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p18_MetSuperTipo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p19_MetSuperCantArg.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p20_MetSuperTipoArg.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p21_MetSuperRet.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p22_Metodos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p23_HCorrect.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p24_MetSuperCorrect.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p25_AllCorrect.txt"))


















        );
    }


    }

import lexicalAnalyzer.FileManager;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.LexicalErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import symbolTable.SemanticErrorException;
import symbolTable.SymbolTable;
import syntacticAnalyzer.SyntacticAnalyzer;
import syntacticAnalyzer.SyntacticErrorException;

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
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p8.txt")),
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
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p25_AllCorrect.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p26_main.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p27_RedefCorrect.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p28_constructor.txt"))

        );
    }


    @ParameterizedTest
    @MethodSource("args3")
    void semanticAnalyzer(FileManager file) {
        String firstLine = file.firstLine();
        LexicalAnalyzer la = new LexicalAnalyzer(file);
        try {
            SyntacticAnalyzer sa = new SyntacticAnalyzer(la);
            SymbolTable.getInstance().checkClassesDeclarationAndConsolidationTable();
            SymbolTable.getInstance().checkSentences();
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


    static Stream<Arguments> args3() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p1.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p2.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p3.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p4.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p5.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p6.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p7.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p8.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p9.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p10.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p11.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p12.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p13.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p14.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p15.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p16.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p17.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p18.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p19.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p20.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p21.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p22.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p23.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p24.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p25.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p26.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p27.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p28.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p29.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p30.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p31.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p32.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p33.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p34.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p35.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p36.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p37.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p38.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p40.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p41.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p42.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p43.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p44.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p45.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p46.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p47.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p48.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p49.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p50.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p51.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p52.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p53.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p54.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p55.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p56.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p57.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p58.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\SemSentencias\\p59.txt"))












        );
    }
    }

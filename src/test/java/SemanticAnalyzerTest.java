import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import symbolTable.SemanticErrorException;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public class SemanticAnalyzerTest {

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
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Semantico\\p7.txt"))





        );
    }

    }

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SyntacticAnalyzerTest {


    @ParameterizedTest
    @MethodSource("args")
    void syntacticTest(FileManager file) {
        String firstLine = file.firstLine();
        //String dataAnswer = processData(firstLine);
        LexicalAnalyzer la = new LexicalAnalyzer(file);
        try {
            SyntacticAnalyzer sa = new SyntacticAnalyzer(la);
            System.out.println("Paso Exitosamente");
        }
        catch (SyntacticErrorException e){
            //System.out.println("Recibi : "+e.getReceivedToken().getName()+ " y esperaba :"+ e.getExpectedTokenName());
            String received = "//"+e.getReceivedToken().getName();
            Assertions.assertEquals(firstLine,received);
        }
        catch (LexicalErrorException e){
            System.out.println(e.getMessage());
        }

    }



    static Stream<Arguments> args() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p1.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p2.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p3.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p4.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p5.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p6.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p7_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p8_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p9_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p10_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p11_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p12_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p13_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p14_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p15_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p16_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p17_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p18_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p19_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p20_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p21_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p22_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p23_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p24_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p25_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p26_asignacionyparametros.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p27_asignaciones.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p28_claseconatributos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p29_constructores.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p30_objetos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p31_detodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p32_return.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p33_clasesifelse.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\p34_todagramatica.txt"))








        );
    }

}
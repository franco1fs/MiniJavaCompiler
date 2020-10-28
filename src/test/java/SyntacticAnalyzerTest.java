import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import symbolTable.SemanticErrorException;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SyntacticAnalyzerTest {


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
            Assertions.assertEquals(firstLine,received);
        }
        catch (LexicalErrorException e){
            System.out.println(e.getMessage());
        }
        catch (SemanticErrorException e){

        }
    }



    static Stream<Arguments> args() throws FileNotFoundException {
        return Stream.of(
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p1.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p2.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p3.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p4.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p5.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p6.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p7_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p8_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p9_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p10_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p11_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p12_metodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p13_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p14_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p15_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p16_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p17_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p18_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p19_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p20_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p21_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p22_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p23_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p24_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p25_sentencia.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p26_asignacionyparametros.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p27_asignaciones.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p28_claseconatributos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p29_constructores.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p30_objetos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p31_detodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p32_return.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p33_clasesifelse.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p34_todagramatica.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p35_interface.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p36_interfaceymetodo.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p37_interfaceextends.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p38_claseinterface.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p39_genericidad.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p40_asignacionenlinea.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p41_visatimplicita.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p42_visimpconstructor.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p43_llamadametodos.txt")),
                Arguments.of(new FileManager("C:\\Users\\sacomanif\\Desktop\\CP\\Sintactico\\p44_todagramatica.txt"))
        );
    }



}
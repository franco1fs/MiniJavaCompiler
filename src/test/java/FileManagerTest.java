import lexicalAnalyzer.FileManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

class FileManagerTest {

    @ParameterizedTest
    @MethodSource("args")
    void pruebaTest(FileManager file){

    }

    static Stream<Arguments> args() throws  FileNotFoundException {
        return Stream.of(
         Arguments.of(new FileManager("s")),
                Arguments.of(new FileManager("s"))
        );
    }

}
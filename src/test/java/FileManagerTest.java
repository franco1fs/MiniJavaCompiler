import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
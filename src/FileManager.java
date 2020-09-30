import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileManager implements IFileManager {

    private FileReader fileIn;
    private BufferedReader fileBuffer;
    private boolean endOfFile= false;

    public FileManager(String file) throws FileNotFoundException{

        fileIn = new FileReader(file);

        fileBuffer = new BufferedReader(fileIn);
    }

    @Override
    public char nextChar() throws Exception{
        int nextChar = fileBuffer.read();
        if(nextChar == -1)
            throw new Exception("EOF");

        return (char) nextChar;
    }
}

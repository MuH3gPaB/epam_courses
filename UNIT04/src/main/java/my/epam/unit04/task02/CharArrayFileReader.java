package my.epam.unit04.task02;

import my.epam.unit04.words_calculator.ArrayFileReader;
import my.epam.unit04.task01.ByteArrayFileReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class CharArrayFileReader implements ArrayFileReader {
    private static Logger logger = Logger.getLogger(ByteArrayFileReader.class);
    private final Path filePath;
    private static final String lineSeparator = System.getProperty("line.separator");

    public CharArrayFileReader(File file) {
        this.filePath = file.toPath();
    }

    @Override
    public Stream<String> getLines() {
        try (Reader reader = new FileReader(filePath.toFile())) {
            StringBuilder result = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1){
                result.append((char) character);
            }

            return Arrays.stream(result.toString().split(lineSeparator));
        } catch (IOException e) {
            logger.error("Could not read the file " + filePath.getFileName());
        }
        return Arrays.stream(new String[0]);
    }


}

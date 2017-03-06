package my.epam.unit04.task01;

import my.epam.unit04.FileReader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class InputStreamFileReader implements FileReader {
    private static Logger logger = Logger.getLogger(InputStreamFileReader.class);
    private final Path filePath;

    public InputStreamFileReader(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> getLines() {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            logger.error("Could not read the file " + filePath.getFileName());
        }
        return Arrays.stream(new String[0]);
    }
}

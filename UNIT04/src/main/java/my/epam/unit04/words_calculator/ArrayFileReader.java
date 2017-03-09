package my.epam.unit04.words_calculator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface ArrayFileReader {
    Stream<String> getLines();
}

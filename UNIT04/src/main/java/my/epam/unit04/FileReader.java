package my.epam.unit04;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileReader {
    Stream<String> getLines();
}

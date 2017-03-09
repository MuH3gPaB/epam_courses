package my.epam.unit04.task03;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        Path fileUtf8 = new File("./dataForApp/utf8.txt").toPath();
        Path fileUtf16 = new File("./dataForApp/utf16.txt").toPath();

        unf8ToUtf16(fileUtf8, fileUtf16);
    }

    private static void unf8ToUtf16(Path fileUtf8, Path fileUtf16) {
        try (BufferedReader br = Files.newBufferedReader(fileUtf8);
             BufferedWriter bw = Files.newBufferedWriter(fileUtf16, StandardCharsets.UTF_16)) {
            br.lines().forEach((s) -> {
                try {
                    bw.write(s);
                } catch (IOException e) {
                    logger.error("Writing to file [" + fileUtf16.getFileName() + "]error. \n" + e.getMessage());
                }
            });
        } catch (IOException e) {
            logger.error("Could not read the file " + fileUtf8.getFileName());
        }
    }

}

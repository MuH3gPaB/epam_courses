package my.epam.unit04.task01;

import my.epam.unit04.FileReader;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class InputStreamFileReader implements FileReader {
    private static Logger logger = Logger.getLogger(InputStreamFileReader.class);
    private final Path filePath;
    private static final String lineSeparator = System.getProperty("line.separator");

    public InputStreamFileReader(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> getLines() {
        try (InputStream is = new FileInputStream(filePath.toFile())) {
            ArrayList<Byte> bytesList = new ArrayList<>();
            byte rByte;
            while ((rByte = (byte) is.read()) != -1) {
                bytesList.add(rByte);
            }

            byte[] bytes = new byte[bytesList.size()];

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = bytesList.get(i);
            }

            String allLines = new String(bytes, Charset.defaultCharset());

            return Arrays.stream(allLines.split(lineSeparator));
        } catch (IOException e) {
            logger.error("Could not read the file " + filePath.getFileName());
        }
        return Arrays.stream(new String[0]);
    }


}

package my.epam.unit04.task01;

import my.epam.unit04.words_calculator.ArrayFileReader;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ByteArrayFileReader implements ArrayFileReader {
    private static Logger logger = Logger.getLogger(ByteArrayFileReader.class);
    private final Path filePath;
    private static final String lineSeparator = System.getProperty("line.separator");

    public ByteArrayFileReader(File file) {
        this.filePath = file.toPath();
    }

    @Override
    public Stream<String> getLines() {
        byte[] bytes = readAllBytes();

        String allLines = new String(bytes, Charset.defaultCharset());

        return Arrays.stream(allLines.split(lineSeparator));
    }

    private byte[] readAllBytes() {
        ArrayList<Byte> bytesList = new ArrayList<>();
        try (InputStream is = new FileInputStream(filePath.toFile())) {
            byte rByte;
            while ((rByte = (byte) is.read()) != -1) {
                bytesList.add(rByte);
            }
        } catch (IOException e) {
            logger.error("Could not read the file " + filePath.getFileName());
        }

        return getPrimitivesFromList(bytesList);
    }

    private byte[] getPrimitivesFromList(ArrayList<Byte> bytesList) {
        byte[] bytes = new byte[bytesList.size()];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = bytesList.get(i);
        }
        return bytes;
    }


}

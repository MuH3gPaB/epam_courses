package my.epam.unit04.task01;

import my.epam.unit04.words_calculator.ArrayFileWriter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class ByteArrayFileWriter implements ArrayFileWriter {
    private static Logger logger = Logger.getLogger(ByteArrayFileWriter.class);
    private final File file;

    public ByteArrayFileWriter(File file) {
        this.file = file;
    }

    @Override
    public void write(String[] strings) {
        try (OutputStream os = new FileOutputStream(file)) {
            for (String str : strings) {
                os.write(str.getBytes(Charset.defaultCharset()));
                os.write(System.getProperty("line.separator").getBytes(Charset.defaultCharset()));
                os.flush();
            }
        } catch (IOException e) {
            logger.error("Write to file [" + file.getName() + "] error! \n" + e.getMessage());
        }
    }
}

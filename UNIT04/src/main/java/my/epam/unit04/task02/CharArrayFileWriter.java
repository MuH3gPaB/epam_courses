package my.epam.unit04.task02;

import my.epam.unit04.words_calculator.ArrayFileWriter;
import my.epam.unit04.task01.ByteArrayFileWriter;
import org.apache.log4j.Logger;

import java.io.*;

public class CharArrayFileWriter implements ArrayFileWriter{
    private static Logger logger = Logger.getLogger(ByteArrayFileWriter.class);
    private final File file;

    public CharArrayFileWriter(File file) {
        this.file = file;
    }

    @Override
    public void write(String[] strings) {
        try (Writer writer = new FileWriter(file)) {
            for (String str : strings) {
                writer.write(str.toCharArray());
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (IOException e) {
            logger.error("Write to file [" + file.getName() + "] error! \n" + e.getMessage());
        }
    }
}

package my.epam.unit05.task01.util;

import my.epam.unit05.task01.exceptions.DoesNotExistException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class TextFileAppender {
    private final File file;

    private TextFileAppender(File file) {
        this.file = file;
    }

    public static TextFileAppender getAppender(File file) throws DoesNotExistException, IOException {
        if (!file.exists()) throw new DoesNotExistException("File [" + file.getName() + "] does not exist.");
        if (!file.canWrite()) throw new IOException("File [" + file.getName() + "] is write protected.");

        return new TextFileAppender(file);
    }

    public void append(String text) throws IOException {
        Files.write(file.toPath(), text.getBytes(Charset.defaultCharset()), StandardOpenOption.APPEND);
        Files.write(file.toPath(), System.getProperty("line.separator").getBytes(Charset.defaultCharset()), StandardOpenOption.APPEND);

    }
}

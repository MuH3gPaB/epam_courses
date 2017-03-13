package my.epam.unit05.task01;

import my.epam.unit05.task01.exceptions.DoesNotExistException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class TextFileAppender {
    private final File file;

    private TextFileAppender(File file) {
        this.file = file;
    }

    public TextFileAppender getAppender(File file) throws DoesNotExistException, IOException {
        if(!file.exists()) throw new DoesNotExistException("File ["+file.getName()+"] does not exist.");
        if(!file.canWrite()) throw new IOException("File ["+file.getName()+"] is write protected.");

        return new TextFileAppender(file);
    }

    public void append(String text) throws IOException {
        Files.write(file.toPath(), text.getBytes(), StandardOpenOption.APPEND);
    }
}

package my.epam.unit04.task01;

import my.epam.unit04.FileWriter;

import java.io.File;
import java.nio.file.Path;

public class OutputStreamFileWriter implements FileWriter{
    private final File file;

    public OutputStreamFileWriter(File file) {
        this.file = file;
    }

    @Override
    public void write(String[] strings) {

    }
}

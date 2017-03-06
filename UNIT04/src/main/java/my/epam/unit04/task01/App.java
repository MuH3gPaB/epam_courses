package my.epam.unit04.task01;

import my.epam.unit04.FileReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL inputFileUrl = App.class.getResource("/my/epam/unit04/task01/CrazyLogger.javaText");
        if (inputFileUrl == null) throw new FileNotFoundException();
        URI inputFileUri = inputFileUrl.toURI();
        Path filePath = Paths.get(inputFileUri);

        File outputFile = new File("hello.txt");

        calculateKeywordsToFile(new InputStreamFileReader(filePath), new OutputStreamFileWriter(outputFile));
    }

    private static void calculateKeywordsToFile(InputStreamFileReader inputStreamFileReader, OutputStreamFileWriter outputStreamFileWriter) {
        String fileAsString = getJavaCodeAsString(inputStreamFileReader);
        String noComments = clearJavaComments(fileAsString);
        System.out.println(noComments);
    }

    private static String clearJavaComments(String fileAsString) {
        String str = fileAsString.replaceAll("(\\/\\*.*?\\*\\/)", "");
        return str;
    }

    private static String getJavaCodeAsString(FileReader reader) {
        StringBuilder sb = new StringBuilder();
        Stream<String> lines = reader.getLines();
        if (lines != null) {
            lines.map(s -> s.replaceAll("\\/\\/.*", ""))
                    .filter(s -> !s.isEmpty())
                    .forEach(sb::append);
        }
        return sb.toString();
    }
}

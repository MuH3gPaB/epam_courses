package my.epam.unit03.task03;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    private static Logger logger = Logger.getLogger(App.class);
    private static String pattern = "(.*(\\([Рр]ис. [0-9]{1,9}\\)).*)";


    public static void main(String[] args) throws URISyntaxException {
        URI fileUri = App.class.getResource("Java.SE.03.Information handling_task_attachment.html").toURI();
        Path filePath = Paths.get(fileUri);

        boolean isValid = checkTheFile(filePath);
        System.out.println("File " + filePath.getFileName() + " is " + ((isValid)?"valid":"invalid")+".");
        System.out.println("________________________________________");

        String searchPattern = "(.*(\\([Рр]ис. [0-9]{1,9}\\)).*)";
        String clausePattern = "( *[А-Я0-9]).*?([.]{3}|(?<![Рр]ис)[.]|[?!])";
        String[] clauses = getClausesFromFile(searchPattern, clausePattern, filePath);

        for (int i = 0; i < clauses.length; i++) {
            System.out.println((i+1) + " -- " + clauses[i]);
        }
    }

    private static String[] getClausesFromFile(String searchPattern, String clausePattern, Path filePath) {
        try {
            StringBuilder sb = new StringBuilder();
            Files.lines(filePath, Charset.forName("cp1251"))
                    .forEach(sb::append);

            ClauseSearcher searcher = new ClauseSearcher(searchPattern, clausePattern);
            return searcher.search(sb.toString());
        } catch (IOException e) {
            logger.error("Error reading the file " + filePath.getFileName() + "[" + e.getMessage() + "]");
            return new String[0];
        }
    }

    private static boolean checkTheFile(Path filePath) {
        try (BufferedReader br = Files.newBufferedReader(filePath, Charset.forName("cp1251"))) {
            StringChecker checker = new StringChecker(pattern, Integer::compare);
            int maxValue = 0;
            String line;
            while ((line = br.readLine()) != null) {
                maxValue = checker.checkString(maxValue, line);
            }
        } catch (IOException e) {
            logger.error("Error reading the file " + filePath.getFileName() + "[" + e.getMessage() + "]");
        } catch (StringCheckFailException e) {
            logger.info("File " + filePath.getFileName() + " - " + e.getMessage());
            return false;
        }
        return true;
    }
}

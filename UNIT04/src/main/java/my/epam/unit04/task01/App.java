package my.epam.unit04.task01;

import my.epam.unit04.words_calculator.JavaCodeKeyWordsCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class App {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        File inputFile = new File("./dataForApp/CrazyLogger.javaText");
        File outputFile = new File("./dataForApp/wordsCountByteArray.txt");

        JavaCodeKeyWordsCalculator.calculateKeywordsToFile(new ByteArrayFileReader(inputFile), new ByteArrayFileWriter(outputFile));
    }


}

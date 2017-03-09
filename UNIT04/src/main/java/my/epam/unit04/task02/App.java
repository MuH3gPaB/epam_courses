package my.epam.unit04.task02;

import my.epam.unit04.words_calculator.JavaCodeKeyWordsCalculator;

import java.io.File;

public class App {
    public static void main(String[] args) {
        File inputFile = new File("./dataForApp/CrazyLogger.javaText");
        File outputFile = new File("./dataForApp/wordsCountCharArray.txt");

       JavaCodeKeyWordsCalculator.calculateKeywordsToFile(new CharArrayFileReader(inputFile), new CharArrayFileWriter(outputFile));
    }
}

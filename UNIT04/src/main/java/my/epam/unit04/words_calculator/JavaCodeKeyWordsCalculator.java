package my.epam.unit04.words_calculator;

import java.util.Map;
import java.util.stream.Stream;

public class JavaCodeKeyWordsCalculator {
    private static final String[] KEYWORDS_JAVA = new String[]{"abstract", "continue", "for", "new", "switch", "assert",
            "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double",
            "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof",
            "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"};

    public static void calculateKeywordsToFile(ArrayFileReader inputStreamFileReader, ArrayFileWriter outputStreamFileWriter) {
        String fileAsString = getJavaCodeAsString(inputStreamFileReader);

        String noComments = clearJavaComments(fileAsString);

        StringWordsCounter counter = new StringWordsCounter(KEYWORDS_JAVA);

        boolean includeAbsent = false;
        Map<String, Integer> result = counter.calculateWords(noComments, includeAbsent);

        String[] resultStrings = getStrings(result);

        outputStreamFileWriter.write(resultStrings);
    }

    private static String[] getStrings(Map<String, Integer> result) {
        return result.entrySet().stream()
                    .map((e) -> e.getKey() + ":" + e.getValue())
                    .toArray(String[]::new);
    }

    private static String clearJavaComments(String fileAsString) {
        String str = fileAsString.replaceAll("(\\/\\*.*?\\*\\/)", "");
        return str;
    }

    private static String getJavaCodeAsString(ArrayFileReader reader) {
        StringBuilder sb = new StringBuilder();
        Stream<String> lines = reader.getLines();
        if (lines != null) {
            lines.map(s -> s.replaceAll("\\/\\/.*", ""))
                    .filter(s -> !s.isEmpty())
                    .forEach(sb::append);
        }
        return sb.toString();
    }

    private JavaCodeKeyWordsCalculator(){}
}

package my.epam.unit03.task03;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {
    private final Pattern pattern;
    private final static String patternChecker = "[0-9]{1,9}";
    private final Comparator<Integer> comparator;

    public StringChecker(String pattern, Comparator<Integer> comparator) {
        if (!checkPattern(pattern)) throw new IllegalArgumentException("Illegal pattern.");
        this.comparator = comparator;
        this.pattern = Pattern.compile(pattern);
    }

    private static boolean checkPattern(String pattern) {
        return pattern.contains(patternChecker)
                && pattern.indexOf(patternChecker) == pattern.lastIndexOf(patternChecker);
    }

    public int checkString(int minValue, String string) throws StringCheckFailException {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()){
            int founded = getValueFromString(matcher.group());
            if (comparator.compare(founded, minValue) < 0) throw new StringCheckFailException("Wrong string '" + string + "'");
            else minValue = founded;
        }
        return minValue;
    }

    private static int getValueFromString(String str) throws StringCheckFailException {
        Matcher matcher = Pattern.compile(patternChecker).matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            throw new StringCheckFailException();
        }

    }
}



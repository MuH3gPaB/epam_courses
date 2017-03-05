package my.epam.unit03.task03;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String checker is string analytic class.
 * <p>
 * The goal is to analyse the order of some integer values in the string.
 * Class use regexp pattern to find integer values.
 * <p>
 * Pattern could contain only ONE integer value mark, represented by
 * "[0-9]{1,9}" sub-pattern.
 * Multimarks do not supported.
 * <p>
 * If pattern is wrong illegal argument exception will thrown.
 * <p>
 * The order of integer values can be set by comparator given to constructor
 * of StringChecker.
 */

public class StringChecker {
    private final Pattern pattern;
    private final static String patternChecker = "[0-9]{1,9}";
    private final Comparator<Integer> comparator;

    public StringChecker(String pattern, Comparator<Integer> comparator) {
        if (!checkPattern(pattern)) throw new IllegalArgumentException("Illegal pattern.");
        this.comparator = comparator;
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Check if string is correct.
     *
     * @param minValue Minimum value that first founded integer could be.
     * @param string   Checked string.
     * @return New min value represented by maximum founded value, or old minValue if nothing was found
     * @throws StringCheckFailException When the order of founded values is wrong, or first founded value less then minValue
     */
    public int checkString(int minValue, String string) throws StringCheckFailException {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            int founded = getValueFromString(matcher.group());
            if (comparator.compare(founded, minValue) < 0)
                throw new StringCheckFailException("Wrong string '" + string + "'");
            else minValue = founded;
        }
        return minValue;
    }

    private static int getValueFromString(String str) throws StringCheckFailException {
        Matcher matcher = Pattern.compile(patternChecker).matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            throw new StringCheckFailException("Wrong string format.");
        }

    }

    private static boolean checkPattern(String pattern) {
        return pattern.contains(patternChecker)
                && pattern.indexOf(patternChecker) == pattern.lastIndexOf(patternChecker);
    }
}



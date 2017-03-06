package my.epam.unit04.task01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Expressions calculator class.
 * <p>
 * Looks to string for expression, and calculates count of
 * founded.
 * <p>
 * Support simple words, or regular expressions.
 */

public class StringWordsCounter {
    private final String[] words;

    /**
     * Builds new WordCounter.
     * <p>
     * Words should not be empty array or array with all strings empty or null.
     *
     * @param words array with words or regular expressions.
     */
    public StringWordsCounter(String[] words) {
        if (words.length == 0) throw new IllegalArgumentException("Words array should not be empty.");
        if (!Arrays.stream(words).anyMatch((s) -> s != null && !s.isEmpty()))
            throw new IllegalArgumentException("All words should not be empty or null.");
        this.words = words;
    }

    public Map<String, Integer> calculateWords(String string) {
        Map<String, Integer> result = new HashMap<>();
        Arrays.stream(words)
                .filter((s) -> s != null && !s.isEmpty())
                .forEach((s) -> {
                    try {
                        Pattern pattern = Pattern.compile(s, Pattern.MULTILINE);
                        result.put(s, calculatePattern(pattern, string));
                    } catch (PatternSyntaxException e) {
                        result.put(s, calculateWord(s, string));
                    }
                });
        return result;
    }

    private Integer calculateWord(String word, String string) {
        if (!string.contains(word)) return 0;
        else {
            int counter = 1;
            int currentIndex = string.indexOf(word) + 1;
            while (string.indexOf(word, currentIndex) != -1) {
                counter++;
                currentIndex = string.indexOf(word, currentIndex) + 1;
            }
            return counter;
        }
    }

    private Integer calculatePattern(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        int result = 0;
        while (matcher.find()) {
            result++;
        }
        return result;
    }
}

package my.epam.unit03.task03;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Nested "." after "Рис" inside brackets was hardcoded =(
 */

public class ClauseSearcher {
    private Pattern patternToSearch;
    private Pattern clausePattern;

    public ClauseSearcher(String patternToSearch, String clausePattern) {
        if (patternToSearch.isEmpty()) throw new IllegalArgumentException();
        if (clausePattern.isEmpty()) throw new IllegalArgumentException();
        this.patternToSearch = Pattern.compile(patternToSearch, Pattern.MULTILINE);
        this.clausePattern = Pattern.compile(clausePattern, Pattern.MULTILINE);
    }


    public String[] search(String validString) {
        Matcher cMatcher = clausePattern.matcher(validString);
        ArrayList<String> result = new ArrayList<>();
        while (cMatcher.find()) {
            String founded = cMatcher.group();
            founded = founded.trim();
            if (founded.matches(patternToSearch.pattern())) result.add(founded);
        }
        return result.toArray(new String[0]);
    }
}

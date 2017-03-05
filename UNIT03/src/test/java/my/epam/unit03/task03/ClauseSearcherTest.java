package my.epam.unit03.task03;

import org.junit.Assert;
import org.junit.Test;

public class ClauseSearcherTest extends Assert {
    private String searchPattern = "(.*(\\(Рис. [0-9]{1,9}\\)).*)";
    private String clausePattern = "( *[A-Z0-9]).*?([.]{3}|(?<!Рис)[.]|[?!])";

    @Test
    public void createClauseSearcherWithValidPattern() {
        try {
            ClauseSearcher searcher = new ClauseSearcher(searchPattern, clausePattern);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClauseSearcherWithEmptyPatternShouldThrowIAE() {
        ClauseSearcher searcher = new ClauseSearcher("", "");
    }

    @Test(expected = NullPointerException.class)
    public void createClauseSearcherWithNullPatternShouldThrowNPE() {
        ClauseSearcher searcher = new ClauseSearcher(null, null);
    }

    @Test
    public void findOneValidClauseWithOneMatcher() {
        String validString = "Hello from London... Here is (Рис. 9) nothing! Or something...";

        ClauseSearcher searcher = new ClauseSearcher(searchPattern, clausePattern);
        String[] founded = searcher.search(validString);

        assertEquals(1, founded.length);
        assertEquals("Here is (Рис. 9) nothing!", founded[0]);
    }

    @Test
    public void findOneValidClauseWithSomeMatchers() {
        String validString = "Hello from London Here is (Рис. 9) nothing (Рис. 11)! Or something...";

        ClauseSearcher searcher = new ClauseSearcher(searchPattern, clausePattern);
        String[] founded = searcher.search(validString);

        assertEquals(1, founded.length);
        assertEquals("Hello from London Here is (Рис. 9) nothing (Рис. 11)!", founded[0]);
    }
}
package my.epam.unit03.task03;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringCheckerTest extends Assert {
    private String pattern = "(\\(Рис. [0-9]{1,9}\\))";

    @Test
    public void createStringCheckerWithValidPattern() {
        try {
            StringChecker checker = new StringChecker(pattern, Integer::compare);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStringCheckerWithTwoValidPatternsShouldThrowsIAE() {
        StringChecker checker = new StringChecker(pattern + "some texte" + pattern, Integer::compare);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStringCheckerWithInvalidPatternShouldThrowsIAE() {
        StringChecker checker = new StringChecker("invalid pattern", Integer::compare);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createStringCheckerWithEmptyPatternThrowsIAE() {
        StringChecker checker = new StringChecker("", Integer::compare);
    }

    @Test(expected = NullPointerException.class)
    public void createStringCheckerWithNullPatternThrowsNPE() {
        StringChecker checker = new StringChecker(null, Integer::compare);
    }

    @Test
    public void checkingStringWithOneMatchAndValidCounter() throws Exception {
        String validString = "привет друзья! (Рис. 23234) И снова здрваствуйте <Рис. 232345>";

        StringChecker checker = new StringChecker(pattern, Integer::compare);
        int maxValue = checker.checkString(0, validString);

        assertEquals(maxValue, 23234);
    }

    @Test
    public void checkingStringWithNoMatchAndNoCounterShouldReturnOldMax() throws Exception {
        String validString = "привет друзья! И снова здрваствуйте <Рис. 232345>";

        StringChecker checker = new StringChecker(pattern, Integer::compare);
        int expectedMax = 123;
        int maxValue = checker.checkString(expectedMax, validString);

        assertEquals(maxValue, expectedMax);
    }

    @Test(expected = StringCheckFailException.class)
    public void checkingStringValidMatchAndWrongCounterShouldThrowsSCFE() throws Exception {
        String validString = "привет друзья! И (Рис. 123) снова здрваствуйте <Рис. 232345> (Рис. 125)";

        StringChecker checker = new StringChecker(pattern, Integer::compare);
        checker.checkString(125, validString);
    }

    @Test
    public void checkingStringValidMatchAndValidOrder() throws Exception {
        int expectedMax = 128;
        String validString = "привет друзья! И (Рис. 123) снова здрваствуйте (Рис. 123) <Рис. 232345>(Рис. " + expectedMax + ")";

        StringChecker checker = new StringChecker(pattern, Integer::compare);
        int acatualMax = checker.checkString(122, validString);

        assertEquals(expectedMax, acatualMax);
    }

    @Test(expected = StringCheckFailException.class)
    public void checkingStringValidMatchAndWrongOrder() throws Exception {
        String validString = "привет друзья! И (Рис. 128) снова здрваствуйте (Рис. 123) <Рис. 232345>";

        StringChecker checker = new StringChecker(pattern, Integer::compare);
        checker.checkString(122, validString);
    }

}
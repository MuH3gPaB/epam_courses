package my.epam.unit04.task01;

import my.epam.unit04.StringWordsCounter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class StringWordsCounterTest extends Assert {

    @Test(expected = IllegalArgumentException.class)
    public void createCounterWithEmptyWordsShouldThrowIAE() {
        new StringWordsCounter(new String[0]);
    }

    @Test(expected = NullPointerException.class)
    public void createCounterWithNullShouldThrowNPE(){
        new StringWordsCounter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCounterWithAllNullWordsShouldThrowIAE(){
        new StringWordsCounter(new String[]{null, null, null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCounterWithEmptyStringsArrayShouldThrowsIAE(){
        new StringWordsCounter(new String[]{"","",""});
    }

    @Test
    public void createCounterWithValidStringArray(){
        try {
            new StringWordsCounter(new String[]{"ddd", "aaa", "fff dd"});
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void counterCalculateWordsNotPatterns(){
        String[] words = new String[]{"word1(", "word2("};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "hello from moscow word1( or say\n word2( or maybe word 2 no way i see word1(";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(2), result.get("word1("));
        assertEquals(new Integer(1), result.get("word2("));
    }

    @Test
    public void counterCalculateWordsLikeValidPatterns(){
        String[] words = new String[]{"word1", "word2"};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "hello from moscow word1 or say\n word2 or maybe word 2 no way i see word1";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(2), result.get("word1"));
        assertEquals(new Integer(1), result.get("word2"));
    }

    @Test
    public void counterCalculateWordsUsingValidPatterns(){
        String[] words = new String[]{"[A].*? ", "[z].*? "};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "hello All from moscow zero word1 or say\n Always or maybe zone  2 no zoo i see word1";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(2), result.get(words[0]));
        assertEquals(new Integer(3), result.get(words[1]));
    }

    @Test
    public void counterCalculateWordsNotPatternsWithOneEmptyAndOneNull(){
        String[] words = new String[]{"word1(", "word2(", "", null};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "hello from moscow word1( or say\n word2( or maybe word 2 no way i see word1(";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(2), result.get("word1("));
        assertEquals(new Integer(1), result.get("word2("));
    }

    @Test
    public void counterCalculateWordsLikeValidPatternsWithOneEmptyAndOneNull(){
        String[] words = new String[]{"word1", "word2", "", null};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "hello from moscow word1 or say\n word2 or maybe word 2 no way i see word1";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(2), result.get("word1"));
        assertEquals(new Integer(1), result.get("word2"));
    }

    @Test(expected = NullPointerException.class)
    public void counterCalculateWithNullStringShouldThrowNPE(){
        String[] words = new String[]{"word1", "word2", "", null};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = null;

        counter.calculateWords(toCount);
    }

    @Test
    public void counterCalculateWithEmptyStringShouldOk(){
        String[] words = new String[]{"word1", "word2", "", null};

        StringWordsCounter counter = new StringWordsCounter(words);

        String toCount = "";

        Map<String, Integer> result = counter.calculateWords(toCount);

        assertEquals(new Integer(0), result.get("word1"));
        assertEquals(new Integer(0), result.get("word2"));
    }
}
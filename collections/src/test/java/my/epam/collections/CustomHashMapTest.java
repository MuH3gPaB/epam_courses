package my.epam.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomHashMapTest {
    private CustomHashMap<String, Integer> map;

    @Before
    public void setUp() throws Exception {
        map = new CustomHashMap<>();
    }
    // SIZE() --------------------------------------------------------------------
    @Test
    public void sizeOfEmptyMapShouldBeZero() throws Exception {
        assertEquals(0, map.size());
    }

    @Test
    public void sizeShouldGrowOnPuttingNewElement() throws Exception {
        map.put("str1", 1);
        int oldSize = map.size();

        map.put("str2", 2);
        int newSize = map.size();

        assertEquals(1, newSize - oldSize);
    }

    @Test
    public void sizeShouldReduceOnRemovingElement() throws Exception {
        String key = "str1";
        map.put(key, 1);
        map.remove(key);

        assertEquals(0, map.size());
    }

    @Test
    public void sizeShouldNotGrowOnAddingExistingKeyElement() throws Exception {
        String key = "str1";
        map.put(key, 1);
        int sizeOld = map.size();

        map.put(key, 2);
        int sizeNew = map.size();

        assertEquals(sizeNew, sizeOld);
    }

    @Test
    public void sizeShouldReturnIntMaxValueIfMapSizeMoreThenIntMaxValue() throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            map.put("val" + i, i);
        }

        map.put("more values", 1);

        assertEquals(Integer.MAX_VALUE, map.size());
    }

    // IS_EMPTY() --------------------------------------------------------------------
    @Test
    public void isEmptyShouldReturnTrueOnEmptyMap() throws Exception {
        assertTrue(map.isEmpty());
    }

    @Test
    public void isEmptyShouldReturnFalseOnNotEmptyMap() throws Exception {
        map.put("str1", 1);
        assertFalse(map.isEmpty());
    }

    // CONTAINS_KEY() --------------------------------------------------------------------
    @Test
    public void containsKeyShouldReturnTrueIfKeyPresent() throws Exception {
        String key = "key";
        map.put(key, 1);
        assertTrue(map.containsKey(key));
    }

    @Test
    public void containsKeyShouldReturnFalseIfKeyAbsent() throws Exception {
        assertFalse(map.containsKey("absent key"));
    }

    @Test(expected = NullPointerException.class)
    public void containsKeyShouldThrowNPEWithNull() throws Exception {
        assertFalse(map.containsKey(null));
    }

    // CONTAINS_VALUE() --------------------------------------------------------------------
    @Test
    public void containsValueShouldReturnTrueIfValuePresentOnce() throws Exception {
        int value = 123;
        map.put("key", value);
        assertTrue(map.containsValue(value));
    }

    @Test
    public void containsValueShouldReturnTrueIfValuePresentTwoTimes() throws Exception {
        int value = 123;
        map.put("keyOne", value);
        map.put("keyTwo", value);
        assertTrue(map.containsValue(value));
    }

    @Test
    public void containsValueShouldReturnFalseIfValueAbsent() throws Exception {
        assertFalse(map.containsValue(123));
    }

    @Test
    public void containsValueShouldReturnTrueOnNullIfNullValuePresent() throws Exception{
        map.put("key", null);
        assertTrue(map.containsValue(null));
    }

    @Test
    public void containsValueShouldReturnFalseOnNullIfNullValueAbsent() throws Exception{
        assertFalse(map.containsValue(null));
    }



    // GET() --------------------------------------------------------------------
    @Test
    public void get() throws Exception {

    }

    // PUT() --------------------------------------------------------------------
    @Test
    public void put() throws Exception {

    }

    // REMOVE() --------------------------------------------------------------------
    @Test
    public void remove() throws Exception {

    }

    // PUT_ALL() --------------------------------------------------------------------
    @Test
    public void putAll() throws Exception {

    }

    // CLEAR() --------------------------------------------------------------------
    @Test
    public void clear() throws Exception {

    }

    // KEY_SET() --------------------------------------------------------------------
    @Test
    public void keySet() throws Exception {

    }

    // VALUES() --------------------------------------------------------------------
    @Test
    public void values() throws Exception {

    }

    // ENTRY_SET() --------------------------------------------------------------------
    @Test
    public void entrySet() throws Exception {

    }

}
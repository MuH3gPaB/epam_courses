package my.epam.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
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
    public void containsValueShouldReturnTrueOnNullIfNullValuePresent() throws Exception {
        map.put("key", null);
        assertTrue(map.containsValue(null));
    }

    @Test
    public void containsValueShouldReturnFalseOnNullIfNullValueAbsent() throws Exception {
        assertFalse(map.containsValue(null));
    }


    // GET() --------------------------------------------------------------------
    @Test
    public void getShouldReturnValueThatMappedToKey() throws Exception {
        String key = "key";
        Integer value = 1;

        map.put(key, value);
        Integer actual = map.get(key);

        assertEquals(value, actual);
    }

    @Test
    public void getShouldReturnNullIfNoValueMappedToKey() throws Exception {
        assertNull(map.get("key"));
    }

    @Test(expected = NullPointerException.class)
    public void getShouldThrowNPEOnNullKey() throws Exception {
        assertNull(null);
    }


    // PUT() --------------------------------------------------------------------
    @Test
    public void putShouldAddNewUniquePair() throws Exception {
        Integer value = 10;
        String key = "key";

        map.put(key, value);
        assertTrue(map.containsKey(key) && map.containsValue(value));
    }

    @Test
    public void putShouldReplaceValueOnMappingToExistingKey() throws Exception {
        String key = "key";
        Integer value = 20;

        map.put(key, 10);
        map.put(key, value);
        assertEquals(value, map.get(key));
    }

    @Test
    public void putShouldReturnPreviousValueIfItWasMapped() throws Exception {
        String key = "key";
        Integer preValue = 20;
        map.put(key, preValue);

        assertEquals(preValue, map.put(key, 10));
    }

    @Test
    public void putShouldReturnNullIfNoPreviousValueWasMapped() throws Exception {
        assertNull(map.put("key", 10));
    }

    @Test(expected = NullPointerException.class)
    public void putShouldThrowNPEIfKeyIsNull() throws Exception {
        map.put(null, 10);
    }

    @Test(expected = ClassCastException.class)
    public void putShouldThrowCCEIfKeyHaveWrongType() throws Exception {
        Map unGenerefiedMap = map;
        unGenerefiedMap.put(1, 1);
    }

    @Test(expected = ClassCastException.class)
    public void putShouldThrowCCEIfValueHaveWrongType() throws Exception {
        Map unGenerefiedMap = map;
        unGenerefiedMap.put("key", "value");
    }

    @Test
    public void putShouldBeOkOnAddingImplementationOfGenericsValue() throws Exception {
        Map<String, Number> map = new CustomHashMap<>();
        map.put("key", 1);
        assertTrue(map.containsValue(1));
    }

    @Test
    public void putShouldBeOkOnAddingImplementationOfGenericsKey() throws Exception {
        Map<Number, Number> map = new CustomHashMap<>();
        map.put(1, 1);
        assertTrue(map.containsKey(1));
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
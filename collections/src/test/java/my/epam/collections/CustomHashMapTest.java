package my.epam.collections;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@FixMethodOrder
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
            map.put("val" + i, 1);
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
    public void removeShouldUnmapValueForPresentKey() throws Exception {
        String key = "key";
        map.put(key, 10);
        map.remove(key);
        assertFalse(map.containsKey(key));
    }

    @Test
    public void removeShouldReturnUnmappedValueForPresentKey() throws Exception {
        String key = "key";
        Integer value = 10;
        map.put(key, value);
        assertEquals(value, map.remove(key));
    }

    @Test
    public void removeShouldReturnNullForAbsentKey() throws Exception {
        assertNull(map.remove("key"));
    }

    @Test(expected = NullPointerException.class)
    public void removeShouldThrowNPEOnNullKey() throws Exception {
        map.remove(null);
    }

    // PUT_ALL() --------------------------------------------------------------------
    @Test
    public void putAllShouldPutAllPairsFromValidMapWithNoDuplicates() throws Exception {
        Map<String, Integer> mapToPut = new HashMap<>();
        Integer value1 = 1;
        Integer value2 = 2;
        mapToPut.put("key1", value1);
        mapToPut.put("key2", value2);

        map.putAll(mapToPut);

        assertEquals(value1, map.get("key1"));
        assertEquals(value2, map.get("key2"));
    }

    @Test
    public void putAllShouldPutAllPairsWithReplacingFromValidMapWithDuplicates() throws Exception {
        Map<String, Integer> mapToPut = new HashMap<>();
        Integer value1 = 1;
        Integer value2 = 2;
        mapToPut.put("key1", value1);
        mapToPut.put("key2", value2);

        map.put("key1", 10);

        map.putAll(mapToPut);

        assertEquals(value1, map.get("key1"));
        assertEquals(value2, map.get("key2"));
    }

    @Test
    public void putAllShouldWorkProperlyWithNullValues() throws Exception {
        Map<String, Integer> mapToPut = new HashMap<>();
        mapToPut.put("key1", null);

        map.putAll(mapToPut);
        assertTrue(map.containsValue(null));
    }

    @Test(expected = ClassCastException.class)
    public void putAllShouldThrowCCEOnPuttingMapWithWrongKeyType() throws Exception {
        Map mapToPut = new HashMap();
        mapToPut.put(10, 10);
        map.putAll(mapToPut);
    }

    @Test(expected = NullPointerException.class)
    public void putAllShouldThrowNPEOnPuttingMapWithNullKey() throws Exception {
        Map mapToPut = new HashMap();
        mapToPut.put(null, 10);
        map.putAll(mapToPut);
    }

    @Test(expected = ClassCastException.class)
    public void putAllShouldThrowCCEOnPuttingMapWithWrongValueType() throws Exception {
        Map mapToPut = new HashMap();
        mapToPut.put("key", "value");
        map.putAll(mapToPut);
    }

    @Test(expected = NullPointerException.class)
    public void putAllShouldThrowNPEOnNull() throws Exception {
        map.putAll(null);
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
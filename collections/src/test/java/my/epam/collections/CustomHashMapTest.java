package my.epam.collections;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

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
        int oldSize = map.size();
        map.remove(key);
        int newSize = map.size();

        assertEquals(1, oldSize - newSize);
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
    @Ignore
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
    public void clearShouldRemoveAllElementsFromMap() throws Exception {
        map.put("key", 10);
        map.clear();
        assertTrue(map.isEmpty());
    }

    // KEY_SET() METHOD --------------------------------------------------------------------
    @Test
    public void keySetShouldReturnSetOfMappedKeys() throws Exception {
        map.put("keyOne", 1);
        map.put("keyTwo", 2);

        Set<String> keys = map.keySet();

        assertTrue(keys.contains("keyOne"));
        assertTrue(keys.contains("keyTwo"));
        assertEquals(2, keys.size());
    }

    @Test
    public void keySetShouldReturnEmptySetOnEmptyMap() throws Exception {
        Set<String> keys = map.keySet();
        assertTrue(keys.isEmpty());
    }

    @Test
    public void keySetShouldNotContainLaterRemovedFromMapKeys() throws Exception {
        String keyOne = "keyOne";
        map.put(keyOne, 10);
        map.put("keyTwo", 20);

        Set<String> keys = map.keySet();
        assertTrue(keys.contains(keyOne));
        map.remove(keyOne);
        assertFalse(keys.contains(keyOne));
    }

    @Test
    public void keySetShouldContainLaterAddedToMapKeys() throws Exception {
        String keyOne = "keyOne";
        map.put("keyTwo", 20);

        Set<String> keys = map.keySet();
        assertFalse(keys.contains(keyOne));
        map.put(keyOne, 10);
        assertTrue(keys.contains(keyOne));
    }

    @Test
    public void keySetShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        String keyToRemove = "keyTwo";

        map.put(keyToRemove, 20);
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(keyToRemove)) iterator.remove();
        }

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    public void keySetShouldSupportRemovingElementsFromMapViaSetRemove() throws Exception {
        String keyToRemove = "keyTwo";

        map.put(keyToRemove, 20);
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        map.keySet().remove(keyToRemove);

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    public void keySetShouldSupportRemovingElementsFromMapViaSetRemoveAll() throws Exception {
        String keyToRemove = "keyTwo";

        map.put(keyToRemove, 20);
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        HashSet<String> keysToRemove = new HashSet<>();
        keysToRemove.add(keyToRemove);

        map.keySet().removeAll(keysToRemove);

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    public void keySetShouldSupportRemovingElementsFromMapViaSetRetainAll() throws Exception {
        String keyToKeep = "keyTwo";

        map.put(keyToKeep, 20);
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        HashSet<String> keysToKeep = new HashSet<>();
        keysToKeep.add(keyToKeep);

        map.keySet().retainAll(keysToKeep);

        assertTrue(map.containsKey(keyToKeep));
        assertFalse(map.containsKey("keyOne"));
        assertFalse(map.containsKey("keyThree"));
    }

    @Test
    public void keySetShouldSupportRemovingElementsFromMapViaSetClear() throws Exception {
        map.put("keyOne", 10);
        map.put("keyTwo", 20);
        map.put("keyThree", 30);

        map.keySet().clear();
        assertTrue(map.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void keySetShouldNotSupportAddToMapOperation() throws Exception {
        map.keySet().add("key");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void keySetShouldNotSupportAddAllToMapOperation() throws Exception {
        HashSet<String> setToAdd = new HashSet<>();
        setToAdd.add("key");
        map.keySet().addAll(setToAdd);
    }

    // VALUES() METHOD --------------------------------------------------------------------
    @Test
    public void valuesShouldReturnValuesCollection() throws Exception {
        map.put("keyOne", 10);
        map.put("keyTwo", 20);

        Collection<Integer> values = map.values();

        assertTrue(values.contains(10));
        assertTrue(values.contains(20));
        assertEquals(2, values.size());
    }

    @Test
    public void valuesShouldReturnEmptyValuesCollectionOnEmptyMap() throws Exception {
        Collection<Integer> values = map.values();
        assertTrue(values.isEmpty());
    }

    @Test
    public void valuesShouldReturnCollectionWithLaterAddedToMapValues() throws Exception {
        Collection<Integer> values = map.values();
        map.put("key", 10);
        assertTrue(values.contains(10));
    }

    @Test
    public void valuesShouldReturnCollectionWithOutLaterRemovedFromMapValues() throws Exception {
        map.put("key", 10);
        Collection<Integer> values = map.values();
        map.remove("key");
        assertFalse(values.contains(10));
    }

    @Test
    public void valuesShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        Integer valueToRemove = 20;

        map.put("keyOne", 10);
        map.put("keyTwo", valueToRemove);
        map.put("keyThree", 30);

        Iterator<Integer> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(valueToRemove)) iterator.remove();
        }

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRemove() throws Exception {
        Integer valueToRemove = 20;

        map.put("keyOne", 10);
        map.put("keyTwo", valueToRemove);
        map.put("keyThree", 30);

        map.values().remove(valueToRemove);

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRemoveAll() throws Exception {
        Integer valueToRemove = 20;

        map.put("keyOne", 10);
        map.put("keyTwo", valueToRemove);
        map.put("keyThree", 30);

        HashSet<Integer> valuesToRemove = new HashSet<>();
        valuesToRemove.add(valueToRemove);

        map.values().removeAll(valuesToRemove);

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRetainAll() throws Exception {
        Integer valueToKeep = 20;

        map.put("keyOne", 10);
        map.put("keyTwo", valueToKeep);
        map.put("keyThree", 30);

        HashSet<Integer> valuesToKeep = new HashSet<>();
        valuesToKeep.add(valueToKeep);

        map.values().retainAll(valuesToKeep);

        assertTrue(map.containsValue(valueToKeep));
        assertFalse(map.containsValue(10));
        assertFalse(map.containsValue(20));
    }

    @Test
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionClear() throws Exception {
        map.put("keyOne", 10);
        map.put("keyTwo", 20);
        map.put("keyThree", 30);

        map.values().clear();
        assertTrue(map.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void valuesShouldNotSupportAddToMapOperation() throws Exception {
        map.values().add(10);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void valuesShouldNotSupportAddAllToMapOperation() throws Exception {
        HashSet<Integer> setToAdd = new HashSet<>();
        setToAdd.add(10);
        map.values().addAll(setToAdd);
    }

    // ENTRY_SET() METHOD --------------------------------------------------------------------
    @Test
    public void entrySetShouldReturnSetOfMapEntries() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryOne = map.new CustomEntry<>("keyOne", 30);
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryTwo = map.new CustomEntry<>("keyTwo", 20);

        map.put(entryOne.getKey(), entryOne.getValue());
        map.put(entryTwo.getKey(), entryTwo.getValue());

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertTrue(entries.contains(entryOne) && entries.contains(entryTwo));
    }

    @Test
    public void entrySetShouldReturnEmptySetOnEmptyMap() throws Exception {
        Set entries = map.entrySet();
        assertTrue(entries.isEmpty());
    }

    @Test
    public void entrySetShouldNotContainLaterRemovedFromMapEntries() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entry = map.new CustomEntry<>("keyOne", 30);
        map.put(entry.getKey(), entry.getValue());
        map.put("keyTwo", 20);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertTrue(entries.contains(entry));
        map.remove(entry.getKey());
        assertFalse(entries.contains(entry));
    }

    @Test
    public void entrySetShouldContainLaterAddedToMapKeys() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entry = map.new CustomEntry<>("keyOne", 30);

        map.put("keyTwo", 20);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertFalse(entries.contains(entry));
        map.put(entry.getKey(), entry.getValue());
        assertTrue(entries.contains(entry));
    }

    @Test
    public void entrySetShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToRemove = map.new CustomEntry<>("keyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.equals(entryToRemove)) iterator.remove();
        }

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRemove() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToRemove = map.new CustomEntry<>("keyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        map.entrySet().remove(entryToRemove);

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRemoveAll() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToRemove = map.new CustomEntry<>("keyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        HashSet<Map.Entry> entriesToRemove = new HashSet<>();
        entriesToRemove.add(entryToRemove);

        map.entrySet().removeAll(entriesToRemove);

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRetainAll() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToKeep = map.new CustomEntry<>("keyTwo", 20);

        map.put(entryToKeep.getKey(), entryToKeep.getValue());
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        HashSet<Map.Entry> entriesToKeep = new HashSet<>();
        entriesToKeep.add(entryToKeep);

        map.entrySet().retainAll(entriesToKeep);

        assertTrue(map.containsKey(entryToKeep.getKey()));
        assertFalse(map.containsKey("keyOne"));
        assertFalse(map.containsKey("keyThree"));
    }

    @Test
    public void entrySetShouldSupportRemovingElementsFromMapViaSetClear() throws Exception {
        map.put("keyOne", 10);
        map.put("keyTwo", 20);
        map.put("keyThree", 30);

        map.entrySet().clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void entrySetShouldSupportSetValueToMapOperation() throws Exception {
        map.put("keyOne", 10);
        map.put("keyThree", 30);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals("keyOne")) entry.setValue(20);
        }

        assertEquals(new Integer(20), map.get("keyOne"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void entrySetShouldNotSupportAddToMapOperation() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToTry = map.new CustomEntry<>("keyTwo", 20);
        map.entrySet().add(entryToTry);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void entrySetShouldNotSupportAddAllToMapOperation() throws Exception {
        CustomHashMap<String, Integer>.CustomEntry<String, Integer> entryToTry = map.new CustomEntry<>("keyTwo", 20);
        HashSet<Map.Entry<String, Integer>> setToAdd = new HashSet<>();
        setToAdd.add(entryToTry);
        map.entrySet().addAll(setToAdd);
    }

}
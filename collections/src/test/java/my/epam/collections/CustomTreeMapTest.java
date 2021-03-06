package my.epam.collections;

import com.codeaffine.test.ConditionalIgnoreRule;
import junitx.framework.ComparableAssert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

import static com.codeaffine.test.ConditionalIgnoreRule.ConditionalIgnore;
import static com.codeaffine.test.ConditionalIgnoreRule.IgnoreCondition;

@FixMethodOrder
@RunWith(Parameterized.class)
public class CustomTreeMapTest {

    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    private CustomTreeMap<String, Integer> map;

    @Parameterized.Parameters
    public static List data() {
        return Arrays.asList(new CustomTreeMap<String, Integer>(),
                new CustomTreeMap<String, Integer>(new NaturalComparator<>()),
                buildSubMap()
        );
    }

    public CustomTreeMapTest(CustomTreeMap<String, Integer> map) {
        this.map = map;
    }

    @Before
    public void setUp() throws Exception {
        map.clear();
    }

    // SIZE() --------------------------------------------------------------------
    @Test
    public void sizeOfEmptyMapShouldBeZero() throws Exception {
        assertEquals(0, map.size());
    }

    @Test
    public void sizeShouldGrowOnPuttingNewElement() throws Exception {
        map.put("Str1", 1);
        int oldSize = map.size();

        map.put("Str2", 2);
        int newSize = map.size();

        assertEquals(1, newSize - oldSize);
    }

    @Test
    public void sizeShouldReduceOnRemovingElement() throws Exception {
        String key = "Str1";
        map.put(key, 1);
        int oldSize = map.size();
        map.remove(key);
        int newSize = map.size();

        assertEquals(1, oldSize - newSize);
    }

    @Test
    public void sizeShouldNotGrowOnAddingExistingKeyElement() throws Exception {
        String key = "Str1";
        map.put(key, 1);
        int sizeOld = map.size();

        map.put(key, 2);
        int sizeNew = map.size();

        assertEquals(sizeNew, sizeOld);
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void sizeShouldReturnIntMaxValueIfMapSizeMoreThenIntMaxValue() throws Exception {
        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);
        sizeField.set(map, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, map.size());
        map.put("Key", 10);
        assertEquals(Integer.MAX_VALUE, map.size());
    }

    // IS_EMPTY() --------------------------------------------------------------------
    @Test
    public void isEmptyShouldReturnTrueOnEmptyMap() throws Exception {
        assertTrue(map.isEmpty());
    }

    @Test
    public void isEmptyShouldReturnFalseOnNotEmptyMap() throws Exception {
        map.put("Str1", 1);
        assertFalse(map.isEmpty());
    }

    // CONTAINS_KEY() --------------------------------------------------------------------
    @Test
    public void containsKeyShouldReturnTrueIfKeyPresent() throws Exception {
        String key = "Key";
        map.put(key, 1);
        assertTrue(map.containsKey(key));
    }

    @Test
    public void containsKeyShouldReturnFalseIfMapIsEmpty() throws Exception {
        assertFalse(map.containsKey("absent key"));
    }

    @Test
    public void containsKeyShouldReturnFalseIfKeyAbsent() throws Exception {
        map.put("Key1", 10);
        map.put("Key2", 20);
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
        map.put("Key", value);
        assertTrue(map.containsValue(value));
    }

    @Test
    public void containsValueShouldReturnTrueIfValuePresentTwoTimes() throws Exception {
        int value = 123;
        map.put("KeyOne", value);
        map.put("KeyTwo", value);
        assertTrue(map.containsValue(value));
    }

    @Test
    public void containsValueShouldReturnFalseIfValueAbsent() throws Exception {
        assertFalse(map.containsValue(123));
    }

    @Test
    public void containsValueShouldReturnTrueOnNullIfNullValuePresent() throws Exception {
        map.put("Key", null);
        assertTrue(map.containsValue(null));
    }

    @Test
    public void containsValueShouldReturnFalseOnNullIfNullValueAbsent() throws Exception {
        assertFalse(map.containsValue(null));
    }

    // GET() --------------------------------------------------------------------
    @Test
    public void getShouldReturnValueThatMappedToKey() throws Exception {
        String key = "Key";
        Integer value = 1;

        map.put(key, value);
        Integer actual = map.get(key);

        assertEquals(value, actual);
    }

    @Test
    public void getShouldReturnNullIfNoValueMappedToKey() throws Exception {
        assertNull(map.get("Key"));
    }

    @Test(expected = NullPointerException.class)
    public void getShouldThrowNPEOnNullKey() throws Exception {
        map.get(null);
    }

    // PUT() --------------------------------------------------------------------
    @Test
    public void putShouldAddNewUniquePair() throws Exception {
        Integer value = 10;
        String key = "Key";

        map.put(key, value);
        assertTrue(map.containsKey(key) && map.containsValue(value));
    }

    @Test
    public void putShouldReplaceValueOnMappingToExistingKey() throws Exception {
        String key = "Key";
        Integer value = 20;

        map.put(key, 10);
        map.put(key, value);
        assertEquals(value, map.get(key));
    }

    @Test
    public void putShouldReturnPreviousValueIfItWasMapped() throws Exception {
        String key = "Key";
        Integer preValue = 20;
        map.put(key, preValue);

        assertEquals(preValue, map.put(key, 10));
    }

    @Test
    public void putShouldReturnNullIfNoPreviousValueWasMapped() throws Exception {
        assertNull(map.put("Key", 10));
    }

    @Test(expected = NullPointerException.class)
    public void putShouldThrowNPEIfKeyIsNull() throws Exception {
        map.put(null, 10);
    }

    @Test
    public void putShouldAcceptMoreThan100Elements() throws Exception {
        int elementsCount = 100;
        for (int i = 0; i < elementsCount; i++) {
            map.put("Key" + i, i);
        }

        assertTrue(map.containsValue(elementsCount - 1));
        assertEquals(elementsCount, map.size());
    }

    @Test
    public void putShouldBeOkOnAddingImplementationOfGenericsValue() throws Exception {
        Map<String, Number> map = new CustomTreeMap<>();
        map.put("Key", 1);
        assertTrue(map.containsValue(1));
    }

    @Test
    public void putShouldBeOkOnAddingImplementationOfGenericsKey() throws Exception {
        Map<Number, Number> map = new CustomTreeMap<>();
        map.put(1, 1);
        assertTrue(map.containsKey(1));
    }

    // REMOVE() --------------------------------------------------------------------
    @Test
    public void removeShouldUnmapValueForPresentKey() throws Exception {
        String key = "Key";
        String key1 = "Key1";
        String key2 = "Key2";

        map.put(key, 10);
        map.put(key1, 20);
        map.put(key2, 30);
        assertTrue(map.containsKey(key));
        map.remove(key);
        assertFalse(map.containsKey(key));
    }

    @Test
    public void removeShouldReturnUnmappedValueForPresentKey() throws Exception {
        String key = "Key";
        Integer value = 10;
        map.put(key, value);
        assertEquals(value, map.remove(key));
    }

    @Test
    public void removeShouldReturnNullForAbsentKey() throws Exception {
        assertNull(map.remove("Key"));
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
        mapToPut.put("Key1", value1);
        mapToPut.put("Key2", value2);

        map.putAll(mapToPut);

        assertEquals(value1, map.get("Key1"));
        assertEquals(value2, map.get("Key2"));
    }

    @Test
    public void putAllShouldPutAllPairsWithReplacingFromValidMapWithDuplicates() throws Exception {
        Map<String, Integer> mapToPut = new HashMap<>();
        Integer value1 = 1;
        Integer value2 = 2;
        mapToPut.put("Key1", value1);
        mapToPut.put("Key2", value2);

        map.put("Key1", 10);

        map.putAll(mapToPut);

        assertEquals(value1, map.get("Key1"));
        assertEquals(value2, map.get("Key2"));
    }

    @Test
    public void putAllShouldWorkProperlyWithNullValues() throws Exception {
        Map<String, Integer> mapToPut = new HashMap<>();
        mapToPut.put("Key1", null);

        map.putAll(mapToPut);
        assertTrue(map.containsValue(null));
    }

    @Test(expected = NullPointerException.class)
    public void putAllShouldThrowNPEOnPuttingMapWithNullKey() throws Exception {
        Map mapToPut = new HashMap();
        mapToPut.put(null, 10);
        map.putAll(mapToPut);
    }

    @Test(expected = NullPointerException.class)
    public void putAllShouldThrowNPEOnNull() throws Exception {
        map.putAll(null);
    }

    // CLEAR() --------------------------------------------------------------------
    @Test
    public void clearShouldRemoveAllElementsFromMap() throws Exception {
        map.put("K", 10);
        map.clear();
        assertTrue(map.isEmpty());
    }

    // KEY_SET() METHOD --------------------------------------------------------------------
    @Test
    public void keySetShouldReturnSetOfMappedKeys() throws Exception {
        map.put("KeyOne", 1);
        map.put("KeyTwo", 2);

        Set<String> keys = map.keySet();

        assertTrue(keys.contains("KeyOne"));
        assertTrue(keys.contains("KeyTwo"));
        assertEquals(2, keys.size());
    }

    @Test
    public void keySetShouldReturnEmptySetOnEmptyMap() throws Exception {
        Set<String> keys = map.keySet();
        assertTrue(keys.isEmpty());
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldNotContainLaterRemovedFromMapKeys() throws Exception {
        String keyOne = "KeyOne";
        map.put(keyOne, 10);
        map.put("KeyTwo", 20);

        Set<String> keys = map.keySet();
        assertTrue(keys.contains(keyOne));
        map.remove(keyOne);
        assertFalse(keys.contains(keyOne));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldContainLaterAddedToMapKeys() throws Exception {
        String keyOne = "KeyOne";
        map.put("KeyTwo", 20);

        Set<String> keys = map.keySet();
        assertFalse(keys.contains(keyOne));
        map.put(keyOne, 10);
        assertTrue(keys.contains(keyOne));
    }

    @Test
    public void keySetShouldSupportIterationWithIterator() throws Exception {
        List<String> keysExpected = new ArrayList<>();
        keysExpected.add("Key1");
        keysExpected.add("Key2");
        keysExpected.add("Key3");

        map.put(keysExpected.get(0), 10);
        map.put(keysExpected.get(1), 20);
        map.put(keysExpected.get(2), 30);

        List<String> keysActual = new ArrayList<>();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            keysActual.add(key);
        }

        if (map.comparator() != null) keysExpected.sort(map.comparator());

        assertEquals(keysExpected, keysActual);
    }

    @Test
    public void keySetShouldSupportIterationWithForEach() throws Exception {
        List<String> keysExpected = new ArrayList<>();
        keysExpected.add("Key1");
        keysExpected.add("Key2");
        keysExpected.add("Key3");

        map.put(keysExpected.get(0), 10);
        map.put(keysExpected.get(1), 20);
        map.put(keysExpected.get(2), 30);

        List<String> keysActual = new ArrayList<>();
        for (String key : map.keySet()) {
            keysActual.add(key);
        }

        if (map.comparator() != null) keysExpected.sort(map.comparator());

        assertEquals(keysExpected, keysActual);
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        String keyToRemove = "KeyTwo";

        map.put(keyToRemove, 20);
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(keyToRemove)) iterator.remove();
        }

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldSupportRemovingElementsFromMapViaSetRemove() throws Exception {
        String keyToRemove = "KeyTwo";

        map.put(keyToRemove, 20);
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        map.keySet().remove(keyToRemove);

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldSupportRemovingElementsFromMapViaSetRemoveAll() throws Exception {
        String keyToRemove = "KeyTwo";

        map.put(keyToRemove, 20);
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        HashSet<String> keysToRemove = new HashSet<>();
        keysToRemove.add(keyToRemove);

        map.keySet().removeAll(keysToRemove);

        assertFalse(map.containsKey(keyToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldSupportRemovingElementsFromMapViaSetRetainAll() throws Exception {
        String keyToKeep = "KeyTwo";

        map.put(keyToKeep, 20);
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        HashSet<String> keysToKeep = new HashSet<>();
        keysToKeep.add(keyToKeep);

        map.keySet().retainAll(keysToKeep);

        assertTrue(map.containsKey(keyToKeep));
        assertFalse(map.containsKey("KeyOne"));
        assertFalse(map.containsKey("KeyThree"));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetShouldSupportRemovingElementsFromMapViaSetClear() throws Exception {
        map.put("KeyOne", 10);
        map.put("KeyTwo", 20);
        map.put("KeyThree", 30);

        map.keySet().clear();
        assertTrue(map.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void keySetShouldNotSupportAddToMapOperation() throws Exception {
        map.keySet().add("Key");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void keySetShouldNotSupportAddAllToMapOperation() throws Exception {
        HashSet<String> setToAdd = new HashSet<>();
        setToAdd.add("Key");
        map.keySet().addAll(setToAdd);
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void keySetIteratorShouldCorrectlyRemoveRootKey() {
        fillMapForSubmapping(map);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "P":
                    iterator.remove();
            }
        }

        assertFalse(map.containsKey("P"));
    }

    // VALUES() METHOD --------------------------------------------------------------------
    @Test

    public void valuesShouldReturnValuesCollection() throws Exception {
        map.put("KeyOne", 10);
        map.put("KeyTwo", 20);

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
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldReturnCollectionWithLaterAddedToMapValues() throws Exception {
        Collection<Integer> values = map.values();
        map.put("Key", 10);
        assertTrue(values.contains(10));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldReturnCollectionWithOutLaterRemovedFromMapValues() throws Exception {
        map.put("Key", 10);
        Collection<Integer> values = map.values();
        map.remove("Key");
        assertFalse(values.contains(10));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        Integer valueToRemove = 20;

        map.put("KeyOne", 10);
        map.put("KeyTwo", valueToRemove);
        map.put("KeyThree", 30);

        Iterator<Integer> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(valueToRemove)) iterator.remove();
        }

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRemove() throws Exception {
        Integer valueToRemove = 20;

        map.put("KeyOne", 10);
        map.put("KeyTwo", valueToRemove);
        map.put("KeyThree", 30);

        map.values().remove(valueToRemove);

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRemoveAll() throws Exception {
        Integer valueToRemove = 20;

        map.put("KeyOne", 10);
        map.put("KeyTwo", valueToRemove);
        map.put("KeyThree", 30);

        HashSet<Integer> valuesToRemove = new HashSet<>();
        valuesToRemove.add(valueToRemove);

        map.values().removeAll(valuesToRemove);

        assertFalse(map.containsValue(valueToRemove));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionRetainAll() throws Exception {
        Integer valueToKeep = 20;

        map.put("KeyOne", 10);
        map.put("KeyTwo", valueToKeep);
        map.put("KeyThree", 30);

        HashSet<Integer> valuesToKeep = new HashSet<>();
        valuesToKeep.add(valueToKeep);

        map.values().retainAll(valuesToKeep);

        assertTrue(map.containsValue(valueToKeep));
        assertFalse(map.containsValue(10));
        assertFalse(map.containsValue(30));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void valuesShouldSupportRemovingElementsFromMapViaCollectionClear() throws Exception {
        map.put("KeyOne", 10);
        map.put("KeyTwo", 20);
        map.put("KeyThree", 30);

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
        Map.Entry<String, Integer> entryOne = map.new CustomNodeEntry("KeyOne", 30);
        Map.Entry<String, Integer> entryTwo = map.new CustomNodeEntry("KeyTwo", 20);

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
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldNotContainLaterRemovedFromMapEntries() throws Exception {
        Map.Entry<String, Integer> entry = map.new CustomNodeEntry("KeyOne", 30);
        map.put(entry.getKey(), entry.getValue());
        map.put("KeyTwo", 20);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertTrue(entries.contains(entry));
        map.remove(entry.getKey());
        assertFalse(entries.contains(entry));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldContainLaterAddedToMapKeys() throws Exception {
        Map.Entry<String, Integer> entry = map.new CustomNodeEntry("KeyOne", 30);

        map.put("KeyTwo", 20);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertFalse(entries.contains(entry));
        map.put(entry.getKey(), entry.getValue());
        assertTrue(entries.contains(entry));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportRemovingElementsFromMapViaIteratorRemove() throws Exception {
        Map.Entry<String, Integer> entryToRemove = map.new CustomNodeEntry("KeyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.equals(entryToRemove)) iterator.remove();
        }

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRemove() throws Exception {
        Map.Entry<String, Integer> entryToRemove = map.new CustomNodeEntry("KeyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        map.entrySet().remove(entryToRemove);

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRemoveAll() throws Exception {
        Map.Entry<String, Integer> entryToRemove = map.new CustomNodeEntry("KeyTwo", 20);

        map.put(entryToRemove.getKey(), entryToRemove.getValue());
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        HashSet<Map.Entry> entriesToRemove = new HashSet<>();
        entriesToRemove.add(entryToRemove);

        map.entrySet().removeAll(entriesToRemove);

        assertFalse(map.containsKey(entryToRemove.getKey()));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportRemovingElementsFromMapViaSetRetainAll() throws Exception {
        Map.Entry<String, Integer> entryToKeep = map.new CustomNodeEntry("KeyTwo", 20);

        map.put(entryToKeep.getKey(), entryToKeep.getValue());
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        HashSet<Map.Entry> entriesToKeep = new HashSet<>();
        entriesToKeep.add(entryToKeep);

        map.entrySet().retainAll(entriesToKeep);

        assertTrue(map.containsKey(entryToKeep.getKey()));
        assertFalse(map.containsKey("KeyOne"));
        assertFalse(map.containsKey("KeyThree"));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportRemovingElementsFromMapViaSetClear() throws Exception {
        map.put("KeyOne", 10);
        map.put("KeyTwo", 20);
        map.put("KeyThree", 30);

        map.entrySet().clear();
        assertTrue(map.isEmpty());
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void entrySetShouldSupportSetValueToMapOperation() throws Exception {
        map.put("KeyOne", 10);
        map.put("KeyThree", 30);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals("KeyOne")) entry.setValue(20);
        }

        assertEquals(new Integer(20), map.get("KeyOne"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void entrySetShouldNotSupportAddToMapOperation() throws Exception {
        Map.Entry<String, Integer> entryToTry = map.new CustomNodeEntry("KeyTwo", 20);
        map.entrySet().add(entryToTry);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void entrySetShouldNotSupportAddAllToMapOperation() throws Exception {
        Map.Entry<String, Integer> entryToTry = map.new CustomNodeEntry("KeyTwo", 20);
        HashSet<Map.Entry<String, Integer>> setToAdd = new HashSet<>();
        setToAdd.add(entryToTry);
        map.entrySet().addAll(setToAdd);
    }

    // COMPARATOR ---------------------------------------------
    @Test
    public void comparatorShouldReturnValidKeyComparator() throws Exception {
        Comparator<String> comparator = new LastCharacterComparator();
        CustomTreeMap<String, Integer> map = new CustomTreeMap<>(comparator);
        assertEquals(comparator, map.comparator());
    }

    @Test
    public void comparatorShouldReturnNullIfDefaultConstructorWasUsed() throws Exception {
        map = new CustomTreeMap<>();
        assertNull(map.comparator());
    }

    // SUBMAP ---------------------------------------------
    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldReturnValidSubMap() throws Exception {
        fillMapForSubmapping(map);
        Map<String, Integer> subMap = map.subMap("E", "G");
        assertEquals(new Integer(50), subMap.get("E"));
        assertEquals(new Integer(60), subMap.get("F"));
        assertEquals(2, subMap.size());
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldReturnEmptyMapIfFirstKeyEqualsToSecond() throws Exception {
        fillMapForSubmapping(map);
        assertTrue(map.subMap("A", "A").isEmpty());
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldContainsLaterAddedToOriginalMapValuesIfTheirKeysAreInRange() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("A", "D");
        String key = "Core";
        Integer value = 10;
        map.put(key, value);

        assertEquals(value, subMap.get(key));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldNotContainsLaterAddedToOriginalMapValuesIfTheirKeysAreNotInRange() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("A", "D");
        String key = "Xiaomi";
        map.put(key, 10);

        assertFalse(subMap.containsKey(key));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldNotContainsLaterRemovedFromOriginalMapValuesIfTheirKeysAreInRange() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("X", "Z");
        String key = "Y";
        map.remove(key);

        assertFalse(subMap.containsKey(key));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldRemoveValuesFromOriginalMap() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("X", "Z");
        String key = "Y";
        subMap.remove(key);

        assertFalse(map.containsKey(key));
    }

    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldPutValuesToOriginalMapIfTheirKeysAreInRange() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("K", "P");
        String key = "Lower";
        subMap.put(key, 20);

        assertTrue(map.containsKey(key));
    }

    @Test(expected = IllegalArgumentException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowIAEOnPuttingNewKeyWhichIsNotInRange() throws Exception {
        fillMapForSubmapping(map);
        SortedMap<String, Integer> subMap = map.subMap("K", "P");
        String key = "Crazy";
        subMap.put(key, 20);
    }

    @Test(expected = NullPointerException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowNPEIfFirstArgumentIsNull() throws Exception {
        fillMapForSubmapping(map);
        map.subMap(null, "Z");
    }

    @Test(expected = NullPointerException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowNPEIfSecondArgumentIsNull() throws Exception {
        fillMapForSubmapping(map);
        map.subMap("A", null);
    }

    @Test(expected = ClassCastException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowCCEIfFirstKeyCouldNotBeComparedInThisMap() throws Exception {
        fillMapForSubmapping(map);
        SortedMap ungenerifiedMap = map;
        ungenerifiedMap.subMap(new Object(), "B");
    }

    @Test(expected = ClassCastException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowCCEIfSecondKeyCouldNotBeComparedInThisMap() throws Exception {
        fillMapForSubmapping(map);
        SortedMap ungenerifiedMap = map;
        ungenerifiedMap.subMap("A", new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowsIAEIfFirstKeyIsGreaterThenSecond() throws Exception {
        fillMapForSubmapping(map);
        map.subMap("Z", "Y");
    }

    @Test(expected = IllegalArgumentException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowsIAEIfFirstKeyIsLessThenMapMinimum() throws Exception {
        fillMapForSubmapping(map);
        map.subMap("0", "Z");
    }

    @Test(expected = IllegalArgumentException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowsIAEIfSecondKeyIsGreaterThenMapMaximum() throws Exception {
        fillMapForSubmapping(map);
        map.subMap("C", "^");
    }

    @Test(expected = IllegalArgumentException.class)
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void subMapShouldThrowsIAEOnEmptyMap() throws Exception {
        map.subMap("a", "b");
    }

    // FIRSTKEY ---------------------------------------------
    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void firstKeyShouldReturnValidFirstKey() throws Exception {
        fillMapForSubmapping(map);
        String[] keys = map.keySet().toArray(new String[0]);
        assertEquals(keys[0], map.firstKey());
    }

    @Test(expected = NoSuchElementException.class)
    public void firstKeyShouldThrowNSEIfMapIsEmpty() throws Exception {
        map.firstKey();
    }

    // LASTKEY ---------------------------------------------
    @Test
    @ConditionalIgnore(condition = IgnoreSubMap.class)
    public void lastKeyShouldReturnValidLastKey() throws Exception {
        fillMapForSubmapping(map);
        String[] keys = map.keySet().toArray(new String[0]);
        assertEquals(keys[keys.length - 1], map.lastKey());
    }

    @Test(expected = NoSuchElementException.class)
    public void lastKeyShouldThrowNSEIfMapIsEmpty() throws Exception {
        map.lastKey();
    }

    // Balance tests
    @Test
    public void if15OrderedElementsWasAddedBalanceShouldBeCloseTo1() {
        fillMapOrdered(15);
        double error = Math.abs(map.getBalanceRate() - 1);
        ComparableAssert.assertLesser(0.01, error);
    }

    @Test
    public void if31OrderedElementsWasAddedAndThen10WasRemovedBalanceShouldBeCloseTo1() {
        fillMapOrdered(31);
        for (int i = 0; i < 10; i++) {
            map.remove("Key1" + i);
        }
        double error = Math.abs(map.getBalanceRate() - 1);
        ComparableAssert.assertLesser(0.01, error);
    }

    private void fillMapOrdered(int count) {
        for (int i = 0; i < count; i++) {
            map.put("Key" + i, i);
        }
    }

    private class LastCharacterComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            char c1 = o1.charAt(o1.length() - 1);
            char c2 = o2.charAt(o2.length() - 1);
            return c1 - c2;
        }
    }

    private static class NaturalComparator<K extends Comparable> implements Comparator<K> {
        @Override
        public int compare(K o1, K o2) {
            return o1.compareTo(o2);
        }
    }

    private static void fillMapForSubmapping(Map<String, Integer> map) {
        map.put("A", 10);
        map.put("B", 20);
        map.put("C", 30);
        map.put("D", 40);
        map.put("E", 50);
        map.put("F", 60);
        map.put("G", 70);
        map.put("H", 80);
        map.put("I", 90);
        map.put("J", 100);
        map.put("K", 110);
        map.put("L", 120);
        map.put("M", 130);
        map.put("N", 140);
        map.put("O", 150);
        map.put("P", 160);
        map.put("Q", 170);
        map.put("R", 180);
        map.put("S", 190);
        map.put("T", 200);
        map.put("U", 210);
        map.put("V", 220);
        map.put("W", 230);
        map.put("X", 240);
        map.put("Y", 250);
        map.put("Z", 260);
    }

    private static CustomTreeMap.SubMap buildSubMap() {
        CustomTreeMap<String, Integer> mainMap = new CustomTreeMap<>();
        fillMapForSubmapping(mainMap);
        return (CustomTreeMap.SubMap) mainMap.subMap("B", "Y");
    }

    public class IgnoreSubMap implements IgnoreCondition {
        @Override
        public boolean isSatisfied() {
            return map instanceof CustomTreeMap.SubMap;
        }
    }


}